package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerCheckBox implements WRecyclerObject {

    public String fieldID;
    public String text = "Test text string";
    public boolean trueOrFalse;
    public int viewType = WRecyclerObject.CHECKBOX;
    public boolean isEditable = false;

    public WRecyclerCheckBox() {}

    public WRecyclerCheckBox(String fieldID, String text, boolean trueOrFalse) {
        this.fieldID = fieldID;
        this.text = text;
        this.trueOrFalse = trueOrFalse;
    }

    public String getText() {
        return text;
    }

    public boolean getTrueOrFalse() { return trueOrFalse; }

    @Override
    public int getWRecyclerViewType() {
        return viewType;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
    }

    public void setTrueOrFalse(boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }
}
