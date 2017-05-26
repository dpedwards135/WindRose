package com.davidparkeredwards.windrosecustomer;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by davidedwards on 5/25/17.
 */

public class WindroseCustomerApplication extends WindroseApplication {

    private static final String TAG = MainCustomerActivity.class.getSimpleName();
    //Set these variables to configure app to specs

    private String companyID = "ExampleCompany2";
    private int userFocus = WindroseApplication.CUSTOMER_FOCUS;
    private int isMultiCompanyApp = WindroseApplication.SINGLECOMPANY_APP;
    private boolean isDebug = BuildConfig.DEBUG;


    @Override
    public void setApplicationConfiguration(Context ctx) {
        if(isDebug){
            Log.i(TAG, "setApplicationConfiguration: Debug Config");
            setCompanyID(companyID+"-QA");
        } else {
            Log.i(TAG, "setApplicationConfiguration: Release Config");
            setCompanyID(companyID);
        }
        setUserFocus(userFocus);
        setMultiCompanyApp(isMultiCompanyApp);

        super.setApplicationConfiguration(ctx);
    }

    @Override
    public FirebaseDatabase getDatabase() {
        return super.getDatabase();
    }

    @Override
    public DatabaseReference getCompanyDbReference() {
        return super.getCompanyDbReference();
    }

    @Override
    public DatabaseReference getWindroseDbReference() {
        return super.getWindroseDbReference();
    }

    @Override
    public FirebaseAuth getAuth() {
        return super.getAuth();
    }
}
