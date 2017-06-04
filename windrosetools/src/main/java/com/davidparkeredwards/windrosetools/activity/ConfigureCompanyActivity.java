package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.davidparkeredwards.windrosetools.R;

/**
 * Created by davidedwards on 6/4/17.
 */

public class ConfigureCompanyActivity extends WNavMenuActivity {

    private static final String TAG = ConfigureCompanyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_customer);
        TextView contentText = (TextView) findViewById(R.id.content_text_view);
        contentText.setText("Configure Company Activity");
    }
}
