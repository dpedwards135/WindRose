package com.davidparkeredwards.windrosetools.wForm;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/8/17.
 */

public class UniqueIds implements DbBody {

    private ArrayList<String> uniqueIds;

    public UniqueIds(ArrayList<String> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }

    @Override
    public DbBody getDbBody() {
        return this;
    }

    public ArrayList<String> getUniqueIds() {
        return uniqueIds;
    }
}
