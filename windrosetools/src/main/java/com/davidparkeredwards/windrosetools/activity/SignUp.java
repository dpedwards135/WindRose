package com.davidparkeredwards.windrosetools.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davidparkeredwards.windrosetools.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Set up to sign up view. When user submits it gets the call back from Firebase and then validates and creates a new user in the db

    }
}
