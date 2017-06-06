package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerTextEdit implements WRecyclerObject {

    public String fieldID;
    public int viewType = TEXT_EDIT;
    public String text;
    public String userInput;
    public String prompt;

    public WRecyclerTextEdit(String fieldID, String text, String userInput, String prompt) {
        this.fieldID = fieldID;
        this.text = text;
        this.userInput = userInput;
        this.prompt = prompt;
    }

    @Override
    public int getWRecyclerViewType() {
        return viewType;
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
