package com.davidparkeredwards.windrosecustomer;

import com.davidparkeredwards.windrosetools.activity.WRecyclerViewActivity;
import com.davidparkeredwards.windrosetools.model.WModelClass;

public class MainCustomerActivity extends WRecyclerViewActivity {

    private static final String TAG = MainCustomerActivity.class.getSimpleName();

    @Override
    public WModelClass defineModelClass() {
        return WModelClass.COMPANY;
    }

    @Override
    public int defineListType() {
        return CONTRACT;
    }
}
