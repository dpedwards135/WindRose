package com.davidparkeredwards.windrosetools.wForm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

import io.reactivex.Observable;
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
    public boolean isEditable = false;
    public boolean setCancel;
    public boolean setSave;
    public boolean setSubmit;

    public WFinalizeButtons() {}

    public WFinalizeButtons(String fieldID, boolean setCancel, boolean setSave, boolean setSubmit) {
        this.fieldID = fieldID;
        this.setCancel = setCancel;
        this.setSave = setSave;
        this.setSubmit = setSubmit;
    }

    @Override
    public int getWRecyclerViewType() {
        return (int) type;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
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

    @Override
    public String getFieldID() {
        return fieldID;
    }

    public void onClickCancel(Context context, WRecyclerBundle bundle) {
        FirebaseHelper helper = new FirebaseHelper(context);
        Intent intent = new Intent();
        String packageName = context.getApplicationContext().getPackageName();
        ComponentName componentName = new ComponentName(packageName,
                packageName + ".WMainActivity");
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    public void onClickSave(Context context, WRecyclerBundle bundle) {
        Log.i(TAG, "onClickSave: ");
        FirebaseHelper helper = new FirebaseHelper(context);

        WForm wForm = WForm.fromRecyclerBundle(bundle);
        Log.i(TAG, "onClickSave: WFORM: " + wForm.toString());
        DbResponseHandler dbResponseHandler = new DbResponseHandler(context);

        Observable<DBResponse> onClickSaveObservable =
                helper.putWForm(wForm);
        onClickSaveObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dbResponseHandler.onSubscribe();
                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        dbResponseHandler.onNext();

                    }

                    @Override
                    public void onError(Throwable e) {
                        dbResponseHandler.onError();
                    }

                    @Override
                    public void onComplete() {
                        dbResponseHandler.onComplete();
                    }
                });
    }

    public void onClickSubmit(Context context, WRecyclerBundle bundle) {
        FirebaseHelper helper = new FirebaseHelper(context);
        WForm wForm = new WForm().fromRecyclerBundle(bundle);
        helper.putWForm(wForm);

        //helper.putWForm(wForm, FirebaseHelper.SAVED, bundle.getwModelClass(), true);

    }
}

