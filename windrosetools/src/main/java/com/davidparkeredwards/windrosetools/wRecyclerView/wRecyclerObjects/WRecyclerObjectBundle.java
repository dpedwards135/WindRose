package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WRecyclerObjectBundle {

    private String classKey;
    private ArrayList<WRecyclerObject> recyclerObjects;
    private String submissionKey;


    public WRecyclerObjectBundle(String classKey, ArrayList<WRecyclerObject> recyclerObjects, String submissionKey) {
        this.classKey = classKey;
        if(recyclerObjects == null) {
            this.recyclerObjects = new ArrayList<>();
        } else {
            this.recyclerObjects = recyclerObjects;
        }
        this.submissionKey = submissionKey;
    }

    public String getClassKey() {
        return classKey;
    }

    public void setClassKey(String classKey) {
        this.classKey = classKey;
    }

    public ArrayList<WRecyclerObject> getRecyclerObjectsArray() {
        return recyclerObjects;
    }

    public void setRecyclerObjectsArray(ArrayList<WRecyclerObject> recyclerObjects) {
        this.recyclerObjects = recyclerObjects;
    }

    public void saveRecyclerObject(int index, WRecyclerObject object) {
        recyclerObjects.set(index, object);
    }

    public WRecyclerObject getRecyclerObject(int index) {
        return recyclerObjects.get(index);
    }

    public String getSubmissionKey() {
        return submissionKey;
    }
}
