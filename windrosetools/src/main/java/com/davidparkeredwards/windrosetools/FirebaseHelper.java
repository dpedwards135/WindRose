package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.model.IndexItem;
import com.davidparkeredwards.windrosetools.model.ModelObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.journey.DbObjectList;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

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
    public static final String SAVED = "saved/";

    private static final String QA = "qa/";
    private static final String PROD = "prod/";
    private static final String IN_PROGRESS = "in_progress/";
    private static final String WINDROSE = "windrose/";
    private static final String FORMS = "forms/";
    private static final String MODEL = "model/";
    private static final String TYPE = "type/";
    private static final String CLASSES = "classes/";
    private static final String STOCK_TYPE = "stock_type/";
    private static final String INDEX = "index/";
    private static final String SUBMITTED_FORMS = "submitted_forms/";
    private static final String COMPANY = "company/";
    private static final String PERSONAL = "personal/";
    private static final String USER_ID = "user_id/";

    private String baseDbString;
    private String formsString;
    private String modelString;
    private String userIdString;
    private String companyIndexString;
    private String userIndexString;
    private String windroseIndexString;
    private String userSaveIndex;


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
        if(WindroseApplication.currentWUserId != null) {
            wUserId = WindroseApplication.currentWUserId + "/";
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

        //right now I'm only worried about saving and retrieving forms. All I need is a Forms bucket
        //and indices

        userIdString = USER_ID;
        modelString = baseDbString + MODEL;
        formsString = baseDbString + FORMS;
        companyIndexString = baseDbString + INDEX + companyId;
        windroseIndexString = baseDbString + INDEX + WINDROSE;
        userIndexString = baseDbString + INDEX + wUserId;
        userSaveIndex = baseDbString + INDEX + wUserId + SAVED + companyId;
    }

    ////////////////////   Model Objects Direct Manipulation ////////////////////

    public String getObjectPathWithUniqueID(String uniqueId, WModelClass wModelClass, int listType) {
        String path;

        if(wModelClass == WModelClass.W_USER) {
            path = userIdString + uniqueId;
        } else {
            path = modelString + wModelClass.getKey() + listType + "/" + uniqueId;
        }

        return path;
    }

    public String getNewObjectKey(WModelClass wModelClass, int listType) {
        String path = getObjectPathWithUniqueID("", wModelClass, listType);
        DatabaseReference ref = database.getReference(path);
        String newId = ref.push().getKey();
        Log.i(TAG, "getNewId: " + newId);
        return newId;
    }

    public Observable<DBResponse> putDbObject(DbObject dbObject, int listType) {
        //DbObject contains all the info to know where it goes.

        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {
                String path = getObjectPathWithUniqueID(dbObject.getUniqueID(),
                        WModelClass.findWModelFromKey(dbObject.getwModelClassKey()), listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
                            e.onNext(dbResponse);
                            Log.i(TAG, "onDataChange: PUTWMODELOBJECT");
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            Log.i(TAG, "onDataChange: PUTOBJECT - FAILED, ITEM NOT FOUND");
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
                objectRef.addValueEventListener(valueEventListener);
                objectRef.setValue(dbObject);
            }
        });
    }

    public Observable<DBResponse> getDbObject(String uniqueId, WModelClass wModelClass, int listType) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {
                String path = getObjectPathWithUniqueID(uniqueId, wModelClass, listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {

                            DbObject dbObject = dataSnapshot.getValue(DbObject.class);
                            DBResponse dbResponse = new DBResponse(OK, dbObject, SUCCESS);
                            e.onNext(dbResponse);
                            Log.i(TAG, "onDataChange: GOTWMODELOBJECT");
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            Log.i(TAG, "onDataChange: GOTOBJECT - FAILED, ITEM NOT FOUND");
                            e.onNext(dbResponse);
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: Cancelled", databaseError.toException());
                        e.onError(databaseError.toException());
                    }
                };
                objectRef.addValueEventListener(valueEventListener);
            }
        });
    }

    public Observable<DBResponse> getDbObjectList(WModelClass wModelClass, int listType) {
        Log.i(TAG, "getDbObjectList: ");
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {
                String path = getObjectPathWithUniqueID("", wModelClass, listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i(TAG, "onDataChange: ");
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            ArrayList<DbObject> list = new ArrayList<>();
                            //FIX THIS SO THAT IT CREATES A LIST PROPERLY
                            Iterable<DataSnapshot> alist = dataSnapshot.getChildren();
                            for (DataSnapshot child : alist) {
                                DbObject dbObject = child.getValue(DbObject.class);
                                list.add(dbObject);
                            }
                            DbObjectList dbObjectList = new DbObjectList(list);
                            DBResponse dbResponse = new DBResponse(OK, dbObjectList, SUCCESS);
                            e.onNext(dbResponse);
                            e.onComplete();
                            Log.i(TAG, "onDataChange: GOTWMODELOBJECT");
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            Log.i(TAG, "onDataChange: GOTOBJECT - FAILED, ITEM NOT FOUND");
                            e.onNext(dbResponse);
                            e.onComplete();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: Cancelled", databaseError.toException());
                        e.onError(databaseError.toException());
                    }
                };
                objectRef.addValueEventListener(valueEventListener);
            }
        });
    }

    public Observable<DBResponse> deleteDbObject(DbObject dbObject, int listType) {
        //DbObject contains all the info to know where it goes.

        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {
                String path = getObjectPathWithUniqueID("",
                        WModelClass.findWModelFromKey(dbObject.getwModelClassKey()), listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
                            e.onNext(dbResponse);
                            Log.i(TAG, "onDataChange: PUTWMODELOBJECT");
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            Log.i(TAG, "onDataChange: PUTOBJECT - FAILED, ITEM NOT FOUND");
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
                objectRef.addValueEventListener(valueEventListener);
                objectRef.child(dbObject.getUniqueID()).removeValue();
            }
        });
    }


    public Observable<DBResponse> putWModelObject(ModelObject modelObject, int listType) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path = getObjectPathWithUniqueID(modelObject.getKey(), modelObject.getWModelClass(), listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
                            e.onNext(dbResponse);
                            Log.i(TAG, "onDataChange: PUTWMODELOBJECT");
                            //Later change this to a more universal indexer
                            /*
                            if(modelObject.getWModelClass() == WModelClass.W_USER) {
                                IndexItem indexItem = new IndexItem(modelObject.getDescription(),
                                        ((WUser) modelObject).getWUserId(), WindroseApplication.getCompanyID());
                                DatabaseReference userIndex = database.getReference(USER_ID);
                                userIndex.child(((WUser) modelObject).getWUserId()).setValue(indexItem);
                            }
                            */
                            return;
                        } else {
                            DBResponse dbResponse = new DBResponse(FAILED, null, ITEM_NOT_FOUND);
                            Log.i(TAG, "onDataChange: PUTOBJECT - FAILED, ITEM NOT FOUND");
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
                objectRef.addValueEventListener(valueEventListener);
                objectRef.setValue(modelObject.toDbObject());
            }
        });
    }

    public Observable<DBResponse> getWModelObjectHashMap(WModelClass wModelClass, String uniqueId, int listType) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path = getObjectPathWithUniqueID(uniqueId, wModelClass, listType);

                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {


                            LinkedHashMap<String, List<String>> dbObjectValues = (LinkedHashMap<String, List<String>>) dataSnapshot.getValue();
                            //HashMap<String, String> modelObjectValue = (HashMap<String, String>) dataSnapshot.getValue();
                            String dbObjectKey = dataSnapshot.getKey();
                            DbObject dbObject = new DbObject(dbObjectKey, "Null Description", dbObjectValues);
                            DBResponse dbResponse = new DBResponse(OK, dbObject, SUCCESS);
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
                objectRef.addValueEventListener(valueEventListener);}
        });
    }

    public Single<DBResponse> checkPreExistingUser(String description) {
        return Single.create(new SingleOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(SingleEmitter<DBResponse> e) throws Exception {
                Log.i(TAG, "subscribe: ");

                String path = userIdString;

                Log.i(TAG, "subscribe: Path " + path);
                DatabaseReference objectRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i(TAG, "onDataChange: FIRST");
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {

                            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                            for(DataSnapshot child : children) {
                                IndexItem indexItem = child.getValue(IndexItem.class);
                                if (indexItem.getDescription().contains(description)) {
                                    Log.i(TAG, "onDataChange: email already exists");
                                    ArrayList<String> list = new ArrayList();
                                    list.add(child.getKey());
                                    UniqueIds uniqueIds = new UniqueIds(list);
                                    e.onSuccess(new DBResponse(OK, uniqueIds, "Object found"));
                                    return;
                                } else {
                                    //e.onSuccess(new DBResponse(FAILED, null, "Object not found"));
                                    Log.i(TAG, "onDataChange: email does not exist");
                                }
                            }
                            e.onSuccess(new DBResponse(FAILED, null, "Object not found"));
                        } else {
                            e.onSuccess(new DBResponse(FAILED, null, "Object not found"));
                            Log.i(TAG, "onDataChange: email does not exist");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i(TAG, "onCancelled: " + databaseError.toString());

                    }
                };

                objectRef.addValueEventListener(valueEventListener);
                Log.i(TAG, "subscribe: Added Event Listener to PREEXISTING");
            }
        });
    }

}
