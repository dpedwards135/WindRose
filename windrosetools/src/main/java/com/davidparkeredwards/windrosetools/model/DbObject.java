package com.davidparkeredwards.windrosetools.model;

import android.content.Context;
import android.util.Log;

import com.davidparkeredwards.windrosetools.FirebaseHelper;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.wForm.DBResponse;
import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.DbResponseHandler;
import com.davidparkeredwards.windrosetools.wForm.WCheckBox;
import com.davidparkeredwards.windrosetools.wForm.WFinalizeButtons;
import com.davidparkeredwards.windrosetools.wForm.WFormField;
import com.davidparkeredwards.windrosetools.wForm.WSelectFrom;
import com.davidparkeredwards.windrosetools.wForm.WTextEdit;
import com.davidparkeredwards.windrosetools.wForm.WTextView;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerBundle;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by davidedwards on 6/14/17.
 */

public class DbObject implements DbBody{

    public final static String WMODEL_CLASS = "wmodel_class";
    public final static int PROPERTY_NAME = 0;
    public final static int FORM_FIELD_TYPE = 1;
    public final static int DISPLAY_TEXT = 2;
    public final static int SELECTED_VALUE = 3;
    public final static int CANCEL_BUTTON = 3;
    public final static int SAVE_BUTTON = 4;
    public final static int SUBMIT_BUTTON = 5;



    public String uniqueID;
    public String description;
    public HashMap<String, List<String>> properties;

    /*
    Object Values in the following order within a list:
    FormType - Checklist, GeoPoint, Exclude, Etc
    DisplayText
    SelectedValue
    Additional Values if applicable

    There will need to be some function that creates new Objects in the DB that can then be pulled
    when user wants to create a new one, so the process is always the same, just pulling DbObjects down
    and then pushing them back up.
     */

    public DbObject() {}

    public DbObject(String uniqueID, String description, HashMap<String, List<String>> objectValues) {
        this.uniqueID = uniqueID;
        this.description = description;
        this.properties = objectValues;
    }

    public DbObject(WRecyclerBundle bundle, DbObject dbObject, int listType) {
        FirebaseHelper helper = new FirebaseHelper(WindroseApplication.applicationContext);
        String newID;
        if(dbObject.uniqueID.contains(Contract.CONTRACT)) {
            newID = helper.getNewObjectKey(WModelClass.findWModelFromKey(dbObject.getwModelClassKey()), listType);
        } else {
            newID = dbObject.getUniqueID();
        }
        String newDescription;
        Date date = new Date();
        if(dbObject.uniqueID.contains(Contract.CONTRACT)) {
            newDescription = newID + " " + date;
        } else {
            newDescription = dbObject.getDescription();
        }
        HashMap<String, List<String>> newProperties = new HashMap<String, List<String>>();
        ArrayList<WFormField> list = bundle.getRecyclerObjectsArray();
        newProperties.put(WMODEL_CLASS, dbObject.getProperties().get(WMODEL_CLASS));
        int keyCounter = 1;
        int recyclerObjectsCounter = 0;
        while (dbObject.getProperties().containsKey(String.valueOf(keyCounter))) {
            WFormField formField = list.get(recyclerObjectsCounter);
            String key = String.valueOf(keyCounter);
            switch(Integer.valueOf(dbObject.getProperties().get(key).get(FORM_FIELD_TYPE))) {
                case WFormField.CHECKBOX:
                    ArrayList<String> newPropList = (ArrayList) dbObject.getProperties().get(key);
                    newPropList.set(SELECTED_VALUE, String.valueOf(((WCheckBox) formField).getTrueOrFalse()));
                    newProperties.put(key, newPropList);
                    recyclerObjectsCounter ++;
                    break;
                case WFormField.FINALIZE_BUTTONS:
                    newProperties.put(String.valueOf(keyCounter), dbObject.getProperties().get(key));
                    recyclerObjectsCounter ++;
                    break;
                case WFormField.GEOSTOP:

                    break;
                case WFormField.SELECT_FROM:
                    ArrayList<String> newPList = (ArrayList) dbObject.getProperties().get(key);
                    newPList.set(SELECTED_VALUE, ((WSelectFrom) formField).getSelectedValueString());
                    newProperties.put(key, newPList);
                    recyclerObjectsCounter ++;
                    break;
                case WFormField.TEXT_EDIT:
                    ArrayList<String> newPrList = (ArrayList) dbObject.getProperties().get(key);
                    newPrList.set(SELECTED_VALUE, ((WTextEdit) formField).getUserInput());
                    newProperties.put(key, newPrList);
                    Log.i("DbOBject", "DbObject: Text Edit");
                    recyclerObjectsCounter ++;
                    break;
                case WFormField.TEXT_VIEW:
                    newProperties.put(String.valueOf(keyCounter), dbObject.getProperties().get(key));
                    recyclerObjectsCounter ++;
                    break;
                default:
                    newProperties.put(String.valueOf(keyCounter), dbObject.getProperties().get(key));
                    break;

            }
            keyCounter ++;
        }
        this.uniqueID = newID;
        this.description = newDescription;
        this.properties = newProperties;
        Log.i("DbObject", "DbObject: CREATED");
    }

