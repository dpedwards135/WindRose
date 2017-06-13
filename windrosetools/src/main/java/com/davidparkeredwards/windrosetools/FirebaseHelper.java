package com.davidparkeredwards.windrosetools;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.model.ModelObject;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbResponseHandler;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private String baseDbString;
    private String formsString;
    private String modelString;
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

        //right now I'm only worried about saving and retrieving forms. All I need is a Forms bucket
        //and indices

        modelString = baseDbString + MODEL;
        formsString = baseDbString + FORMS;
        companyIndexString = baseDbString + INDEX + companyId;
        windroseIndexString = baseDbString + INDEX + WINDROSE;
        userIndexString = baseDbString + INDEX + wUserId;
        userSaveIndex = baseDbString + INDEX + wUserId + SAVED + companyId;
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
                        break;
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

                String path = getFormPathWithUniqueID(uniqueId, wModelClass);

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

    public String getNewId(WModelClass wModelClass) {
        String path = getFormPathWithUniqueID("", wModelClass);
        DatabaseReference ref = database.getReference(path);
        String newId = ref.push().getKey();
        Log.i(TAG, "getNewId: " + newId);
        return newId;
    }

    public String getFormPathWithUniqueID(String uniqueId, WModelClass wModelClass) {
        String path;

        path = formsString + wModelClass.getKey() + "/" + uniqueId;

        return path;
    }

    public Observable<DBResponse> putWForm(WForm wForm) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path = getFormPathWithUniqueID(wForm.getUniqueId(), wForm.getWModelClass());

                DatabaseReference formRef = database.getReference(path);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                            DBResponse dbResponse = new DBResponse(OK, null, SUCCESS);
                            e.onNext(dbResponse);
                            indexAndProcessForm(wForm);
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
                formRef.addValueEventListener(valueEventListener);
                Log.i(TAG, "subscribe: FORM: " + wForm.toString());
                formRef.setValue(wForm);
            }
        });
    }


    public Observable<DBResponse> indexKey(WForm wForm, WModelClass wModelClass) {
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

                IndexItem indexItem = new IndexItem(wForm.description, wForm.userId, wForm.companyId);

                if(wForm.isSubmitted) {
                    ArrayList<DatabaseReference> indexRefs = new ArrayList<>();

                    if (wModelClass.getIsCompanyIndexed()) {
                        DatabaseReference compIndex = database.getReference(companyIndexString + wModelClass.getKey() + "/");
                        indexRefs.add(compIndex);
                    }
                    if (wModelClass.getIsWindroseIndexed()) {
                        DatabaseReference wIndex = database.getReference(windroseIndexString + wModelClass.getKey() + "/");
                        indexRefs.add(wIndex);
                    }
                    if (wModelClass.getIsUserIndexed()) {
                        DatabaseReference userIndex = database.getReference(userIndexString + wModelClass.getKey() + "/");
                        indexRefs.add(userIndex);
                    }
                    for(DatabaseReference ref : indexRefs) {
                        ref.addValueEventListener(valueEventListener);
                        ref.child(wForm.uniqueId).setValue(indexItem);
                    }
                } else {
                    DatabaseReference savedIndex = database.getReference(userSaveIndex + wModelClass.getKey());
                    savedIndex.removeValue();
                    savedIndex.child(wForm.uniqueId).setValue(indexItem);
                }
            }
        });
    }

    private void indexAndProcessForm(WForm wform) {

        DbResponseHandler handler = new DbResponseHandler(ctx);

        Observable<DBResponse> indexObservable = indexKey(wform, wform.getWModelClass());
        indexObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        handler.onSubscribe();
                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {

                        handler.onNext();
                    }

                    @Override
                    public void onError(Throwable e) {
                        handler.onError();
                    }

                    @Override
                    public void onComplete() {
                        handler.onComplete();
                    }
                });
    }

    ////////////////////   Model Objects Direct Manipulation ////////////////////

    public String getObjectPathWithUniqueID(String uniqueId, WModelClass wModelClass) {
        String path;

        path = modelString + wModelClass.getKey() + "/" + uniqueId;

        return path;
    }

    public String getNewObjectKey(WModelClass wModelClass) {
        String path = getFormPathWithUniqueID("", wModelClass);
        DatabaseReference ref = database.getReference(path);
        String newId = ref.push().getKey();
        Log.i(TAG, "getNewId: " + newId);
        return newId;
    }

    public Observable<DBResponse> putWModelObject(ModelObject modelObject) {
        return Observable.create(new ObservableOnSubscribe<DBResponse>() {
            @Override
            public void subscribe(ObservableEmitter<DBResponse> e) throws Exception {

                String path = getObjectPathWithUniqueID(modelObject.getKey(), modelObject.getWModelClass());

                DatabaseReference objectRef = database.getReference(path);

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
                objectRef.addValueEventListener(valueEventListener);
                objectRef.setValue(modelObject.getValue());
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

}
