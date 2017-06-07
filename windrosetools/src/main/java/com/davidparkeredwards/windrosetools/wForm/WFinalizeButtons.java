package com.davidparkeredwards.windrosetools.wForm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WFinalizeButtons implements WFormField {

    public String fieldID;
    public int type = FINALIZE_BUTTONS;
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
        return type;
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
        helper.clearWROBundle(bundle.getClassKey());
        Intent intent = new Intent();
        String packageName = context.getApplicationContext().getPackageName();
        ComponentName componentName = new ComponentName(packageName,
                packageName + ".WMainActivity");
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    public void onClickSave(Context context, WRecyclerBundle bundle) {
        FirebaseHelper helper = new FirebaseHelper(context);
        helper.saveWROBundle(bundle);
    }

    public void onClickSubmit(Context context, WRecyclerBundle bundle) {
        FirebaseHelper helper = new FirebaseHelper(context);
        helper.addToSubmissionQueue(bundle);

    }
}

