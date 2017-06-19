package com.davidparkeredwards.windrosetools.wForm;

import android.content.Context;

import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

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

    public void onClickSave(Context context, WRecyclerBundle bundle) {

    }

    public void onClickSubmit(Context context, WRecyclerBundle bundle) {

    }
}

