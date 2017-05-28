package com.davidparkeredwards.windrosetools;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by davidedwards on 5/26/17.
 */

public class WLoginActivity extends AppCompatActivity {

    private static final String TAG = "WLoginActivity";

    private ArrayList<String> companyIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wloginactivity);

        //AUTHORIZATION INITIALIZATION
        if (WindroseApplication.auth.getCurrentUser() != null) {
            Log.i(TAG, "onCreate: User is signed in as "
                    + WindroseApplication.auth.getCurrentUser().getDisplayName());
            initializeCompany();
        } else {
            Log.i(TAG, "onCreate: User is not signed in");
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), WindroseApplication.RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult: " + resultCode);
        if(resultCode == RESULT_OK) {
            initializeCompany();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean initializeCompany() {
        Date date = new Date();
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        //helper.setNewCompanyId("Example Company " + date.toString());
        helper.getCompanyIds();


        /*
        Single<String> testSingle = helper.testObservable();
                testSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: Disposable");
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.i(TAG, "onSuccess: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

                */


/*
        (new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: SUBSCRIBED...");

                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.i(TAG, "onSuccess: " + s);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
*/





            if(WindroseApplication.auth.getCurrentUser() != null) {
                //Start MainActivity
                Intent intent = new Intent();
                String packageName = getApplicationContext().getPackageName();
                ComponentName componentName = new ComponentName(packageName,
                        packageName + ".WMainActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }


        return true;
    }
    private boolean companyIdValid() {
        return true;
    }

    private void requestCompanyId() {
        //Show box with company names and ids to select from, or search box
    }


}