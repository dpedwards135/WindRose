package com.davidparkeredwards.windrosetools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;

/**
 * Created by davidedwards on 5/26/17.
 */

public class WLoginActivity extends AppCompatActivity {

    private static final String TAG = WLoginActivity.class.getSimpleName();

    //MOVE DEFINITIONS TO APPLICATION LEVEL VALUES FILE
    private String flavor;
    private String companyID;
    private int userFocus;
    private int isMultiCompanyApp;
    private Class<Activity> mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wloginactivity);

        //Define variables:
        flavor = BuildConfig.
        companyID =
        userFocus = WindroseApplication.CUSTOMER_FOCUS;
        isMultiCompanyApp = WindroseApplication.SINGLECOMPANY_APP;
        mainActivity =

        //AUTHORIZATION INITIALIZATION
        if (WindroseApplication.auth.getCurrentUser() != null) {
            Log.i(TAG, "onCreate: User is signed in as "
                    + WindroseApplication.auth.getCurrentUser().getDisplayName());
            //Go to next step - Choose company or proceed to Main Activity

        } else {
            Log.i(TAG, "onCreate: User is not signed in");
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), WindroseApplication.RC_SIGN_IN);
        }

        //COMPANY INITIALIZATION
        if(companyID == "" || !companyIdValid()) {
            requestCompanyId();
        }
        WindroseApplication application = (WindroseApplication) getApplication();
        application.setApplicationCompanyConfiguration(getApplicationContext(), userFocus, isMultiCompanyApp);

        //Start MainActivity

        Intent mainActivityIntent = new Intent().setClass(getApplicationContext(), mainActivity);

    }


    private void requestCompanyId() {
        //Show box with company names and ids to select from, or search box
    }


}