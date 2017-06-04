package com.davidparkeredwards.windrosetools.wRecyclerView;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerTextView implements WRecyclerObject {
    @Override
    public int getWRecyclerViewType() {
        return 0;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }
}
