package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.WUser;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 6/4/17.
 */

public abstract class WRecyclerViewActivity extends WNavMenuActivity {

    private static final String TAG = WRecyclerViewActivity.class.getSimpleName();
    WModelClass modelClass;
    WRecyclerBundle bundle;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private WRecyclerAdapter recyclerAdapter;

    //Need to change all methods over to the new getter and setter and get this to pass Form instead of Bundle
    public abstract WModelClass defineModelClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModelClass();

        Log.i(TAG, "onCreate: RecyclerView Activity");
        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("");

        getSavedForm();


    }

    public void setModelClass() {
        this.modelClass = defineModelClass();
    }

    public void newRecyclerView() {
        Log.i(TAG, "newRecyclerView: ");
        //mRecyclerView = new RecyclerView(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.wRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerAdapter = new WRecyclerAdapter(DbObject.getTestDbObject());
        //recyclerAdapter = new WRecyclerAdapter(bundle);
        mRecyclerView.setAdapter(recyclerAdapter);
    }


    public void saveRecyclerObjects() {
        WRecyclerBundle savedBundle = recyclerAdapter.getSavedBundle();
        WForm wForm = (new WForm()).fromRecyclerBundle(savedBundle);
        FirebaseHelper helper = new FirebaseHelper(this);
        helper.putWForm(wForm);
        //helper.saveWROBundle(savedBundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveRecyclerObjects();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void getSavedForm() {

        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        Observable<DBResponse> dbResponseObservable = helper.getWForm(FirebaseHelper.SAVED, modelClass, true);
        dbResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        if (dbResponse.getCode() == FirebaseHelper.OK) {
                            DbBody body = dbResponse.getDbBody();
                            WForm form = (WForm) body;
                            setBundle(form);
                        } else {
                            Log.i(TAG, "onNext: Error " + dbResponse.getMessage());
                            getBlankForm();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getBlankForm() {
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());

        //New UserID and new CompanyID are the key without dashes replaced by @ if - doesn't work

        //Next: Get new ID for each new form as it is created. Instead of storing objects in Saved, turn that
            //into an index where you can pull another object, and the index is cleared onCancel and onSubmit.
            //Company and WUser will only be unique because instead of calling the ids from the app they'll self assign

        Observable<DBResponse> dbResponseObservable = helper.getWForm(FirebaseHelper.BLANK, modelClass, false);
        dbResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        if (dbResponse.getCode() == FirebaseHelper.OK) {
                            DbBody body = dbResponse.getDbBody();
                            WForm form = (WForm) body;
                            setBundle(form);
                        } else {
                            Log.i(TAG, "onNext: No Blank form available");
                            Log.i(TAG, "onNext: CREATED OBJECT SIZE = " + (new WUser().getForm().textEdits.size()));
                            setBundle(new WUser().getForm()); //CHECK
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        Observable<WRecyclerBundle> idObservable = helper.getSavedWROBundle(modelClass.getKey());
        idObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WRecyclerBundle>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: OnSubscribedID");
                    }

                    @Override
                    public void onNext(WRecyclerBundle s) {
                        setBundle(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        Log.i(TAG, "onError: ERROR");
                        Company company = new Company();
                        setBundle(company.getWRecyclerObjectsEditable());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }
    */

    public void setBundle(WForm form) {
        WForm newForm = WForm.initialize(this, form);
        bundle = newForm.toRecyclerBundle();
        Log.i(TAG, "setBundle: ");
        Log.i(TAG, "setBundle: " );

        newRecyclerView();
    }

}
