package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerFinalizeButtons implements WRecyclerObject {

    private int numberOfButtons;

    @Override
    public int getWRecyclerViewType() {
        return 0;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }

    public int getNumberOfButtons() {
        return numberOfButtons;
    }
}

