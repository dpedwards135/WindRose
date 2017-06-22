package com.davidparkeredwards.windrosetools.wForm;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.activity.WRecyclerViewActivity;
import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WFinalizeButtons implements WFormField {

    private static final String TAG = WFinalizeButtons.class.getSimpleName();


    public String fieldID;
    public double type = FINALIZE_BUTTONS;
    public boolean setCancel;
    public boolean setSave;
    public boolean setSubmit;
    public String displayText;

    public WFinalizeButtons() {}

    public WFinalizeButtons(String fieldID, String displayText, boolean setCancel, boolean setSave, boolean setSubmit) {
        this.fieldID = fieldID;
        this.displayText = displayText;
        this.setCancel = setCancel;
        this.setSave = setSave;
        this.setSubmit = setSubmit;
    }

    @Override
    public int getWRecyclerViewType() {
        return (int) type;
    }

    public boolean isSetCancel() {
        return setCancel;
    }

    public boolean isSetSave() {
        return setSave;
    }

    public boolean isSetSubmit() {
        return setSubmit;
    }

    public void onClickCancel(Context context, WRecyclerBundle bundle) {

    }

    public void onClickSave(Context context, WRecyclerBundle bundle, DbObject dbObject) {
        DbResponseHandler dbResponseHandler = new DbResponseHandler(context);
        DbObject newDbObject = new DbObject(bundle, dbObject, WRecyclerViewActivity.SAVED);
        FirebaseHelper helper = new FirebaseHelper(context);

        io.reactivex.Observable<DBResponse> submitter = helper.putDbObject(newDbObject, WRecyclerViewActivity.SAVED);
        submitter.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        dbResponseHandler.onNext();
                        Log.i(TAG, "onNext: DBOBJECT HAS BEEN SAVED");
                        onComplete();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onClickSubmit(Context context, WRecyclerBundle bundle, DbObject dbObject) {
        DbResponseHandler dbResponseHandler = new DbResponseHandler(context);
        DbObject newDbObject = new DbObject(bundle, dbObject, WRecyclerViewActivity.SUBMITTED);
        FirebaseHelper helper = new FirebaseHelper(context);

        io.reactivex.Observable<DBResponse> submitter = helper.putDbObject(newDbObject, WRecyclerViewActivity.SUBMITTED);
        submitter.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        dbResponseHandler.onNext();
                        Log.i(TAG, "onNext: DBOBJECT HAS BEEN SUBMITTED");

                        io.reactivex.Observable<DBResponse> saveDeleter =
                                helper.deleteDbObject(newDbObject, WRecyclerViewActivity.SAVED);
                        saveDeleter.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<DBResponse>() {
                                               @Override
                                               public void onSubscribe(Disposable d) {

                                               }

                                               @Override
                                               public void onNext(DBResponse dbResponse) {
                                                    dbResponseHandler.onNext();
                                                   Log.i(TAG, "onNext: Item Deleted");
                                                   onComplete();
                                               }

                                               @Override
                                               public void onError(Throwable e) {
                                                   Log.e(TAG, "onError: ", e);
                                               }

                                               @Override
                                               public void onComplete() {

                                               }
                                           });

                                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

