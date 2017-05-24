package com.davidparkeredwards.windrosecustomer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainCustomerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainCustomerActivity.class.getSimpleName();

    Spinner tripTypeSpinner;
    ArrayAdapter tripTypeSpinnerAdapter;
    ArrayList<String> tripTypeArrayList;
    private FirebaseAuth auth;
    private String currentUser;
    private static final int RC_SIGN_IN = 123;
    String companyName;
    FirebaseDatabase database;
    DatabaseReference baseDbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);

        //LATER: Turn these initializations into separate library to be used across apps. That will
        //help in making sweeping db changes and security

        //AUTHORIZATION INITIALIZATION
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Log.i(TAG, "onCreate: User is signed in as " + auth.getCurrentUser().getDisplayName());
            currentUser = auth.getCurrentUser().getDisplayName().toString();
        } else {
            Log.i(TAG, "onCreate: User is not signed in");
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
        }

        //COMPANY INITIALIZATION
        companyName = "Example Company";

        //DB INITIALIZATION
        database = FirebaseDatabase.getInstance();
        baseDbReference = database.getReference("/" + companyName + "/");

        //INITIALIZE ACTIVITY LAYOUT

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Shuttle");
        setSupportActionBar(toolbar);

        //Initialize Spinner, SpinnerAdapter, and tripTypeArrayList and setItineraryView
        tripTypeArrayList = new ArrayList<>();
        tripTypeArrayList.add("Trip1"); //Fix to download tripTypes from Firebase
        tripTypeArrayList.add("Trip2");
        tripTypeSpinner = (Spinner) findViewById(R.id.trip_type_spinner);
        tripTypeSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        tripTypeSpinnerAdapter.addAll(tripTypeArrayList);
        tripTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripTypeSpinner.setAdapter(tripTypeSpinnerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
