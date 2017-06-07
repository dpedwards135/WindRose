package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerGeoStop implements WRecyclerObject {

    public String fieldID;

    public WRecyclerGeoStop() {}

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
