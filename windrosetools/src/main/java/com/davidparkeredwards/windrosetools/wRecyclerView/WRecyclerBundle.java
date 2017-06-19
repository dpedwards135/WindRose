package com.davidparkeredwards.windrosetools.wRecyclerView;

import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.wForm.WFormField;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/5/17.
 */

public class WRecyclerBundle {
    private static final String TAG = WRecyclerBundle.class.getSimpleName();


    //private String classKey;
    private String uniqueId;
    private WModelClass wModelClass;
    private ArrayList<WFormField> recyclerObjects;
    //private HashMap<String, WFormField> recyclerObjectMap;
    private boolean isSubmitted;


    public WRecyclerBundle(String uniqueId, ArrayList<WFormField> recyclerObjects) {
        this.uniqueId = uniqueId;
        this.wModelClass = wModelClass;
        if(recyclerObjects == null) {
            this.recyclerObjects = new ArrayList<>();
        } else {
            this.recyclerObjects = recyclerObjects;
        }
        this.isSubmitted = isSubmitted;
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

    public String getClassKey() {
        return wModelClass.getKey();
    }

    /*public void setClassKey(String classKey) {
        this.classKey = classKey;
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

    public boolean getIsSubmitted() {
        return isSubmitted;
    }

    public WModelClass getwModelClass() {
        return wModelClass;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
