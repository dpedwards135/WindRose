package com.davidparkeredwards.windrosetools.wRecyclerView;

import android.support.v7.widget.RecyclerView;

/**
 * Created by davidedwards on 6/4/17.
 */

public class WRecyclerFinalizeButtons implements WRecyclerObject {
    @Override
    public int getWRecyclerViewType() {
        return 0;
    }

    @Override
    public boolean getIsEditable() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder() {
        return null;
    }
}
