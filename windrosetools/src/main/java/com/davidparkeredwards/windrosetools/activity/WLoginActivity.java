package com.davidparkeredwards.windrosetools.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.StringWithTag;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.WUser;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;
import com.davidparkeredwards.windrosetools.wForm.WForm;

import java.util.ArrayList;
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
    public static final int RC_SIGN_IN = 100;
    public static final int CREATE_WUSER = 200;


    Spinner companyIdSpinner;
    ArrayAdapter companySpinnerAdapter;
    Button newCompanyButton;

    private HashMap<String, String> companyIDList;
    private List<StringWithTag> companyNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wloginactivity);

        newCompanyButton = (Button) findViewById(R.id.new_company_button);
        if(WindroseApplication.getMultiCompanyApp() == WindroseApplication.MULTICOMPANY_APP
                && WindroseApplication.getUserFocus() == WindroseApplication.BUSINESS_FOCUS) {
            newCompanyButton.setVisibility(View.VISIBLE);
        }
        companyIdSpinner = (Spinner) findViewById(R.id.company_spinner);

        //AUTHORIZATION INITIALIZATION
        if (WindroseApplication.auth.getCurrentUser() != null) {
            Log.i(TAG, "onCreate: User is signed in as "
                    + WindroseApplication.auth.getCurrentUser().getDisplayName());
            getWUserIDFromIndex();
        } else {
            Log.i(TAG, "onCreate: User is not signed in");

            Intent intent = new Intent();
            intent.setClass(this, SignUp.class);
            startActivityForResult(intent, CREATE_WUSER);
            //startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
        }
    }

    protected void createNewWUser() {
        Log.i(TAG, "createNewWUser: ");
        Intent intent = new Intent();
        //intent.setClass(this, WSignUpActivity.class); Check
        //startActivityForResult(intent, CREATE_WUSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Get Authorization Activity result
        Log.i(TAG, "onActivityResult: " + resultCode);
        if(requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                getWUserIDFromIndex();
            }
        }
        if(requestCode == CREATE_WUSER) {
            if (resultCode == RESULT_OK) {

                //Next - log in the user in this activity, including logging in after signing up
                Log.i(TAG, "onActivityResult: ");
                Log.i(TAG, "onActivityResult: OK CREATE WUSER " + WindroseApplication.currentWUser.getWUserId());
                //getWUserFromDB(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getWUserIDFromIndex() {
        Log.i(TAG, "getWUserIDFromIndex: ");
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        Observable<DBResponse> userIndexObservable = helper.getUniqueIds(FirebaseHelper.WINDROSE_INDEX, WModelClass.W_USER,
                FirebaseHelper.PRECISION_EXACT,WindroseApplication.auth.getCurrentUser().getUid());
        userIndexObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        Log.i(TAG, "onNext: Message = " + dbResponse.getMessage());
                        if(dbResponse.getCode() == FirebaseHelper.OK) {
                            DbBody body = dbResponse.getDbBody();
                            UniqueIds ids = (UniqueIds) body.getDbBody();
                            String id = ids.getUniqueIds().get(0);
                            Log.i(TAG, "onNext: ID is " + id);
                            getWUserFromDB(id);
                        } else {
                            Log.i(TAG, "onNext: Error " + dbResponse.getMessage());
                            createNewWUser();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getWUserFromDB(String uniqueId) {
        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());
        Observable<DBResponse> userObservable = helper.getWForm(uniqueId, WModelClass.W_USER, false);
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        Log.i(TAG, "onNext: Message = " + dbResponse.getMessage());
                        if(dbResponse.getCode() == FirebaseHelper.OK) {
                            DbBody body = dbResponse.getDbBody();
                            WForm form = (WForm) dbResponse.getDbBody();
                            WUser wUser = new WUser(form);
                            WindroseApplication.currentWUser = wUser;
                            initializeCompany();
                        } else {
                            Log.i(TAG, "onNext: Error " + dbResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    private void startMainActivity() {
        Intent intent = new Intent();
        String packageName = getApplicationContext().getPackageName();
        ComponentName componentName = new ComponentName(packageName,
                packageName + ".WMainActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }

    private void selectCompany() {
        Iterator it = companyIDList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            StringWithTag entry = new StringWithTag(pair.getValue().toString(), pair.getKey().toString());
            companyNameList.add(entry);
            it.remove(); // avoids a ConcurrentModificationException
        }
        companySpinnerAdapter = new ArrayAdapter<StringWithTag> (this, android.R.layout.simple_spinner_item, companyNameList);
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

    public void addNewCompany() {
        /*
        Need to create Company class and a subtype of that to be CompanyInConfiguration - one of
        which can be saved to every user and if they have one it will be listed as an option in
        place of Add New Company. A CompanyInConfiguration will walk the user through all the steps
        of Configuring a new company, with steps in all of the options. There will be an option at
        the end to Validate New Company and then to Launch New Company, which will move the companyID
        fromRecyclerBundle CompanyInConfiguration class to CompanyIdList so that customers can find it.
        Companies/InConfiguration/UserId/CompanyID: Company
        Companies/Launched/CompanyID: Company
        Companies/Deactivated/CompanyId: Company

        Company
         */
    }


}