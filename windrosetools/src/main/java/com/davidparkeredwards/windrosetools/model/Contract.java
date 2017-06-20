package com.davidparkeredwards.windrosetools.model;

import android.util.Log;

import com.davidparkeredwards.windrosetools.wForm.WFormField;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by davidedwards on 6/19/17.
 */

public class Contract extends DbObject{

    public static final String CONTRACT = "contract";

    public static DbObject getCompanyContract() {

        String testId = CONTRACT;
        String testDescription = "Company contract";
        LinkedHashMap<String, List<String>> testValues = new LinkedHashMap<>();

        String property0name = WMODEL_CLASS;
        List<String> property0list = new ArrayList<>();
        property0list.add(property0name);
        property0list.add("" + WFormField.EXCLUDE);
        property0list.add(WModelClass.COMPANY.getKey());


        String property1name = "name";
        List<String> property1list = new ArrayList<>();
        property1list.add(property1name);
        property1list.add("" + WFormField.TEXT_EDIT);
        property1list.add("Company Name");
        property1list.add("");
        property1list.add("Company Name");

        String property2name = "phone";
        List<String> property2list = new ArrayList<>();
        property2list.add(property2name);
        property2list.add("" + WFormField.TEXT_EDIT);
        property2list.add("Phone Number");
        property2list.add("");
        property2list.add("Phone Number");

        String property3name = "address";
        List<String> property3list = new ArrayList<>();
        property3list.add(property3name);
        property3list.add("" + WFormField.TEXT_EDIT);
        property3list.add("Address");
        property3list.add("");
        property3list.add("Address");

        String property4name = "email";
        List<String> property4list = new ArrayList<>();
        property4list.add(property4name);
        property4list.add("" + WFormField.TEXT_EDIT);
        property4list.add("Email Address");
        property4list.add("");
        property4list.add("Email Address");

        String property5name = "finalize_buttons";
        List<String> property5list = new ArrayList<>();
        property5list.add(property5name);
        property5list.add( "" + WFormField.FINALIZE_BUTTONS);
        property5list.add("Submit when finished: ");
        property5list.add(Boolean.toString(true));
        property5list.add(Boolean.toString(true));
        property5list.add(Boolean.toString(true));

        testValues.put(WMODEL_CLASS, property0list);
        testValues.put("1", property1list);
        testValues.put("2", property2list);
        testValues.put("3", property3list);
        testValues.put("4", property4list);
        testValues.put("5", property5list);


        DbObject contract = new DbObject(testId, testDescription, testValues);
        return contract;
    }

    public static DbObject getTestDbObject() {
        //Change - Add description to object. Put property name as first item in String list. Key is the insertion/display order.

        //REQUIRED ------>
        String testId = "testDbObject";
        String testDescription = "An example of DbObject";
        LinkedHashMap<String, List<String>> testValues = new LinkedHashMap<>();


        String property0name = WMODEL_CLASS;
        List<String> property0list = new ArrayList<>();
        property0list.add(property0name);
        property0list.add("" + WFormField.EXCLUDE);
        property0list.add(WModelClass.COMPANY.getKey());
        //END REQUIRED

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

        //REQUIRED - NUMBERS INDICATED FIELD DISPLAY ORDER
        testValues.put(WMODEL_CLASS, property0list);
        testValues.put("1", property1list);
        testValues.put("2", property2list);
        testValues.put("3", property4list);
        testValues.put("4", property5list);
        testValues.put("5", property3list);

        DbObject testObject = new DbObject(testId, testDescription, testValues);
        return testObject;
    }
}
