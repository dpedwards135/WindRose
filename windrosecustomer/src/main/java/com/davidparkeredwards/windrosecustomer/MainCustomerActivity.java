package com.davidparkeredwards.windrosecustomer;

import android.os.Bundle;

import com.davidparkeredwards.windrosetools.activity.WRecyclerViewActivity;
import com.davidparkeredwards.windrosetools.model.WModelClass;

public class MainCustomerActivity extends WRecyclerViewActivity {

    private static final String TAG = MainCustomerActivity.class.getSimpleName();

    @Override
    public WModelClass defineModelClass() {
        return WModelClass.COMPANY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_customer);

    }
}
