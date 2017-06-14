package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.DbBody;

import java.util.HashMap;

/**
 * Created by davidedwards on 6/14/17.
 */

public class DbObject implements DbBody{

    public String uniqueID;
    public HashMap<String, String> objectValues;

    public DbObject() {}

    public DbObject(String uniqueID, HashMap<String, String> objectValues) {
        this.uniqueID = uniqueID;
        this.objectValues = objectValues;
    }
    @Override
    public DbBody getDbBody() {
        return this;
    }
}
