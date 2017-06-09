package com.davidparkeredwards.windrosetools.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.davidparkeredwards.windrosetools.model.WModelClass;

public class WSignUpActivity extends WRecyclerViewActivity {

    @Override
    public WModelClass defineModelClass() {
        return WModelClass.W_USER;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.setResult(RESULT_CANCELED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SIGNUPACTIVITY", "onResume: ");


        //WindroseApplication.currentWUser = ...
        Intent returnIntent = new Intent();
        this.setResult(Activity.RESULT_OK, returnIntent);
        //this.finish();
    }
}
