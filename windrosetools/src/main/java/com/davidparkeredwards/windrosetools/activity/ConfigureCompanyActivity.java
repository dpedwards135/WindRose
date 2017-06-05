package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerAdapter;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerTextView;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class ConfigureCompanyActivity extends WNavMenuActivity {

    private static final String TAG = ConfigureCompanyActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private WRecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("Configure Company Activity");

        ArrayList<WRecyclerObject> wRecyclerObjectsList = new ArrayList<>();
        WRecyclerTextView wRecyclerTextView = new WRecyclerTextView("Text Text: ");
        wRecyclerObjectsList.add(wRecyclerTextView);
        wRecyclerObjectsList.add(wRecyclerTextView);
        wRecyclerObjectsList.add(wRecyclerTextView);

        mRecyclerView = (RecyclerView) findViewById(R.id.wRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerAdapter = new WRecyclerAdapter(wRecyclerObjectsList);
        mRecyclerView.setAdapter(recyclerAdapter);

    }
}