    @Exclude
    public static DbObject getTestDbObject() {
        //Change - Add description to object. Put property name as first item in String list. Key is the insertion/display order.
        String testId = "testDbObject";
        String testDescription = "An example of DbObject";
        LinkedHashMap<String, List<String>> testValues = new LinkedHashMap<>();


        String property0name = WMODEL_CLASS;
        List<String> property0list = new ArrayList<>();
        property0list.add(property0name);
        property0list.add("" + WFormField.EXCLUDE);
        property0list.add(WModelClass.COMPANY.getKey());


        String property1name = "First Property";
        List<String> property1list = new ArrayList<>();
        String property1fieldType = "" + WFormField.CHECKBOX;
        String property1DisplayText = "Property 1 text";
        String property1SelectedValue = "" + true;
        property1list.add(property1name);
        property1list.add(property1fieldType);
        property1list.add(property1DisplayText);
        property1list.add(property1SelectedValue);

        String property2name = "Second Property";
        List<String> property2list = new ArrayList<>();
        String property2fieldType = "" + WFormField.SELECT_FROM;
        String property2DisplayText = "Property 2 text";
        String property2SelectedValue = "1";
        String property2Value1 = "0";
        String property2Value2 = "1";
        String property2Value3 = "2";
        String property2Value4 = "3";
        String property2Value5 = "4";
        property2list.add(property2name);
        property2list.add(property2fieldType);
        property2list.add(property2DisplayText);
        property2list.add(property2SelectedValue);
        property2list.add(property2Value1);
        property2list.add(property2Value2);
        property2list.add(property2Value3);
        property2list.add(property2Value4);
        property2list.add(property2Value5);

        String property3name = "Third Property";
        List<String> property3list = new ArrayList<>();
        property3list.add(property3name);
        property3list.add( "" + WFormField.FINALIZE_BUTTONS);
        property3list.add("Property 3 Buttons");
        property3list.add(Boolean.toString(true));
        property3list.add(Boolean.toString(true));
        property3list.add(Boolean.toString(true));
        Log.i("Finalize Buttons", "getTestDbObject: Property3 list: " + property3list.toString());

        String property4name = "Fourth Property";
        List<String> property4list = new ArrayList<>();
        property4list.add(property4name);
        property4list.add("" + WFormField.TEXT_EDIT);
        property4list.add("Property 4 text");
        property4list.add("This is the correct text");
        property4list.add("This is the prompt");

        String property5name = "Fifth Property";
        List<String> property5list = new ArrayList<>();
        property5list.add(property5name);
        property5list.add("" + WFormField.TEXT_VIEW);
        property5list.add("This is the display Text");
        property5list.add("This is the selected value");


        testValues.put(WMODEL_CLASS, property0list);
        testValues.put("1", property1list);
        testValues.put("2", property2list);
        testValues.put("3", property4list);
        testValues.put("4", property5list);
        testValues.put("5", property3list);

        DbObject testObject = new DbObject(testId, testDescription, testValues);
        return testObject;
    }

