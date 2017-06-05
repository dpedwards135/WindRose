package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.model.company.Company;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObjectBundle;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class ConfigureCompanyActivity extends WNavMenuActivity {

    private static final String TAG = ConfigureCompanyActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private WRecyclerAdapter recyclerAdapter;
    private ArrayList<WRecyclerObject> wRecyclerObjectsList;
    private static final String LIST_KEY = "RVKEY";
    private Parcelable mListState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("Configure Company Activity");

        /*
        wRecyclerObjectsList = new ArrayList<>();
        WRecyclerTextView wRecyclerTextView = new WRecyclerTextView("Text Text: 1");
        WRecyclerTextView wRecyclerTextView1 = new WRecyclerTextView("Text Text: 2");
        WRecyclerCheckBox wRecyclerCheckBox = new WRecyclerCheckBox("Check it: 1 ", true);
        WRecyclerFinalizeButtons finalizeButtons = new WRecyclerFinalizeButtons(true, true, true);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("String 1");
        strings.add("String 2");
        strings.add("String 3");
        WRecyclerSelectFrom selectFrom = new WRecyclerSelectFrom(true, "sample text",
                strings, 1,"Select Something");


        WRecyclerTextEdit textEdit = new WRecyclerTextEdit("This is your Text Edit", null, "Enter some text");

        wRecyclerObjectsList.add(wRecyclerTextView);
        wRecyclerObjectsList.add(wRecyclerTextView);
        wRecyclerObjectsList.add(wRecyclerTextView1);
        wRecyclerObjectsList.add(wRecyclerCheckBox);
        wRecyclerObjectsList.add(finalizeButtons);
        wRecyclerObjectsList.add(selectFrom);
        wRecyclerObjectsList.add(textEdit);
        */

        WRecyclerObjectBundle bundle = new Company().getWRecyclerObjectsEditable();

        wRecyclerObjectsList = bundle.getRecyclerObjects();

        mRecyclerView = (RecyclerView) findViewById(R.id.wRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerAdapter = new WRecyclerAdapter(wRecyclerObjectsList);
        mRecyclerView.setAdapter(recyclerAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mListState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mListState);
        }
    }

    public void saveRecyclerObjects() {
        wRecyclerObjectsList = recyclerAdapter.getSavedObjects();
        Log.i(TAG, "saveRecyclerObjects: DO MORE WORK TO ENSURE RECYCLER OBJECT PERSISTENCE HERE");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save list state
        mListState = mLinearLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_KEY, mListState);
        saveRecyclerObjects();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(LIST_KEY);
    }
}
