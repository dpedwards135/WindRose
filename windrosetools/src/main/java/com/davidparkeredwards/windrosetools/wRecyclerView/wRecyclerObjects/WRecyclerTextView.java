package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerTextView implements WRecyclerObject {

    private String text = "Test text string";
    private int viewType = WRecyclerObject.TEXT_VIEW;
    private boolean isEditable = false;


    public WRecyclerTextView() {}

    public WRecyclerTextView(String text) {
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

}
