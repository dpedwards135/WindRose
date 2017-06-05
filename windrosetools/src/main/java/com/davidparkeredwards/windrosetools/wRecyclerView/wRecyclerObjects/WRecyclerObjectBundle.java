package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WRecyclerObjectBundle {

    private String convertedObjectID;
    private ArrayList<WRecyclerObject> recyclerObjects;

    public WRecyclerObjectBundle(String convertedObjectID, ArrayList<WRecyclerObject> recyclerObjects) {
        this.convertedObjectID = convertedObjectID;
        this.recyclerObjects = recyclerObjects;
    }

    public String getConvertedObjectID() {
        return convertedObjectID;
    }

    public void setConvertedObjectID(String convertedObjectID) {
        this.convertedObjectID = convertedObjectID;
    }

    public ArrayList<WRecyclerObject> getRecyclerObjects() {
        return recyclerObjects;
    }

    public void setRecyclerObjects(ArrayList<WRecyclerObject> recyclerObjects) {
        this.recyclerObjects = recyclerObjects;
    }
}
