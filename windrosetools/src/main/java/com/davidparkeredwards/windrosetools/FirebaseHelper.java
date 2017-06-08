package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.load.model.ModelCache;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.company.Company;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public static final int WINDROSE_INDEX = 1;
    public static final int COMPANY_INDEX = 2;
    public static final int USER_INDEX = 3;
    public static final int PRECISION_EXACT = 1;
    public static final int PRECISION_CONTAINS = 2;

    public static final String ITEM_NOT_FOUND = "item not found";
    public static final String SUCCESS = "success";
    public static final int OK = 200;
    public static final int FAILED = 400;

    private static final String QA = "qa/";
    private static final String PROD = "prod/";
    private static final String IN_PROGRESS = "in_progress/";
    private static final String WINDROSE = "windrose/";
    private static final String FORMS = "forms/";
    private static final String TYPE = "type/";
    private static final String CLASSES = "classes/";
    private static final String STOCK_TYPE = "stock_type/";
    private static final String INDEX = "index/";
    private static final String SUBMITTED_FORMS = "submitted_forms/";
    private static final String COMPANY = "company/";
    private static final String PERSONAL = "personal/";
    private String baseDbString;
    private String companyString;
    private String companyFormString;
    private String companyClassString; //Classes are classes, or defined Type
    private String companyIndexString;
    private String windroseStockTypeString;
    private String windroseClassString;
    private String userString;
    private String userSavedString;
    private String userSavedCompanyString;
    private String userSavedPersonalString;
    private String userIndexString;
    private String windroseString;
    private String windroseFormsString;
    private String windroseTypesString;
    private String windroseIndexString;
    private String submittedFormsString;


    private String companyId;
    private String wUserId;
    private FirebaseDatabase database;
    private static boolean isDebug;
    private Context ctx;





    /*
    Next:
        Contract for paths X
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

        configureStrings();


    }

    public void configureStrings() {
        wUserId = WindroseApplication.currentWUser.getId;
        companyId = WindroseApplication.getCompanyID().replace("-","") + "/";

        baseDbString = "/v" + BuildConfig.DB_VERSION + "/";
        if (this.isDebug) {
            baseDbString += QA;
        } else {
            baseDbString += PROD;
        }
        companyString = baseDbString + companyId;
        companyFormString = companyString + FORMS;
        companyClassString = companyFormString + CLASSES;
        companyIndexString = companyString + INDEX;
        userString = companyString + userString;
        userSavedString = userString + IN_PROGRESS;
        userSavedCompanyString = userSavedString + COMPANY;
        userSavedPersonalString = userSavedString + PERSONAL;
        userIndexString = userString + INDEX;
        windroseString = baseDbString + WINDROSE;
        windroseIndexString = windroseString + INDEX;
        windroseFormsString = windroseString + FORMS;
        windroseTypesString = windroseFormsString + TYPE;
        windroseStockTypeString = windroseFormsString + STOCK_TYPE;
        windroseClassString = windroseFormsString + CLASSES;
        submittedFormsString = baseDbString + SUBMITTED_FORMS;

        //One big bucket for submitted forms from all companies broken out by WModelClass
        //Individual buckets for saved forms broken out by Company then User
    }

    public Observable<DBResponse> getUniqueIds(int indexType, WModelClass wModelClass, final int precision, String description) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                //Indexes are HashMaps with UniqueIds as keys
                String path;
                switch (indexType) {
                    case WINDROSE_INDEX:
                        path = windroseIndexString + wModelClass.getKey() + "/";
                        break;
                    case COMPANY_INDEX:
                        path = companyIndexString + wModelClass.getKey() + "/";
                        break;
                    case USER_INDEX:
                        path = userIndexString + wModelClass.getKey() + "/";
                    default:
                        path = userIndexString + wModelClass.getKey() + "/";
                }
                DatabaseReference indexRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> uniqueIds = new ArrayList<>();
                        if(dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            for(DataSnapshot child : dataSnapshot.getChildren()) {
                                IndexItem indexItem = child.getValue(IndexItem.class);
                                String indexItemDescription = indexItem.getDescription();
                                if(precision == PRECISION_EXACT
                                        && description.contentEquals(indexItemDescription)) {
                                    uniqueIds.add(child.getKey());
                                } else if(precision == PRECISION_CONTAINS
                                        && indexItemDescription.contains(description)) {
                                    uniqueIds.add(child.getKey());
                                }
                            }
                            if(uniqueIds.isEmpty()) {
                                DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                                e.onNext(dbResponse);
                                return;
                            } else {
                                UniqueIds ids = new UniqueIds(uniqueIds);
                                DBResponse dbResponse = new DBResponse(OK, ids, SUCCESS);
                                e.onNext(dbResponse);
                                return;
                            }
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            e.onNext(dbResponse);
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: Cancelled");
                        e.onError(databaseError.toException());
                    }
                };
                indexRef.addValueEventListener(valueEventListener);
            }
        });
    }

    public Observable<DBResponse> getWForm(String uniqueId, WModelClass wModelClass, boolean isInProgress) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path;

                if(isInProgress) {
                    if(wModelClass.getIsCompanyIndexed()) {
                        path = userSavedCompanyString + companyId + wModelClass.getKey() + "/" + uniqueId;
                    } else {
                        path = userSavedPersonalString + wModelClass.getKey() + "/" + uniqueId;
                    }
                } else {
                    path = submittedFormsString + wModelClass.getKey() + "/" + uniqueId;
                }

                DatabaseReference indexRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            WForm wform = dataSnapshot.getValue(WForm.class);
                            DBResponse dbResponse = new DBResponse(OK, wform, SUCCESS);
                            e.onNext(dbResponse);
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            e.onNext(dbResponse);
                            return;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: Cancelled");
                        e.onError(databaseError.toException());
                    }
                };
                indexRef.addValueEventListener(valueEventListener);
            }
        });
    }










    ///////////////////         Old Stuff        ////////////////////////

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
        WForm form = new WForm();
        return form.fromRecyclerBundle(bundle);
    }

    private class IndexItem {
        private String description;
        private String userId;
        private String companyId;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }
}
