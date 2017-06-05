package com.davidparkeredwards.windrosetools.wRecyclerView;

import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObjectBundle;

/**
 * Created by davidedwards on 6/4/17.
 */

public interface WRecyclerConvertibleObject {

    //This interface includes the ability to transform object into an Array of WRecyclerObjects
    //for display and editing

    WRecyclerObjectBundle getWRecyclerObjectsEditable(); // If you want a RecyclerView that can edit properties
    WRecyclerObjectBundle getWRecyclerObjectsViewable(); //If you want a RecyclerView that is view only
    void from(WRecyclerObjectBundle bundle);

}
