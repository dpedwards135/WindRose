package com.davidparkeredwards.windrosetools.wRecyclerView;

/**
 * Created by davidedwards on 6/4/17.
 */

public interface WRecyclerObject {

    /*  Whenever you want to show a new recyclerView, using an object as a type, that object needs
        to be converted to an array, where each property of the object that you want to display
        is converted to a WRecyclerObject, implementing getWRecyclerViewType() which returns
        the type of ViewHolder that should be used to change the value of the property.


     */

    int getWRecyclerViewType(); //This applies to the property and value
    boolean getIsEditable();

}
