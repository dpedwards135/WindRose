package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.davidparkeredwards.windrosetools.wForm.UniqueIds;

/**
 * Created by davidedwards on 6/13/17.
 */

public abstract class ModelObject implements DbBody {

    //What if I just put the ModelObject stuff here, and let the others inherit the abstract classes?
    public ModelObject() {
    }

    public abstract String getKey();
    //public abstract HashMap<String, String> getValue();
    public abstract DbObject toDbObject();
    public abstract void fromDbObject(DbObject dbObject);
    //public abstract ModelObject fromHashMapValue(WModelObjectKeyAndValue mkv);
    public abstract WModelClass getWModelClass();
    public abstract String getDescription();
    public abstract UniqueIds getUniqueId();
}
