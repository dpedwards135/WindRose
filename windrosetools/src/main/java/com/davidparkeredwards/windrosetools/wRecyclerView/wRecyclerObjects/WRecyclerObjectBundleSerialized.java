package com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/6/17.
 */

public class WRecyclerObjectBundleSerialized {

    public List<String> fieldIdOrder;
    public List<WRecyclerCheckBox> checkBoxes;
    public List<WRecyclerFinalizeButtons> finalizeButtons;
    public List<WRecyclerGeoStop> geoStops;
    public List<WRecyclerSelectFrom> selectFroms;
    public List<WRecyclerTextEdit> textEdits;
    public List<WRecyclerTextView> textViews;
    public String classKey;
    public String submissionKey;

    public WRecyclerObjectBundleSerialized(WRecyclerObjectBundle bundle) {
        ArrayList<WRecyclerObject> recyclerObjects = bundle.getRecyclerObjectsArray();
        this.classKey = bundle.getClassKey();
        this.submissionKey = bundle.getSubmissionKey();
        fieldIdOrder = new ArrayList<>();

        checkBoxes = new ArrayList<>();
        finalizeButtons = new ArrayList<>();
        geoStops = new ArrayList<>();
        selectFroms = new ArrayList<>();
        textEdits = new ArrayList<>();
        textViews = new ArrayList<>();

        for(WRecyclerObject object : recyclerObjects) {
            fieldIdOrder.add(object.getFieldID());
            int objectType = object.getWRecyclerViewType();
            switch(objectType) {
                case WRecyclerObject.CHECKBOX:
                    checkBoxes.add((WRecyclerCheckBox) object);
                    break;
                case WRecyclerObject.FINALIZE_BUTTONS:
                    finalizeButtons.add((WRecyclerFinalizeButtons) object);
                    break;
                case WRecyclerObject.GEOSTOP:
                    geoStops.add((WRecyclerGeoStop) object);
                    break;
                case WRecyclerObject.SELECT_FROM:
                    selectFroms.add((WRecyclerSelectFrom) object);
                    break;
                case WRecyclerObject.TEXT_EDIT:
                    textEdits.add((WRecyclerTextEdit) object);
                    break;
                case WRecyclerObject.TEXT_VIEW:
                    textViews.add((WRecyclerTextView) object);
                    break;
            }
        }
    }

    public WRecyclerObjectBundleSerialized(){}

    public WRecyclerObjectBundle deserialize() {

        ArrayList<WRecyclerObject> arrayList = new ArrayList<>(fieldIdOrder.size());

        checkBoxes = new ArrayList<>();
        finalizeButtons = new ArrayList<>();
        geoStops = new ArrayList<>();
        selectFroms = new ArrayList<>();
        textEdits = new ArrayList<>();
        textViews = new ArrayList<>();



        for (WRecyclerCheckBox wRobject : checkBoxes) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WRecyclerFinalizeButtons wRobject : finalizeButtons) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WRecyclerGeoStop wRobject : geoStops) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WRecyclerSelectFrom wRobject : selectFroms) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WRecyclerTextEdit wRobject : textEdits) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }


        WRecyclerObjectBundle bundle = new WRecyclerObjectBundle(classKey, arrayList, submissionKey);
        return bundle;


    }
}
