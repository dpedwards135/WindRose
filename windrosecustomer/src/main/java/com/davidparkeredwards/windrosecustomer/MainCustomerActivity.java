package com.davidparkeredwards.windrosecustomer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.activity.ConfigureCompanyActivity;
import com.davidparkeredwards.windrosetools.activity.WNavMenuActivity;

public class MainCustomerActivity extends WNavMenuActivity {

    private static final String TAG = MainCustomerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_customer);

        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("Main Customer Activity");

        Intent intent = new Intent();
        intent.setClass(this, ConfigureCompanyActivity.class);
        startActivity(intent);
    }
}
