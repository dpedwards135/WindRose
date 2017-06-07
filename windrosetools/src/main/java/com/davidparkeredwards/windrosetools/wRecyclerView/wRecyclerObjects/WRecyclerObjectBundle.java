package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WRecyclerObjectBundle {
    private static final String TAG = WRecyclerObjectBundle.class.getSimpleName();


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

    public WRecyclerObjectBundle(WRecyclerObjectBundleSerialized sbundle) {
        this.classKey = sbundle.classKey;
        this.submissionKey = sbundle.submissionKey;
        ArrayList<WRecyclerObject> stagedRecyclerObjects = new ArrayList<>();
        if(sbundle.checkBoxes != null) stagedRecyclerObjects.addAll(sbundle.checkBoxes);
        if(sbundle.finalizeButtons != null) stagedRecyclerObjects.addAll(sbundle.finalizeButtons);
        if(sbundle.geoStops != null) stagedRecyclerObjects.addAll(sbundle.geoStops);
        if(sbundle.selectFroms != null) stagedRecyclerObjects.addAll(sbundle.selectFroms);
        if(sbundle.textEdits != null) stagedRecyclerObjects.addAll(sbundle.textEdits);
        if(sbundle.textViews != null) stagedRecyclerObjects.addAll(sbundle.textViews);
        this.recyclerObjects = new ArrayList<>();
        recyclerObjects.addAll(stagedRecyclerObjects);
        for(WRecyclerObject object : stagedRecyclerObjects) {
            String fieldId = object.getFieldID();
            int index = sbundle.fieldIdOrder.indexOf(fieldId);
            recyclerObjects.set(index, object);
        }
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
