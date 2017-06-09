package com.davidparkeredwards.windrosetools.wForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WSelectFrom implements WFormField {

    public String fieldID;
    public double viewType = SELECT_FROM;
    public boolean isEditable;
    public String text;
    public List<String> selectableValues;
    public double selectedValue;
    public String spinnerPrompt;

    public WSelectFrom() {}

    public WSelectFrom(String fieldID, boolean isEditable, String text, ArrayList<String> selectableValues, int selectedValue, String spinnerPrompt) {
        this.fieldID = fieldID;
        this.isEditable = isEditable;
        this.text = text;
        this.selectableValues = selectableValues;
        this.selectedValue = selectedValue;
        this.spinnerPrompt = spinnerPrompt;
    }

    @Override
    public int getWRecyclerViewType() {
        return (int) viewType;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getValues() {
        return (ArrayList) selectableValues;
    }

    public void setSelectedValue(int position) {
        selectedValue = position;
    }

    public int getSelectedValue() {
        return (int) selectedValue;
    }

    public String getSpinnerPrompt() {
        return spinnerPrompt;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }
}
