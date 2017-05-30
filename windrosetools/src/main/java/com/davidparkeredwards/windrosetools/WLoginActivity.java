package com.davidparkeredwards.windrosetools;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.firebase.ui.auth.AuthUI;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 5/26/17.
 */

public class WLoginActivity extends AppCompatActivity {

    private static final String TAG = "WLoginActivity";

    Spinner companyIdSpinner;
    ArrayAdapter companySpinnerAdapter;
    ListView companyListView;

    private HashMap<String, String> companyIDList;
    private List<StringWithTag> companyNameList;

    public class StringWithTag {
        public String string;
        public Object tag;

        public StringWithTag(String stringPart, Object tagPart) {
            string = stringPart;
            tag = tagPart;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wloginactivity);

        companyIdSpinner = (Spinner) findViewById(R.id.company_spinner);

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

    private void initializeCompany() {
        
        Date date = new Date();
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        //helper.setNewCompanyId("Example Company " + date.toString());
        Observable<HashMap> idObservable = helper.getCompanyIdObservable();
        idObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HashMap>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   Log.i(TAG, "onSubscribe: OnSubscribedID");
                               }

                               @Override
                               public void onNext(HashMap s) {
                                   Log.i(TAG, "onNext: VALUE: ");
                                   companyIDList = s;
                                   if(WindroseApplication.getMultiCompanyApp() == WindroseApplication.MULTICOMPANY_APP
                                           || !companyIdValid()) {
                                       selectCompany();

                                   } else {
                                       startMainActivity();
                                   }

                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e(TAG, "onError: ", e);

                               }

                               @Override
                               public void onComplete() {
                                   Log.i(TAG, "onComplete: ");

                               }
                           });
    }
    private boolean companyIdValid() {
        if(companyIDList.containsKey(WindroseApplication.getCompanyID())) {
            return true;
        } else {
            return false;
        }
    }

    private void requestCompanyId() {
        //Show box with company names and ids to select from, or search box
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        String packageName = getApplicationContext().getPackageName();
        ComponentName componentName = new ComponentName(packageName,
                packageName + ".WMainActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }

    private void selectCompany() {
        //!!!!!Need to fix this so that the IDs can be mapped with names.
        Iterator it = companyIDList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            StringWithTag entry = new StringWithTag(pair.getValue().toString(), pair.getKey().toString());
            companyNameList.add(entry);
            it.remove(); // avoids a ConcurrentModificationException
        }
        companySpinnerAdapter = new ArrayAdapter<StringWithTag> (this, android.R.layout.simple_spinner_item, companyNameList);
        //
        companySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companyIdSpinner.setAdapter(companySpinnerAdapter);
        companyIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag s = (StringWithTag) parent.getItemAtPosition(position);
                Object tag = s.tag;
                Log.i(TAG, "onItemSelected: CLICK " + tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setCompany(View view) {
        String companyId = ((StringWithTag) companyIdSpinner.getSelectedItem()).tag.toString();
        Log.i(TAG, "setCompany: COMPANYID = " + companyId);
        WindroseApplication.setCompanyID(companyId);
        startMainActivity();

    }


}