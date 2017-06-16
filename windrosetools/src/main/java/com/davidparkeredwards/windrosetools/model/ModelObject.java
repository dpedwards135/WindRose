package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 6/13/17.
 */

public abstract class ModelObject implements DbBody {

    //What if I just put the ModelObject stuff here, and let the others inherit the abstract classes?
    public ModelObject() {
    }

    public abstract String getKey();
    public abstract DbObject toDbObject();
    public abstract void fromDbObject(DbObject dbObject);
    public abstract WModelClass getWModelClass();
    public abstract String getDescription();
    public abstract UniqueIds getUniqueId();


    protected ArrayList<String> valueToList(String value) {
        ArrayList<String> list = new ArrayList<>(1);
        list.add(value);
        return list;
    }

    protected String singleValueFromList(List<String> list) {
        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
