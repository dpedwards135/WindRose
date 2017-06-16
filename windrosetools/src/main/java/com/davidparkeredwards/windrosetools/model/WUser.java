package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;
import com.davidparkeredwards.windrosetools.wForm.WCheckBox;
import com.davidparkeredwards.windrosetools.wForm.WFinalizeButtons;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.davidparkeredwards.windrosetools.wForm.WFormSource;
import com.davidparkeredwards.windrosetools.wForm.WGeoStop;
import com.davidparkeredwards.windrosetools.wForm.WSelectFrom;
import com.davidparkeredwards.windrosetools.wForm.WTextEdit;
import com.davidparkeredwards.windrosetools.wForm.WTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by davidedwards on 6/8/17.
 */

public class WUser extends ModelObject implements WFormSource {

    private static final String CLASS_KEY = WModelClass.W_USER.getKey();
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String EMAIL_ADDRESS = "email_address";
    private static final String WUSER_FINALIZE_BUTTONS = "wuser_finalize_buttons";

    private String name;
    private String userName;
    private String emailAddress;
    private String wUserId;
    private String authUID;

    //When user logs in, if it doesn't recognize their info it prompts them to create a WUser
    //Later I'll probably need to just incorporate all of this into the sign up screen and allow users to
    //create a separate username, etc, so they are not permanently tied to any given sign in method.

    //Next: User should be able to create new sign in info and have user info set to app and continue to create company.
    //  Clean up recyclerView and Adapter

    public WUser() {}

    public WUser(String fullname, String emailAddress, String wUserId, String authUID) {
        this.name = fullname;
        this.userName = fullname;
        this.emailAddress = emailAddress;
        this.wUserId = wUserId;
        this.authUID = authUID;
    }

    public WUser(WForm wForm){
        //Set to pull values based on field ID for arrays.
    }

    public String getWUserId() {
        return this.wUserId;
    }

    @Override
    public boolean getIsType() {
        return false;
    }

    @Override
    public WForm getForm() {

        String userId = "Anonymous";//Need to get key prior to saving this. Same for Company.
        String companyId = WindroseApplication.getCompanyID();
        String classKey = CLASS_KEY;
        boolean isSubmitted = false;

        List<String> fieldIdOrder = new ArrayList<>();
        fieldIdOrder.add(NAME);
        fieldIdOrder.add(USERNAME);
        fieldIdOrder.add(EMAIL_ADDRESS);
        fieldIdOrder.add(WUSER_FINALIZE_BUTTONS);

        List<WCheckBox> checkBoxes = new ArrayList<>();

        List<WFinalizeButtons> finalizeButtons = new ArrayList<>();
        finalizeButtons.add(new WFinalizeButtons(WUSER_FINALIZE_BUTTONS, true, true, true));

        List<WGeoStop> geoStops = new ArrayList<>();

        List<WSelectFrom> selectFroms = new ArrayList<>();

        List<WTextEdit> textEdits = new ArrayList<>();
        textEdits.add(new WTextEdit(NAME, "Full Name", name, "Full Name"));
        textEdits.add(new WTextEdit(USERNAME, "Username", userName, "Username"));
        textEdits.add(new WTextEdit(EMAIL_ADDRESS, "Email Address", emailAddress, "name@example.com"));

        List<WTextView> textViews = new ArrayList<>();

        String description = authUID;

        WForm wForm = new WForm(null, userId, companyId, fieldIdOrder, checkBoxes, finalizeButtons, geoStops,
                selectFroms, textEdits, textViews, classKey, isSubmitted, description);
        return wForm;
    }

    @Override
    public String getKey() {
        return wUserId;
    }


    @Override
    public DbObject toDbObject() {
        HashMap<String, List<String>> userValues = new HashMap<>();
        userValues.put("full_name", valueToList(name));
        userValues.put("email_address", valueToList(emailAddress));
        userValues.put("fb_uid", valueToList(authUID));
        String uniqueId = wUserId;
        return new DbObject(uniqueId, userValues);
    }

    //PICK UP HERE - NEED TO FINISH CONVERTING TO DBOBJECT AND FIX OLD METHOD CALLS IN OTHER CLASSES ESPECIALLY HELPER
    @Override
    public void fromDbObject(DbObject dbObject) {
        HashMap<String, List<String>> hashMapLists = dbObject.getObjectValues();
        String uniqueId = dbObject.getUniqueID();
        name = singleValueFromList(hashMapLists.get("full_name"));
        emailAddress = singleValueFromList(hashMapLists.get("email_address"));
        authUID = singleValueFromList(hashMapLists.get("fb_uid"));
        userName = singleValueFromList(hashMapLists.get("full_name"));
        wUserId = uniqueId;
    }

    @Override
    public String getDescription() {
        return emailAddress + " " + authUID;
    }

    @Override
    public WModelClass getWModelClass() {
        return WModelClass.W_USER;
    }

    @Override
    public UniqueIds getUniqueId() {
        ArrayList<String> uniqueId = new ArrayList<>();
        uniqueId.add(this.wUserId);
        UniqueIds uniqueIds = new UniqueIds(uniqueId);
        return uniqueIds;
    }

    @Override
    public DbBody getDbBody() {
        return this;
    }
}
