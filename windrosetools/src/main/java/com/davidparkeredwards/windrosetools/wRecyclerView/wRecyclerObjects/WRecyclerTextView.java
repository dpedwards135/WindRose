package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerTextView implements WRecyclerObject {

    public String fieldID;
    public String text = "Test text string";
    public int viewType = WRecyclerObject.TEXT_VIEW;
    public boolean isEditable = false;

    public WRecyclerTextView(String fieldID, String text) {
        this.fieldID = fieldID;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getWRecyclerViewType() {
        return viewType;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }
}
