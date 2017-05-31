package com.davidparkeredwards.windrosetools;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by davidedwards on 5/25/17.
 */

public class WindroseApplication extends Application {

    /*This class assists in setting up the individual apps in the same way. It does the following:
    1. Defines whether the app is a customer focused app, employee focused app, or management focused app
    2. Defines whether the app is a company specific app or a multi-company app
    3. Initializes Firebase App with all necessary elements
    4. Pulls the appropriate DatabaseReference for the scope
    5. Pulls the Windrose DatabaseReference for everything else
    6. Identifies the company in focus
    */


    private static String companyID;


    private static int userFocus;
    private static int multiCompanyApp;
    private boolean isDebug;
    public static final int RC_SIGN_IN = 123;


    //UserFocus variables:
    public static final int CUSTOMER_FOCUS = 1;
    public static final int BUSINESS_FOCUS = 2;

    public static final int MULTICOMPANY_APP = 1;
    public static final int SINGLECOMPANY_APP = 2;

    //Firebase variables - Delete references later
    public static FirebaseDatabase firebaseDatabase;
    public static FirebaseAuth auth;

    private static final String TAG = WindroseApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);

        FirebaseApp.initializeApp(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        //Define variables:
        companyID = BuildConfig.COMPANY_ID;
        userFocus = BuildConfig.USER_FOCUS;

        multiCompanyApp = BuildConfig.IS_MULTI_COMPANY_APP;
        isDebug = BuildConfig.DEBUG;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        //FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        //helper.firebaseHelperCheck();
    }

    public static int getMultiCompanyApp() {
        return multiCompanyApp;
    }

    public static String getCompanyID() {
        return companyID;
    }

    public static void setCompanyID(String companyID) {
        WindroseApplication.companyID = companyID;
    }

    public static int getUserFocus() {
        return userFocus;
    }

}