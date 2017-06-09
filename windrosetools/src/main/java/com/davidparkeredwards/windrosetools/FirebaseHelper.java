package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public static final String BLANK = "blank";
    public static final String SAVED = "saved";

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
        if(WindroseApplication.currentWUser != null) {
            wUserId = WindroseApplication.currentWUser.getWUserId() + "/";
        } else {
            wUserId = "Anonymous/";
        }
        companyId = WindroseApplication.getCompanyID().replace("-","") + "/";

        baseDbString = "/v" + BuildConfig.DB_VERSION + "/";
        if (this.isDebug) {
            baseDbString += QA;
        } else {
            baseDbString += PROD;
        }

        companyString = baseDbString + companyId;
        companyFormString = baseDbString + companyId + FORMS;
        companyClassString = baseDbString + companyId + FORMS + CLASSES;
        companyIndexString = baseDbString + companyId + INDEX;
        userString = baseDbString + wUserId;
        userSavedString = baseDbString + wUserId + IN_PROGRESS + companyId;
        userSavedPersonalString = baseDbString + wUserId + IN_PROGRESS + PERSONAL;
        userIndexString = baseDbString + wUserId + INDEX;
        windroseString = baseDbString + WINDROSE;
        windroseIndexString = baseDbString + WINDROSE + INDEX;
        windroseFormsString = baseDbString + WINDROSE + FORMS;
        windroseTypesString = baseDbString + WINDROSE + FORMS + TYPE;
        windroseClassString = baseDbString + WINDROSE + FORMS + CLASSES;
        windroseStockTypeString = baseDbString + WINDROSE + FORMS + CLASSES + STOCK_TYPE;
        submittedFormsString = baseDbString + SUBMITTED_FORMS;

        Log.i(TAG, "configureStrings: " + userSavedString);
        //One big bucket for submitted forms from all companies broken out by WModelClass
        //Individual buckets for saved forms broken out by Company then User
    }


    //GET Methods

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

                String path = getPathWithUniqueID(uniqueId, wModelClass, isInProgress);

                /*
                if(isInProgress) {
                    if(wModelClass.getIsCompanyIndexed()) {
                        path = userSavedString + companyId + wModelClass.getKey() + "/" + uniqueId;
                    } else {
                        path = userSavedPersonalString + wModelClass.getKey() + "/" + uniqueId;
                    }
                } else {
                    path = submittedFormsString + wModelClass.getKey() + "/" + uniqueId;
                }
                */
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

    //Save methods - Put, Post(push/set/save ID - Should always be indexed when making an addition), update, delete
        //Next: Create new WUser and post to DB, with return to Login

    private String getNewId(WModelClass wModelClass, boolean isInProgress) {
        String path = getPathWithUniqueID("", wModelClass, isInProgress);
        DatabaseReference ref = database.getReference(path);
        String newId = ref.push().getKey();
        return newId;
    }

    private String getPathWithUniqueID(String uniqueId, WModelClass wModelClass, boolean isInProgress) {
        String path;
        if (isInProgress) {
            if (wModelClass.getIsCompanyIndexed()) {
                path = userSavedString + wModelClass.getKey() + "/" + uniqueId;
                Log.i(TAG, "subscribe: Path: " + path);
            } else {
                path = userSavedPersonalString + wModelClass.getKey() + "/" + uniqueId;
                Log.i(TAG, "subscribe: Path: " + path);
            }
        } else {
            path = submittedFormsString + wModelClass.getKey() + "/" + uniqueId;
            Log.i(TAG, "subscribe: Path: " + path);
        }
        return path;
    }

    public Observable<DBResponse> putWForm(WForm wForm, String uniqueId, WModelClass wModelClass, boolean isInProgress) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path = getPathWithUniqueID(uniqueId, wModelClass, isInProgress);

                DatabaseReference indexRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
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
                Log.i(TAG, "subscribe: FORM: " + wForm.toString());
                indexRef.setValue(wForm);
            }
        });
    }


    public Observable<DBResponse> indexNewKeyAndSubmitForm(WForm wForm, WModelClass wModelClass) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
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

                String path;
                path = submittedFormsString + wModelClass.getKey() + "/";
                DatabaseReference indexRef = database.getReference(path);
                String newKey = indexRef.push().getKey();
                DatabaseReference newRef = indexRef.child(newKey);
                newRef.addValueEventListener(valueEventListener);
                newRef.setValue(wForm);

                IndexItem indexItem = new IndexItem(wForm.description, wForm.userId, wForm.companyId);
                putIndexItem(indexItem, newKey, wModelClass);

            }
        });
    }

    public Observable<DBResponse> putIndexItem(IndexItem indexItem, String uniqueId, WModelClass wModelClass) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
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

                if(wModelClass.getIsWindroseIndexed()) {
                    String path = windroseIndexString + wModelClass.getKey() + "/";
                    DatabaseReference indexRef = database.getReference(path);
                    indexRef.addValueEventListener(valueEventListener);
                    indexRef.child(uniqueId).setValue(indexItem);
                }
                if(wModelClass.getIsCompanyIndexed()) {
                    String path = companyIndexString + wModelClass.getKey() + "/";
                    DatabaseReference indexRef = database.getReference(path);
                    indexRef.addValueEventListener(valueEventListener);
                    indexRef.child(uniqueId).setValue(indexItem);
                }
                if(wModelClass.getIsUserIndexed()) {
                    String path = userIndexString + wModelClass.getKey() + "/";
                    DatabaseReference indexRef = database.getReference(path);
                    indexRef.addValueEventListener(valueEventListener);
                    indexRef.child(uniqueId).setValue(indexItem);
                }
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


    private class IndexItem {
        private String description;
        private String userId;
        private String companyId;

        private IndexItem(String description, String userId, String companyId) {
            this.description = description;
            this.userId = userId;
            this.companyId = companyId;
        }

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

    public String getUserSavedString() {
        return userSavedString;
    }
}
