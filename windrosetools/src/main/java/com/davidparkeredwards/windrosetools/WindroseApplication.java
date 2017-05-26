package com.davidparkeredwards.windrosetools;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private boolean isDebug = BuildConfig.DEBUG;

    //UserFocus variables:
    public static final int CUSTOMER_FOCUS = 1;
    public static final int EMPLOYEE_FOCUS = 2;
    public static final int MANAGEMENT_FOCUS = 3;

    public static final int MULTICOMPANY_APP = 1;
    public static final int SINGLECOMPANY_APP = 2;

    //Firebase variables
    public static FirebaseDatabase database;
    public static DatabaseReference companyDbReference;
    public static DatabaseReference windroseDbReference;
    public static FirebaseAuth auth;
    public static final int RC_SIGN_IN = 123;

    private static final String TAG = WindroseApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        setApplicationBaseConfiguration(getApplicationContext());
    }

    public void setApplicationBaseConfiguration(Context ctx) {


            FirebaseApp.initializeApp(ctx);
            auth = FirebaseAuth.getInstance();

            //DB INITIALIZATION
            database = FirebaseDatabase.getInstance();
            windroseDbReference = database.getReference();
    }

    public void setApplicationCompanyConfiguration(Context ctx, int userFocus, int multiCompanyApp) {

        if(isDebug){
            Log.i(TAG, "setApplicationConfiguration: Debug Config");
            setCompanyID(companyID+"-QA");
        } else {
            Log.i(TAG, "setApplicationConfiguration: Release Config");
            setCompanyID(companyID);
        }
        setUserFocus(userFocus);
        setMultiCompanyApp(multiCompanyApp);

        if(companyID != null && userFocus > 0 && multiCompanyApp > 0) {

            //COMPANY DB INITIALIZATION

            if(isDebug){
                Log.i(TAG, "setApplicationCompanyConfiguration: Debug Config");
                companyDbReference = database.getReference("/" + companyID + "-QA" + "/");
                setCompanyID(companyID+"-QA");
            } else {
                Log.i(TAG, "setApplicationCompanyConfiguration: Release Config");
                companyDbReference = database.getReference("/" + companyID + "/");
            }
        } else {
            Log.i(TAG, "setApplicationConfiguration: PLEASE ENTER CONFIG INFO");
        }
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

    public static void setUserFocus(int userFocus) {
        WindroseApplication.userFocus = userFocus;
    }

    public static int isMultiCompanyApp() {
        return multiCompanyApp;
    }

    public static void setMultiCompanyApp(int multiCompanyApp) {
        WindroseApplication.multiCompanyApp = multiCompanyApp;
    }

    public static int getCustomerFocus() {
        return CUSTOMER_FOCUS;
    }

    public static int getEmployeeFocus() {
        return EMPLOYEE_FOCUS;
    }

    public static int getManagementFocus() {
        return MANAGEMENT_FOCUS;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getCompanyDbReference() {
        return companyDbReference;
    }

    public void setCompanyDbReference(DatabaseReference companyDbReference) {
        this.companyDbReference = companyDbReference;
    }

    public DatabaseReference getWindroseDbReference() {
        return windroseDbReference;
    }

    public void setWindroseDbReference(DatabaseReference windroseDbReference) {
        this.windroseDbReference = windroseDbReference;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

}
