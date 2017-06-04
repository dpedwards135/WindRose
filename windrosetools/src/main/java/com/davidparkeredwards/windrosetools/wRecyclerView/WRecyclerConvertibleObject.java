package com.davidparkeredwards.windrosetools.wRecyclerView;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/4/17.
 */

public interface WRecyclerConvertibleObject {

    //This interface includes the ability to transform object into an Array of WRecyclerObjects
    //for display and editing

    ArrayList<WRecyclerObject> getWRecyclerObjectsEditable(); // If you want a RecyclerView that can edit properties
    ArrayList<WRecyclerObject> getWRecyclerObjectsNonEditable(); //If you want a RecyclerView that is view only

}