    @Exclude
    public WRecyclerBundle toWRecyclerBundle(boolean viewOnly) {
        ArrayList<WFormField> formFields = new ArrayList<>();
        int keyCounter = 1;
        while(properties.containsKey(String.valueOf(keyCounter))) {
            String key = String.valueOf(keyCounter);
        //for(String key : properties.keySet()) {
            List<String> list = properties.get(key);
            if (!viewOnly) {
                switch (Integer.valueOf(list.get(FORM_FIELD_TYPE))) {
                    case WFormField.EXCLUDE:
                        break;
                    //ALL CONSTRUCTORS SHOULD HAVE KEY, DISPLAY_TEXT, SELECTED_VALUE AS FIRST THREE ARGUMENTS
                    case WFormField.CHECKBOX:
                        WCheckBox checkBox = new WCheckBox(key, list.get(DISPLAY_TEXT), Boolean.getBoolean(list.get(SELECTED_VALUE)));
                        formFields.add(checkBox);
                        break;
                    case WFormField.FINALIZE_BUTTONS:
                        WFinalizeButtons finalizeButtons = new WFinalizeButtons(key, list.get(DISPLAY_TEXT), Boolean.valueOf(list.get(CANCEL_BUTTON)),
                                Boolean.valueOf(list.get(SAVE_BUTTON)), Boolean.valueOf(list.get(SUBMIT_BUTTON)));
                        formFields.add(finalizeButtons);
                        Log.i("ToBundle", "toWRecyclerBundle: Finalize Save? " + list.get(2) + " " + finalizeButtons.isSetSave());
                        break;
                    case WFormField.GEOSTOP:

                        break;
                    case WFormField.SELECT_FROM:
                        ArrayList<String> allValues = new ArrayList<>();
                        if (list.size() > 3) {
                            int counter = 3;
                            while (counter < list.size()) {
                                allValues.add(list.get(counter));
                                counter++;
                            }
                        }
                        WSelectFrom selectFrom = new WSelectFrom(key, list.get(DISPLAY_TEXT), list.get(SELECTED_VALUE), allValues);
                        formFields.add(selectFrom);
                        break;
                    case WFormField.TEXT_EDIT:
                        WTextEdit textEdit = new WTextEdit(key, list.get(DISPLAY_TEXT), list.get(SELECTED_VALUE), list.get(3));
                        formFields.add(textEdit);
                        break;
                    case WFormField.TEXT_VIEW:
                        WTextView textView = new WTextView(key, list.get(DISPLAY_TEXT), list.get(SELECTED_VALUE));
                        formFields.add(textView);
                        break;
                    default:
                        break;
                }
            } else {
                if (Integer.valueOf(list.get(FORM_FIELD_TYPE)) == WFormField.EXCLUDE
                        || Integer.valueOf(list.get(FORM_FIELD_TYPE)) == WFormField.FINALIZE_BUTTONS) {
                    continue;
                } else {
                    WTextView textView = new WTextView(key, list.get(DISPLAY_TEXT), list.get(SELECTED_VALUE));
                    formFields.add(textView);
                    continue;
                }
            }
            keyCounter ++;
        }
        return new WRecyclerBundle(formFields);
    }

    @Exclude
    @Override
    public DbBody getDbBody() {
        return this;
    }
    @Exclude
    public String getUniqueID() {
        return uniqueID;
    }
    @Exclude
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
    @Exclude
    public HashMap<String, List<String>> getProperties() {
        return properties;
    }
    @Exclude
    public void setObjectLists(LinkedHashMap<String, List<String>> objectValues) {
        this.properties = objectValues;
    }
    @Exclude
    public String getwModelClassKey() {
        Log.i("DbObject", "getwModelClassKey: " + properties.get(WMODEL_CLASS));
        return WModelClass.findWModelFromKey(properties.get(WMODEL_CLASS).get(2)).getKey();
    }
    @Exclude
    public void putToDb(Context context, int listType) {
        DbResponseHandler dbResponseHandler = new DbResponseHandler(context);
        FirebaseHelper helper = new FirebaseHelper(context);
        io.reactivex.Observable<DBResponse> dbObjectSave = helper.putDbObject(this, listType);
        dbObjectSave.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DBResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DBResponse dbResponse) {
                        Log.i("DbObject", "onNext: " + dbResponse.getCode());
                        dbResponseHandler.onNext();
                        onComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DbObject", "onError: ", e);
                        dbResponseHandler.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Exclude
    public String getDescription() {
        return description;
    }
}
