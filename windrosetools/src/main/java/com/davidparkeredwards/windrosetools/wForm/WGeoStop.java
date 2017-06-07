package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WGeoStop implements WFormField {

    public String fieldID;

    public WGeoStop() {}

    @Override
    public int getWRecyclerViewType() {
        return 0;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }

    @Override
    public String getFieldID() {
        return fieldID;
    }

}
