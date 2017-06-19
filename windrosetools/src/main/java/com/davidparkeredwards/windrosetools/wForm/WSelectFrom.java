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
    public String selectedValue;
    public String spinnerPrompt;

    public WSelectFrom() {}

    public WSelectFrom(String fieldID, String text, String selectedValue, ArrayList<String> selectableValues) {
        this.fieldID = fieldID;
        this.isEditable = isEditable;
        this.text = text;
        this.selectableValues = selectableValues;
        this.selectedValue = selectedValue;
    }

    @Override
    public int getWRecyclerViewType() {
        return (int) viewType;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getValues() {
        return (ArrayList) selectableValues;
    }

    public void setSelectedValue(int position) {
        selectedValue = selectableValues.get(position);
    }

    public int getSelectedValue() {

        return selectableValues.indexOf(selectedValue);
    }

    public String getSpinnerPrompt() {
        return spinnerPrompt;
    }

}
