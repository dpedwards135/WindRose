package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerCheckBox implements WRecyclerObject {

    private String fieldID;
    private String text = "Test text string";
    private boolean trueOrFalse = false;
    private int viewType = WRecyclerObject.CHECKBOX;
    private boolean isEditable = false;

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
