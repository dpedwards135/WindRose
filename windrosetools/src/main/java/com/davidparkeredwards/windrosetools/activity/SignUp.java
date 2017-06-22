package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.Contract;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.WUser;
import com.davidparkeredwards.windrosetools.model.journey.DbObjectList;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbResponseHandler;
import com.davidparkeredwards.windrosetools.wForm.WFormField;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    FirebaseHelper helper = new FirebaseHelper(this);
    DbResponseHandler dbResponseHandler = new DbResponseHandler(this);
    TextInputEditText password;
    TextInputLayout password_layout;
    TextInputEditText password_confirm;
    TextInputLayout password_confirm_layout;
    TextInputEditText full_name;
    TextInputEditText email_address;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Set up to sign up view. When user submits it gets the call back from Firebase and then validates and creates a new user in the db

        password = (TextInputEditText) findViewById(R.id.password);
        password_confirm = (TextInputEditText) findViewById(R.id.verify_password);
        full_name = (TextInputEditText) findViewById(R.id.full_name);
        email_address = (TextInputEditText) findViewById(R.id.email_address_box);
        password_layout = (TextInputLayout) findViewById(R.id.password_til);
        password_confirm_layout = (TextInputLayout) findViewById(R.id.password_confirm_til);
        submitButton = (Button) findViewById(R.id.signup_button);

        password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == password.getText()) {
                    password_confirm_layout.setError("Password OK");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(password.getText().toString())) {
                    password_confirm_layout.setError("Password does not match");
                } else if(s.toString().equals(password.getText().toString())) {
                    password_confirm_layout.setError("");
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSubmit();
                //Check if all fields are filled out
                //Check if user already exists in W system
                //Attempt to create new google id
                //Attempt to create new W ID
                //Return to last activity with congratulations
            }
        });
    }

    private void clickSubmit() {
        Log.i(TAG, "clickSubmit: ");
        if(email_address.getText() != null
                && full_name.getText() != null
                && password.getText() != null
                && password_confirm != null) {
            //createFBUser();
            checkForExistingUser();
        }
    }

    private void createFBUser() {
        WindroseApplication.auth.createUserWithEmailAndPassword(email_address.getText().toString(),
                password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i(TAG, "createUserWithEmail:success "
                                    + WindroseApplication.auth.getCurrentUser().getEmail().toString());
                            addNewWUser(WindroseApplication.auth.getCurrentUser().getUid());

                        } else {
                            Log.i(TAG, "createUserWithEmail:failure", task.getException());
                            showToast();
                        }
                    }
                });
    }

    private void checkForExistingUser() {

        Log.i(TAG, "checkForExistingUser: ");
        Observable<DBResponse> getUsers = helper.getDbObjectList(WModelClass.W_USER, WRecyclerViewActivity.SUBMITTED);
        getUsers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        List<DbObject> list = ((DbObjectList) dbResponse.getDbBody()).getDbObjectList();
                        for(DbObject dbObject : list) {
                            if(dbObject.description.contains(email_address.getText().toString())) {
                                showToast();
                                return;
                            }
                        }
                        createFBUser();
                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*

        Log.i(TAG, "checkForExistingUser: ");
        Single<DBResponse> newSingle = helper.checkPreExistingUser(email_address.getText().toString());
        newSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DBResponse dbResponse) {

                        Log.i(TAG, "onNext: Single ");
                        if(dbResponse.getCode() == helper.OK) {
                            showToast();
                        } else if(dbResponse.getCode() == helper.FAILED) {
                            createFBUser();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                */
    }

    private void addNewWUser(String uid) {
        Log.i(TAG, "addNewWUser: ");
        String newKey = helper.getNewObjectKey(WModelClass.W_USER, WRecyclerViewActivity.SUBMITTED);

        WUser user = new WUser(full_name.getText().toString(), email_address.getText().toString(),
                newKey, uid);

        String testId = newKey;
        String testDescription = WindroseApplication.auth.getCurrentUser().getUid() + " " + newKey + " " + email_address.getText();
        LinkedHashMap<String, List<String>> testValues = new LinkedHashMap<>();


        String property0name = Contract.WMODEL_CLASS;
        List<String> property0list = new ArrayList<>();
        property0list.add(property0name);
        property0list.add("" + WFormField.EXCLUDE);
        property0list.add(WModelClass.W_USER.getKey());

        String property1name = "full_name";
        List<String> property1list = new ArrayList<>();
        property1list.add(property1name);
        property1list.add("" + WFormField.TEXT_EDIT);
        property1list.add("Full Name");
        property1list.add(full_name.getText().toString());
        property1list.add("Full Name");

        String property2name = "email_address";
        List<String> property2list = new ArrayList<>();
        property2list.add(property2name);
        property2list.add("" + WFormField.TEXT_EDIT);
        property2list.add("Email Address");
        property2list.add(email_address.getText().toString());
        property2list.add("Email Address");

        String property3name = "uid";
        List<String> property3list = new ArrayList<>();
        property3list.add(property3name);
        property3list.add("" + WFormField.EXCLUDE);
        property3list.add(WindroseApplication.auth.getCurrentUser().getUid());

        testValues.put(Contract.WMODEL_CLASS, property0list);
        testValues.put("1", property1list);
        testValues.put("2", property2list);
        testValues.put("3", property3list);

        DbObject userObject = new DbObject(testId, testDescription, testValues);

        Observable<DBResponse> putNewUser = helper.putDbObject(userObject, WRecyclerViewActivity.SUBMITTED);
        
        putNewUser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        if(dbResponse.getCode() == 200) Log.i(TAG, "onNext: USER ENTERED");
                        finishAndReturn();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showToast() {
        Toast toast = Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT);
        toast.show();

    }

    private void finishAndReturn() {
        setResult(RESULT_OK);
        finish();
    }



}
