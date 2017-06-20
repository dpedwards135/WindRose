package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.StringWithTag;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.journey.DbObjectList;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

import java.util.ArrayList;

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
    DbObject dbObject;
    int listType = CONTRACT;
    public final static int SAVED = 1;
    public final static int SUBMITTED = 2;
    public final static int CONTRACT = 3;

    private Spinner recyclerSpinner;
    private ArrayAdapter<StringWithTag> recyclerSpinnerAdapter;
    private FirebaseHelper helper;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private WRecyclerAdapter recyclerAdapter;

    //Need to change all methods over to the new getter and setter and get this to pass Form instead of Bundle
    public abstract WModelClass defineModelClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModelClass();

        helper = new FirebaseHelper(this);

        //Contract.getCompanyContract().putToDb(this, CONTRACT);

        Log.i(TAG, "onCreate: RecyclerView Activity");
        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("");

        recyclerSpinner = (Spinner) findViewById(R.id.recycler_spinner);

        getDbTagList();

    }

    public void getDbTagList() {

        Observable<DBResponse> getDbOList = helper.getDbObjectList(modelClass, listType);
        getDbOList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        if (dbResponse.getCode() == 200) {
                            ArrayList<StringWithTag> stringWithTags = new ArrayList<>();
                            DbObjectList dbol = (DbObjectList) dbResponse.getDbBody();
                            ArrayList<DbObject> list = (ArrayList<DbObject>) dbol.getDbObjectList();
                            Log.i(TAG, "onNext: " + list.toString());
                            for (DbObject object : list) {
                                StringWithTag stringWithTag = new StringWithTag(object.getDescription(), object.getUniqueID());
                                stringWithTags.add(stringWithTag);
                            }
                            configureSpinner(stringWithTags);
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

    public void configureSpinner(ArrayList<StringWithTag> dbObjectList) {
        //Later get all the contracts or saved objects and add to the list
        recyclerSpinnerAdapter = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, dbObjectList);
        recyclerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recyclerSpinner.setAdapter(recyclerSpinnerAdapter);
        recyclerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag s = (StringWithTag) parent.getItemAtPosition(position);
                String tag = (String) s.tag;
                Log.i(TAG, "onItemSelected: CLICK " + tag);

                if(!tag.equals("No Tag")) getDbObject(tag, modelClass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getDbObject(String uniqueId, WModelClass wModelClass) {

        io.reactivex.Observable<DBResponse> getDbObject = helper.getDbObject(uniqueId, wModelClass, listType);
        getDbObject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        Log.i(TAG, "onNext: DBObject received");
                        dbObject = (DbObject) dbResponse.getDbBody();
                        newRecyclerView();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
        if(dbObject != null) {
            recyclerAdapter = new WRecyclerAdapter(dbObject);
            mRecyclerView.setAdapter(recyclerAdapter);
        } else {
            Log.e(TAG, "newRecyclerView: DbObject is null");
        }

    }


    public void saveRecyclerObjects() {
        dbObject.putToDb(this, listType);
        //Add SaveDbObject here
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
}
