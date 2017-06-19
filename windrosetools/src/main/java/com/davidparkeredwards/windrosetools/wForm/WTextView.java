package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WTextView implements WFormField {

    public String fieldID;
    public String text = "Test text string";
    public String selectedValue;
    public double viewType = WFormField.TEXT_VIEW;
    public boolean isEditable = false;

    public WTextView() {}

    public WTextView(String fieldID, String text, String selectedValue) {
        this.fieldID = fieldID;
        this.text = text;
        this.selectedValue = selectedValue;
        this.text = text + ": " + selectedValue; //CHECK
    }



    public String getText() {
        return text;
    }

    @Override
    public int getWRecyclerViewType() {
        return (int) viewType;
    }

}
