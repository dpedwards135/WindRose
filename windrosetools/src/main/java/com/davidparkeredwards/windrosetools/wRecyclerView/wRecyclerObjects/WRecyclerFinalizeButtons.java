package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.WindroseApplication;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerFinalizeButtons implements WRecyclerObject {

    private String fieldID;
    private int type = FINALIZE_BUTTONS;
    private boolean isEditable = false;
    private boolean setCancel;
    private boolean setSave;
    private boolean setSubmit;

    public WRecyclerFinalizeButtons(String fieldID, boolean setCancel, boolean setSave, boolean setSubmit) {
        this.fieldID = fieldID;
        this.setCancel = setCancel;
        this.setSave = setSave;
        this.setSubmit = setSubmit;
    }

    @Override
    public int getWRecyclerViewType() {
        return type;
    }

    @Override
    public boolean getIsEditable() {
        return isEditable;
    }

    public boolean isSetCancel() {
        return setCancel;
    }

    public boolean isSetSave() {
        return setSave;
    }

    public boolean isSetSubmit() {
        return setSubmit;
    }

    public enum ButtonType {

        SAVE(WindroseApplication.applicationContext.getString(R.string.save)),
        CANCEL(WindroseApplication.applicationContext.getString(R.string.cancel)),
        SUBMIT(WindroseApplication.applicationContext.getString(R.string.submit));

        private String buttonName;

        private ButtonType(String buttonType) {
            this.buttonName = buttonName;
        }

        public String getButtonName() {
            return this.buttonName;
        }

    }

    @Override
    public String getFieldID() {
        return fieldID;
    }
}

