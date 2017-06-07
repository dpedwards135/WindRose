package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.model.company.Company;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by davidedwards on 5/27/17.
 */

public class FirebaseHelper {

    private static final String TAG = FirebaseHelper.class.getSimpleName();

    private FirebaseDatabase database;
    private static boolean isDebug;
    private Context ctx;
    private String baseDbString;
    private String inProgressString;


    /*
    Next:
        Contract for paths
        saveForm();
        submitForm();
        deleteForm();
        getBlankForm();
        getListOfTypes();

        progressSpinner
        userAlert with messages
     */


    public FirebaseHelper(Context ctx) {
        this.ctx = ctx;
        database = WindroseApplication.firebaseDatabase;
        this.isDebug = BuildConfig.DEBUG;
        String currentUser = WindroseApplication.auth.getCurrentUser().getEmail().toString().replace(".", "");
        String companyId = WindroseApplication.getCompanyID().replace("-","");

        if (this.isDebug) {
            baseDbString = "/QA/";
        } else {
            baseDbString = "/Prod/";
        }
        this.inProgressString = (baseDbString + "in_progress" + "/" + currentUser + "/" + companyId + "/");
    }

    public Observable<HashMap> getCompanyIdObservable() {
        return Observable.create(new ObservableOnSubscribe<HashMap>() {
            @Override
            public void subscribe(ObservableEmitter<HashMap> e) throws Exception {
                DatabaseReference ref = database.getReference(baseDbString + "companyIds");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                        Log.i(TAG, "OBSERVABLE onDataChange: Value = " + value.toString());
                        e.onNext(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: Cancelled");

                    }
                };
                ref.addValueEventListener(valueEventListener);
            }
        });
    }



        public void firebaseHelperCheck() {
        DatabaseReference ref = database.getReference(baseDbString + "testNode");
        Date date = new Date();
        ref.setValue("WRTools Check on: " + date.toString());
        Log.i(TAG, "firebaseHelperCheck: Adding Listener");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.i(TAG, "onDataChange: Value = " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i(TAG, "onCancelled: Cancelled");
            }
        });
    }

    public void setNewCompanyId(String companyName) {

        //Set company ID by getting all companyIds then CompanyId : CompanyName
        DatabaseReference ref = database.getReference(baseDbString + "companyIds");
        Date date = new Date();
        ref.push().setValue(companyName);
        Log.i(TAG, "setNewCompanyId: Adding Listener");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                Log.i(TAG, "onDataChange: Value = " + value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i(TAG, "onCancelled: Cancelled");
            }
        });
    }


    public ValueEventListener getCompanyIds() {

        DatabaseReference ref = database.getReference(baseDbString + "companyIds");


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> value = (HashMap<String, String>) dataSnapshot.getValue();
                Log.i(TAG, "onDataChange: Value = " + value.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: Cancelled");

            }
        };
        ref.addValueEventListener(valueEventListener);

        return valueEventListener;
    }

    //Caching methods for work in progress
    public void saveWROBundle(WRecyclerBundle bundle){
        DatabaseReference ref = database.getReference(inProgressString + bundle.getClassKey());
        WForm sbundle = serializeBundle(bundle);
        Log.i(TAG, "saveWROBundle: " + sbundle.classKey);
        ref.setValue(sbundle);
    }

    public void clearWROBundle(String classKey) {
        DatabaseReference ref = database.getReference(inProgressString + classKey);
        ref.removeValue();
    }

    public Observable<WRecyclerBundle> getSavedWROBundle(String classKey) {
        return Observable.create(new ObservableOnSubscribe<WRecyclerBundle>() {
            @Override
            public void subscribe(ObservableEmitter<WRecyclerBundle> e) throws Exception {

                DatabaseReference ref = database.getReference(
                        inProgressString + classKey);
                Log.i(TAG, "subscribe: Path: " + inProgressString+classKey);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            Log.i(TAG, "onDataChange: SNAPSHOT: " + dataSnapshot.getValue().toString());
                            WForm sbundle =
                                    dataSnapshot.getValue(WForm.class);
                            Log.i(TAG, "onDataChange: TEST: " + sbundle.classKey);
                            WRecyclerBundle bundle = new WRecyclerBundle(sbundle);
                            e.onNext(bundle);
                        } else {
                            Log.i(TAG, "onDataChange: Value was null");
                            Company company = new Company();
                            e.onNext(company.getWRecyclerObjectsEditable());
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: Cancelled");
                        e.onError(databaseError.toException());
                    }
                };
                ref.addValueEventListener(valueEventListener);
            }
        });
    }


    public void addToSubmissionQueue(WRecyclerBundle bundle) {
        DatabaseReference ref = database.getReference(baseDbString + bundle.getSubmissionKey() + bundle.getClassKey());
        ref.push().setValue(bundle);
    }

    public WForm serializeBundle(WRecyclerBundle bundle) {
        return new WForm(bundle);
    }
}
