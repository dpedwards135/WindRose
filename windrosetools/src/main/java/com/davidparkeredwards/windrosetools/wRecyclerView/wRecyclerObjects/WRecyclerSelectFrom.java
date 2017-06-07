package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerSelectFrom implements WRecyclerObject {

    public String fieldID;
    public int viewType = SELECT_FROM;
    public boolean isEditable;
    public String text;
    public ArrayList<String> selectableValues;
    public int selectedValue;
    public String spinnerPrompt;

    public WRecyclerSelectFrom() {}

    public WRecyclerSelectFrom(String fieldID, boolean isEditable, String text, ArrayList<String> selectableValues, int selectedValue, String spinnerPrompt) {
        this.fieldID = fieldID;
        this.isEditable = isEditable;
        this.text = text;
        this.selectableValues = selectableValues;
        this.selectedValue = selectedValue;
        this.spinnerPrompt = spinnerPrompt;
    }

    @Override
    public int getWRecyclerViewType() {
        return viewType;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getValues() {
        return selectableValues;
    }

    public void setSelectedValue(int position) {
        selectedValue = position;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public String getSpinnerPrompt() {
        return spinnerPrompt;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }
}
