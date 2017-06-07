package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.company.Company;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObjectBundle;

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
    WRecyclerObjectBundle bundle;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private WRecyclerAdapter recyclerAdapter;


    public abstract WModelClass defineModelClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModelClass();

        Log.i(TAG, "onCreate: RecyclerView Activity");
        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("Configure Recycler View Activity");

        getWROBundle();


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
        recyclerAdapter = new WRecyclerAdapter(bundle);
        mRecyclerView.setAdapter(recyclerAdapter);
    }


    public void saveRecyclerObjects() {
        WRecyclerObjectBundle savedBundle = recyclerAdapter.getSavedBundle();
        FirebaseHelper helper = new FirebaseHelper(this);
        helper.saveWROBundle(savedBundle);
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

    private void getWROBundle() {
       // setBundle(new Company().getWRecyclerObjectsEditable()); //Temp Fix

        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        Observable<WRecyclerObjectBundle> idObservable = helper.getSavedWROBundle(modelClass.getKey());
        idObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WRecyclerObjectBundle>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: OnSubscribedID");
                    }

                    @Override
                    public void onNext(WRecyclerObjectBundle s) {
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

    public void setBundle(WRecyclerObjectBundle bundle) {
        Log.i(TAG, "setBundle: ");
        this.bundle = bundle;
        newRecyclerView();
    }

}
