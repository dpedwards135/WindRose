package com.davidparkeredwards.windrosetools.wRecyclerView;

import com.davidparkeredwards.windrosetools.wForm.WFormField;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WRecyclerBundle {
    private static final String TAG = WRecyclerBundle.class.getSimpleName();



    private ArrayList<WFormField> recyclerObjects;



    public WRecyclerBundle(ArrayList<WFormField> recyclerObjects) {
        if(recyclerObjects == null) {
            this.recyclerObjects = new ArrayList<>();
        } else {
            this.recyclerObjects = recyclerObjects;
        }

    }

    /*
    public WRecyclerBundle(WForm sbundle) {
        this.classKey = sbundle.classKey;
        this.submissionKey = sbundle.submissionKey;
        ArrayList<WFormField> stagedRecyclerObjects = new ArrayList<>();
        if(sbundle.checkBoxes != null) stagedRecyclerObjects.addAll(sbundle.checkBoxes);
        if(sbundle.finalizeButtons != null) stagedRecyclerObjects.addAll(sbundle.finalizeButtons);
        if(sbundle.geoStops != null) stagedRecyclerObjects.addAll(sbundle.geoStops);
        if(sbundle.selectFroms != null) stagedRecyclerObjects.addAll(sbundle.selectFroms);
        if(sbundle.textEdits != null) stagedRecyclerObjects.addAll(sbundle.textEdits);
        if(sbundle.textViews != null) stagedRecyclerObjects.addAll(sbundle.textViews);
        this.recyclerObjects = new ArrayList<>();
        recyclerObjects.addAll(stagedRecyclerObjects);
        for(WFormField object : stagedRecyclerObjects) {
            String fieldId = object.getFieldID();
            int index = sbundle.fieldIdOrder.indexOf(fieldId);
            recyclerObjects.set(index, object);
        }
    }
    */


    public ArrayList<WFormField> getRecyclerObjectsArray() {
        return recyclerObjects;
    }

    public void setRecyclerObjectsArray(ArrayList<WFormField> recyclerObjects) {
        this.recyclerObjects = recyclerObjects;
    }

    public void saveRecyclerObject(int index, WFormField object) {
        recyclerObjects.set(index, object);
    }

    public WFormField getRecyclerObject(int index) {
        return recyclerObjects.get(index);
    }

}
