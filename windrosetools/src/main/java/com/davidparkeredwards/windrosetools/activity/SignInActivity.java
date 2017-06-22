package com.davidparkeredwards.windrosetools.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.journey.DbObjectList;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbResponseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 6/14/17.
 */

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    FirebaseHelper helper = new FirebaseHelper(this);
    DbResponseHandler dbResponseHandler = new DbResponseHandler(this);
    TextInputEditText password;
    TextInputEditText email_address;
    Button submitButton;
    Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //Set up to sign up view. When user submits it gets the call back from Firebase and then validates and creates a new user in the db

        password = (TextInputEditText) findViewById(R.id.si_password);
        email_address = (TextInputEditText) findViewById(R.id.si_email_address_box);
        submitButton = (Button) findViewById(R.id.si_signin_button);
        createAccountButton = (Button) findViewById(R.id.si_create_account_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSubmit();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCreateAccount();
            }
        });
    }

    private void clickCreateAccount() {
        Intent intent = new Intent();
        intent.setClass(this, SignUp.class);
        startActivityForResult(intent, 111);
    }

    private void clickSubmit() {
        Log.i(TAG, "clickSubmit: ");
        if(email_address.getText() != null
                && password.getText() != null) {
            //createFBUser();
            checkForExistingUser();
        }
    }

    private void signInFBUser() {
        Log.i(TAG, "signInFBUser: ");
        WindroseApplication.auth.signInWithEmailAndPassword(email_address.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.i(TAG, "onComplete: Signin Successful");
                            finishAndReturn();
                        } else {
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
                                signInFBUser();
                                Log.i(TAG, "onNext: Contains email");
                                onComplete();
                                return;
                            } else {
                                Log.i(TAG, "onNext: does not contain email");
                            }
                        }
                        showToast();
                        onComplete();
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
        Toast toast = Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT);
        toast.show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            finishAndReturn();
        }
    }

    private void finishAndReturn() {
        setResult(RESULT_OK);
        finish();
    }

}
