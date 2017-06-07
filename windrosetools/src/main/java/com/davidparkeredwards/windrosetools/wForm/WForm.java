package com.davidparkeredwards.windrosetools.wForm;

import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/6/17.
 */

public class WForm {

    public List<String> fieldIdOrder;
    public List<WCheckBox> checkBoxes;
    public List<WFinalizeButtons> finalizeButtons;
    public List<WGeoStop> geoStops;
    public List<WSelectFrom> selectFroms;
    public List<WTextEdit> textEdits;
    public List<WTextView> textViews;
    public String classKey;
    public String submissionKey;

    public WForm(WRecyclerBundle bundle) {
        ArrayList<WFormField> recyclerObjects = bundle.getRecyclerObjectsArray();
        this.classKey = bundle.getClassKey();
        this.submissionKey = bundle.getSubmissionKey();
        fieldIdOrder = new ArrayList<>();

        checkBoxes = new ArrayList<>();
        finalizeButtons = new ArrayList<>();
        geoStops = new ArrayList<>();
        selectFroms = new ArrayList<>();
        textEdits = new ArrayList<>();
        textViews = new ArrayList<>();

        for(WFormField object : recyclerObjects) {
            fieldIdOrder.add(object.getFieldID());
            int objectType = object.getWRecyclerViewType();
            switch(objectType) {
                case WFormField.CHECKBOX:
                    checkBoxes.add((WCheckBox) object);
                    break;
                case WFormField.FINALIZE_BUTTONS:
                    finalizeButtons.add((WFinalizeButtons) object);
                    break;
                case WFormField.GEOSTOP:
                    geoStops.add((WGeoStop) object);
                    break;
                case WFormField.SELECT_FROM:
                    selectFroms.add((WSelectFrom) object);
                    break;
                case WFormField.TEXT_EDIT:
                    textEdits.add((WTextEdit) object);
                    break;
                case WFormField.TEXT_VIEW:
                    textViews.add((WTextView) object);
                    break;
            }
        }
    }

    public WForm(){}

    public WRecyclerBundle deserialize() {

        ArrayList<WFormField> arrayList = new ArrayList<>(fieldIdOrder.size());

        checkBoxes = new ArrayList<>();
        finalizeButtons = new ArrayList<>();
        geoStops = new ArrayList<>();
        selectFroms = new ArrayList<>();
        textEdits = new ArrayList<>();
        textViews = new ArrayList<>();



        for (WCheckBox wRobject : checkBoxes) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WFinalizeButtons wRobject : finalizeButtons) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WGeoStop wRobject : geoStops) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WSelectFrom wRobject : selectFroms) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }
        for (WTextEdit wRobject : textEdits) {
            String fieldId = wRobject.getFieldID();
            int index = fieldIdOrder.indexOf(fieldId);
            arrayList.set(index, wRobject);
        }


        WRecyclerBundle bundle = new WRecyclerBundle(classKey, arrayList, submissionKey);
        return bundle;


    }
}
