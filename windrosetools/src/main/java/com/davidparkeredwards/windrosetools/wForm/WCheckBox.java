package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WCheckBox implements WFormField {

    public String fieldID;
    public String text = "Test text string";
    public boolean trueOrFalse;
    public double viewType = WFormField.CHECKBOX;
    public boolean isEditable = false;

    public WCheckBox() {}

    public WCheckBox(String fieldID, String text, boolean trueOrFalse) {
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
        return (int) viewType;
    }

    public void setTrueOrFalse(boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }
}
