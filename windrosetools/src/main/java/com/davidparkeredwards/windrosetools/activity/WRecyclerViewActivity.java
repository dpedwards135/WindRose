package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

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

        //getSavedObject();

        newRecyclerView();

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
        saveRecyclerObjects();
    }


    public void saveRecyclerObjects() {
        DbObject.getTestDbObject().putToDb(this);
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
