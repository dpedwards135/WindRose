package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerSelectFrom implements WRecyclerObject {

    private String fieldID;
    private int viewType = SELECT_FROM;
    private boolean isEditable;
    private String text;
    private ArrayList<String> selectableValues;
    private int selectedValue;
    private String spinnerPrompt;

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
