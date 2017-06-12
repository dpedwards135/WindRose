package com.davidparkeredwards.windrosetools.wForm;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/6/17.
 */

public class WForm implements DbBody {

    @Exclude
    private static final String TAG = WForm.class.getSimpleName();

    public String uniqueId;
    public String userId;
    public String companyId;
    public String classKey;
    public boolean isSubmitted;
    public double requestType;
    public String modelReference; //If it is anything but Create
    public String description;

    public List<String> fieldIdOrder;
    public List<WCheckBox> checkBoxes;
    public List<WFinalizeButtons> finalizeButtons;
    public List<WGeoStop> geoStops;
    public List<WSelectFrom> selectFroms;
    public List<WTextEdit> textEdits;
    public List<WTextView> textViews;


    public WForm(){}


    public WForm(String uniqueId, String userId, String companyId, List<String> fieldIdOrder, List<WCheckBox> checkBoxes,
                 List<WFinalizeButtons> finalizeButtons, List<WGeoStop> geoStops, List<WSelectFrom> selectFroms,
                 List<WTextEdit> textEdits, List<WTextView> textViews, String classKey, boolean isSubmitted,
                 String description) {
        this.uniqueId = uniqueId;
        this.userId = userId;
        this.companyId = companyId;
        this.fieldIdOrder = fieldIdOrder;
        this.checkBoxes = checkBoxes;
        this.finalizeButtons = finalizeButtons;
        this.geoStops = geoStops;
        this.selectFroms = selectFroms;
        this.textEdits = textEdits;
        this.textViews = textViews;
        this.classKey = classKey;
        this.isSubmitted = isSubmitted;
        this.description = description;
    }

    @Exclude
    public static WForm fromRecyclerBundle(WRecyclerBundle bundle) {
        ArrayList<WFormField> recyclerObjects = bundle.getRecyclerObjectsArray();
        String wrUniqueId = bundle.getUniqueId();
        String wrclassKey = bundle.getClassKey();
        boolean wrIsSubmitted = bundle.getIsSubmitted();
        String wrdescription = "Form from WRecyclerBundle";
        List wrfieldIdOrder = new ArrayList<>();

        List wrcheckBoxes = new ArrayList<>();
        List wrfinalizeButtons = new ArrayList<>();
        List wrgeoStops = new ArrayList<>();
        List wrselectFroms = new ArrayList<>();
        List wrtextEdits = new ArrayList<>();
        List wrtextViews = new ArrayList<>();

        for(WFormField object : recyclerObjects) {
            wrfieldIdOrder.add(object.getFieldID());
            int objectType = object.getWRecyclerViewType();
            switch(objectType) {
                case WFormField.CHECKBOX:
                    wrcheckBoxes.add((WCheckBox) object);
                    break;
                case WFormField.FINALIZE_BUTTONS:
                    wrfinalizeButtons.add((WFinalizeButtons) object);
                    break;
                case WFormField.GEOSTOP:
                    wrgeoStops.add((WGeoStop) object);
                    break;
                case WFormField.SELECT_FROM:
                    wrselectFroms.add((WSelectFrom) object);
                    break;
                case WFormField.TEXT_EDIT:
                    wrtextEdits.add((WTextEdit) object);
                    Log.i(TAG, "fromRecyclerBundle: added textedit");
                    break;
                case WFormField.TEXT_VIEW:
                    wrtextViews.add((WTextView) object);
                    break;
            }
        }
        String wrwUserId;
        if(WindroseApplication.currentWUser != null) {
            wrwUserId = WindroseApplication.currentWUser.getWUserId();
        } else {
            wrwUserId = "null";
        }
        String wrCompanyId;
        if(WindroseApplication.getCompanyID() != null) {
            wrCompanyId = WindroseApplication.getCompanyID();
        } else {
            wrCompanyId = "null";
        }

        WForm wform = new WForm(wrUniqueId, wrwUserId, wrCompanyId,
                wrfieldIdOrder, wrcheckBoxes,
                wrfinalizeButtons, wrgeoStops, wrselectFroms,
                wrtextEdits, wrtextViews, wrclassKey, wrIsSubmitted,
                wrdescription);
        return wform;
    }

