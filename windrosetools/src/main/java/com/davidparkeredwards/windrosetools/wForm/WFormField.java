package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/4/17.
 */

public interface WFormField {

    /*  Whenever you want to show a new recyclerView, using an object as a type, that object needs
        to be converted to an array, where each property of the object that you want to display
        is converted to a WFormField, implementing getWRecyclerViewType() which returns
        the type of ViewHolder that should be used to change the value of the property.

        RecyclerObject will also return its own view holder.

        Two types of WViewHolders - Generic and GeoPoint
     */

    int getWRecyclerViewType(); //This applies to the property and value

    int EXCLUDE = 0;
    int CHECKBOX = 1;
    int FINALIZE_BUTTONS = 2;
    int GEOSTOP = 3;
    int SELECT_FROM = 4;
    int TEXT_EDIT = 5;
    int TEXT_VIEW = 6;

}
