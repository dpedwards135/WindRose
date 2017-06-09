package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WTextEdit implements WFormField {

    public String fieldID;
    public double viewType = TEXT_EDIT;
    public String text;
    public String userInput;
    public String prompt;

    public WTextEdit(String fieldID, String text, String userInput, String prompt) {
        this.fieldID = fieldID;
        this.text = text;
        this.userInput = userInput;
        this.prompt = prompt;
    }

    public WTextEdit() {}

    @Override
    public int getWRecyclerViewType() {
        return (int) viewType;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }

    public String getText() {
        return text;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }

}
