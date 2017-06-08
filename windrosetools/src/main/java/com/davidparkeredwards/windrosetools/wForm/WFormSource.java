package com.davidparkeredwards.windrosetools.wForm;

import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

/**
 * Created by davidedwards on 6/4/17.
 */

public interface WFormSource {

    //This interface includes the ability to transform object into an Array of WRecyclerObjects
    //for display and editing

    boolean getIsType();
    WRecyclerBundle getWRecyclerObjectsEditable(); // If you want a RecyclerView that can edit properties
    WRecyclerBundle getWRecyclerObjectsViewable(); //If you want a RecyclerView that is view only
    void from(WRecyclerBundle bundle);

}
