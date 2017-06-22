package com.davidparkeredwards.windrosetools.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.WUser;
import com.davidparkeredwards.windrosetools.model.journey.DbObjectList;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 5/26/17.
 */

public class WLoginActivity extends AppCompatActivity {
    private static final String TAG = "WLoginActivity";
    public static final int CREATE_WUSER = 200;


    private Spinner recyclerSpinner;
    private ArrayAdapter<StringWithTag> recyclerSpinnerAdapter;
    private FirebaseHelper helper;
    private Button newCompanyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new FirebaseHelper(this);


        Log.i(TAG, "onCreate: Wlogin Activity");
        setContentView(R.layout.wloginactivity);
        newCompanyButton = (Button) findViewById(R.id.new_company_button);
        newCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCompany();
            }
        });

        getDbTagList();

        if (WindroseApplication.auth.getCurrentUser() != null) {
            setWUser();
            Log.i(TAG, "onCreate: User is not null");
        } else {
            Log.i(TAG, "onCreate: User is not signed in");
            Intent intent = new Intent();
            intent.setClass(this, SignInActivity.class);
            startActivityForResult(intent, CREATE_WUSER);
        }
    }
    
    public void getDbTagList() {

        Observable<DBResponse> getDbOList = helper.getDbObjectList(WModelClass.COMPANY, WRecyclerViewActivity.SUBMITTED);
        getDbOList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        if (dbResponse.getCode() == 200) {
                            ArrayList<StringWithTag> stringWithTags = new ArrayList<>();
                            DbObjectList dbol = (DbObjectList) dbResponse.getDbBody();
                            ArrayList<DbObject> list = (ArrayList<DbObject>) dbol.getDbObjectList();
                            Log.i(TAG, "onNext: " + list.toString());
                            for (DbObject object : list) {
                                if(object.getProperties().get("1") != null) {
                                    String name = object.getProperties().get("1").get(3);
                                    StringWithTag stringWithTag = new StringWithTag(name, object.getUniqueID());
                                    stringWithTags.add(stringWithTag);
                                }
                            }
                            configureSpinner(stringWithTags);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void configureSpinner(ArrayList<StringWithTag> dbObjectList) {
        recyclerSpinner = (Spinner) findViewById(R.id.company_spinner);
        recyclerSpinnerAdapter = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, dbObjectList);
        dbObjectList.add(0, new StringWithTag("Select one", "No Tag"));
        recyclerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recyclerSpinner.setAdapter(recyclerSpinnerAdapter);
        recyclerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag s = (StringWithTag) parent.getItemAtPosition(position);
                String tag = (String) s.tag;
                Log.i(TAG, "onItemSelected: CLICK " + tag);
                if(!tag.equals("No Tag")) {
                    WindroseApplication.setCompanyID(tag);
                    startMainActivity();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        String packageName = getApplicationContext().getPackageName();
        ComponentName componentName = new ComponentName(packageName,
                packageName + ".WMainActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_WUSER) {
            if (resultCode == RESULT_OK) {
                setWUser();
                Log.i(TAG, "onActivityResult: ");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setWUser() {

        Single<DBResponse> getWUserIDObservable = helper.checkPreExistingUser(WindroseApplication.auth.getCurrentUser().getUid());

        getWUserIDObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DBResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(DBResponse dbResponse) {
                        if (dbResponse.getCode() == FirebaseHelper.OK) {

                            //NEXT OBSERVABLE GETS THE USER OBJECT
                            String uniqueId = ((UniqueIds) dbResponse.getDbBody()).getUniqueIds().get(0);
                            Observable<DBResponse> getUser = helper.getDbObject(uniqueId, WModelClass.W_USER,
                                    WRecyclerViewActivity.SUBMITTED);
                            getUser.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<DBResponse>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(DBResponse dbResponse) {
                                            if (dbResponse.getCode() == FirebaseHelper.OK) {
                                                WUser w = new WUser();
                                                w.fromDbObject((DbObject) dbResponse.getDbBody());
                                                WindroseApplication.currentWUser = w;
                                                Log.i(TAG, "onNext: currentWUser : " + WindroseApplication.currentWUser.getWUserId());
                                            } else {
                                                Log.i(TAG, "onNext: SET WUSER FAILED");
                                            }
                                        }


                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }
                });
    }

    public void addNewCompany() {
        Intent intent = new Intent();
        intent.setClass(this, NewCompanySetUp.class);
        startActivity(intent);
    }

}




    /*

    public static final int CREATE_WUSER = 200;


    FirebaseHelper helper = new FirebaseHelper(this);
    Spinner companyIdSpinner;
    ArrayAdapter companySpinnerAdapter;
    Button newCompanyButton;
    Context context = this;

    private HashMap<String, String> companyIDList;
    private List<StringWithTag> companyNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wloginactivity);

        newCompanyButton = (Button) findViewById(R.id.new_company_button);
        newCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Clicked");
                Intent intent = new Intent();
                intent.setClass(context, NewCompanySetUp.class);
                startActivity(intent);
            }
        });
        if(WindroseApplication.getMultiCompanyApp() == WindroseApplication.MULTICOMPANY_APP
                && WindroseApplication.getUserFocus() == WindroseApplication.BUSINESS_FOCUS) {
            newCompanyButton.setVisibility(View.VISIBLE);
        }
        companyIdSpinner = (Spinner) findViewById(R.id.company_spinner);


        //AUTHORIZATION INITIALIZATION
        if (WindroseApplication.auth.getCurrentUser() != null) {
            setWUser();
        } else {
            Log.i(TAG, "onCreate: User is not signed in");

            Intent intent = new Intent();
            intent.setClass(this, SignInActivity.class);
            startActivityForResult(intent, CREATE_WUSER);
            //startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_WUSER) {
            if (resultCode == RESULT_OK) {
                setWUser();
                Log.i(TAG, "onActivityResult: ");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setWUser() {
        Single<DBResponse> getWUserIDObservable = helper.checkPreExistingUser(WindroseApplication.auth.getCurrentUser().getUid());

        getWUserIDObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(DBResponse dbResponse) {
                        if (dbResponse.getCode() == FirebaseHelper.OK) {
                            String uniqueId = ((UniqueIds) dbResponse.getDbBody()).getUniqueIds().get(0);
                            Observable<DBResponse> getUser = helper.getWModelObjectHashMap(WModelClass.W_USER, uniqueId, WRecyclerViewActivity.SUBMITTED);
                            getUser.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<DBResponse>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {}

                                        @Override
                                        public void onNext(DBResponse dbResponse) {
                                            if (dbResponse.getCode() == FirebaseHelper.OK) {
                                                WUser w = new WUser();
                                                w.fromDbObject((DbObject) dbResponse.getDbBody());
                                                WindroseApplication.currentWUser = w;
                                                Log.i(TAG, "onNext: currentWUser : " + WindroseApplication.currentWUser.getWUserId());
                                                initializeCompany();
                                            } else {
                                                Log.i(TAG, "onNext: SET WUSER FAILED");
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });


                        } else {
                            Log.i(TAG, "onNext: SET WUSER FAILED");

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void initializeCompany() {

        FirebaseHelper helper = new FirebaseHelper(getApplicationContext());


        /*
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


    }

    */




