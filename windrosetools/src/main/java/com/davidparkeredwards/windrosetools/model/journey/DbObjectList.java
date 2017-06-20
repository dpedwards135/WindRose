package com.davidparkeredwards.windrosetools.model.journey;

import com.davidparkeredwards.windrosetools.model.DbObject;
import com.davidparkeredwards.windrosetools.wForm.DbBody;
import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Created by davidedwards on 6/20/17.
 */

public class DbObjectList implements DbBody {

    public List<DbObject> dbObjectList;

    public DbObjectList() {}

    @Exclude
    @Override
    public DbBody getDbBody() {
        return this;
    }

    @Exclude
    public List<DbObject> getDbObjectList() {
        return dbObjectList;
    }
}