    @Exclude
    public static WForm initialize(Context context, WForm wForm) {
        FirebaseHelper helper = new FirebaseHelper(context);
        WModelClass wModelClass = wForm.getWModelClass();
        String id = helper.getNewId(wModelClass);
        String uniqueId = id;
        String userId;
        String companyId;
        String description;
        if(wModelClass == WModelClass.W_USER) {
            userId = id;
            companyId = (WindroseApplication.getCompanyID() != null) ? WindroseApplication.getCompanyID() : "UninitializedCompany";
            description = WindroseApplication.auth.getCurrentUser().getUid();
        } else if (wModelClass == WModelClass.COMPANY) {
            userId = WindroseApplication.currentWUser.getWUserId();
            companyId = id;
            description = "";
        } else {
            userId = WindroseApplication.currentWUser.getWUserId();
            companyId = WindroseApplication.getCompanyID();
            description = "";
        }
        return new WForm(uniqueId, userId, companyId, wForm.fieldIdOrder, wForm.checkBoxes,
                wForm.finalizeButtons, wForm.geoStops, wForm.selectFroms,
                wForm.textEdits, wForm.textViews, wForm.classKey, wForm.isSubmitted,
        description); //Need to finish constructing new WForm with new variables and old variables from input
    }

    @Exclude
    public WRecyclerBundle toRecyclerBundle() {

        Log.i(TAG, "toRecyclerBundle: FieldIDOrder Size: " + fieldIdOrder.size());
        ArrayList<WFormField> arrayList = new ArrayList<>(fieldIdOrder.size());

        ArrayList emptyArray = new ArrayList();
        arrayList.addAll(((checkBoxes == null) ? emptyArray : checkBoxes));
        arrayList.addAll(((finalizeButtons == null) ? emptyArray : finalizeButtons));
        arrayList.addAll(((geoStops == null) ? emptyArray : geoStops));
        arrayList.addAll(((selectFroms == null) ? emptyArray : selectFroms));
        arrayList.addAll(((textEdits == null) ? emptyArray : textEdits));
        arrayList.addAll(((textViews == null) ? emptyArray : textViews));

        if(checkBoxes != null) {
            for (WCheckBox wRobject : checkBoxes) {
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        if(finalizeButtons != null) {
            for (WFinalizeButtons wRobject : finalizeButtons) {
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        if(geoStops != null) {
            for (WGeoStop wRobject : geoStops) {
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        if(selectFroms != null) {
            for (WSelectFrom wRobject : selectFroms) {
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        if(textEdits != null) {
            for (WTextEdit wRobject : textEdits) {
                Log.i(TAG, "toRecyclerBundle: Add textEdit");
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        if(textViews != null) {
            for (WTextView wRobject : textViews) {
                String fieldId = wRobject.getFieldID();
                int index = fieldIdOrder.indexOf(fieldId);
                arrayList.set(index, wRobject);
            }
        }
        Log.i("WFORM", "toRecyclerBundle: ArrayList size = " + arrayList.size());
        WRecyclerBundle bundle = new WRecyclerBundle(this.uniqueId, this.getWModelClass(), arrayList, isSubmitted);
        return bundle;
    }

    @Exclude
    @Override
    public DbBody getDbBody() {
        return this;
    }

    @Exclude
    public String getUniqueId() {
        return this.uniqueId;
    }

    @Exclude
    public WModelClass getWModelClass() {
        return WModelClass.findWModelFromKey(classKey);
    }

    @Exclude
    @Override
    public String toString() {

        String string = "UserID = " + userId + "\n" +
                "CompanyID = " + companyId + "\n" +
                "Description = " + description + "\n" +
                "ClassKey = " + classKey + "\n" +
                "TextView Size = " + textViews.size() + "\n" +
                "TextEdit Size = " + textEdits.size() + "\n" +
                "SelectFroms Size = " + selectFroms.size() + "\n" +
                "GeoStops Size = " + geoStops.size() + "\n" +
                "FinalizeButtons Size = " + finalizeButtons.size() + "\n" +
                "CheckBoxes Size = " + checkBoxes.size() + "\n" +
                "FieldIdOrder Size = " + fieldIdOrder.size();

        return string;
    }
}
