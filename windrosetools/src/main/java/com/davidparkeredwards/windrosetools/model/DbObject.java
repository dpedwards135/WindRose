package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;

/**
 * Created by davidedwards on 6/14/17.
 */

public class DbObject implements DbBody{

    public String uniqueID;
    public HashMap<String, List<String>> objectValues;

    public DbObject() {}


    public DbObject(String uniqueID, HashMap<String, List<String>> objectValues) {
        this.uniqueID = uniqueID;
        this.objectValues = objectValues;
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
    public HashMap<String, List<String>> getObjectValues() {
        return objectValues;
    }
    @Exclude
    public void setObjectLists(HashMap<String, List<String>> objectValues) {
        this.objectValues = objectValues;
    }
}
