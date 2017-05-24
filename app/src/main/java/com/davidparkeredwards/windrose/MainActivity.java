package com.davidparkeredwards.windrose;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String vehicle;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 123;
    private String currentUser;
    private FirebaseAuth auth;
    private String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Log.i(TAG, "onCreate: User is signed in as " + auth.getCurrentUser().getDisplayName());
            currentUser = auth.getCurrentUser().getDisplayName().toString();
        } else {
            Log.i(TAG, "onCreate: User is not signed in");
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), RC_SIGN_IN);
        }

        getCompany();
        Location location = getLocation();
        checkIntoVehicle();
        if (location != null) {
            saveLocationToFirebase(location);
            Log.i(TAG, "onCreate: Saving location");
        } else {

            Toast toast = Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT);
            Log.i(TAG, "onCreate: No location");
        }
    }

    protected void getCompany() {
        companyName = "Example Company";
    }

    protected Location getLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                "android.permission.ACCESS_FINE_LOCATION");

        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            String locationProvider = LocationManager.GPS_PROVIDER;

            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            return lastKnownLocation;
        } else {
            //Add permission request here with recursive getLocation call
            Log.i(TAG, "getLocation: No location permission");
        }
        Log.i(TAG, "getLocation: Returning null");
        return null;
    }

    protected void checkIntoVehicle() {
        //Flesh this out for the driver to check in to any vehicle. This should also trigger location tracking.
        //When driver checks out tracking ends. Check out should include PTA setting with option to set no PTA.
        //Add beacon info much later
        //Save all user settings to Firebase and/or sharedPreferences
        vehicle = "123";
    }

    protected void saveLocationToFirebase(Location location) {
        //Set this up as service to run triggered by FCM
        //Saves ping to both last_known_locations and vehicle_history
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference vehicleHistoryRef = database.getReference("/" + companyName +
                "/location_pings/" + "vehicle_history/" + vehicle);
        DatabaseReference lastKnownLocationRef = database.getReference("/" + companyName +
                "/location_pings/last_known_locations/" + vehicle);
        String dateString = new Date().toString();
        VehiclePing vehiclePing = new VehiclePing(location.toString(), dateString, currentUser);
        vehicleHistoryRef.push().setValue(vehiclePing);
        lastKnownLocationRef.setValue(vehiclePing);

        database.getReference("dbCheck1").setValue("Check2");

        // Read from the database
        lastKnownLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                /*
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                */
                Log.i(TAG, "onDataChange: Changed");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
