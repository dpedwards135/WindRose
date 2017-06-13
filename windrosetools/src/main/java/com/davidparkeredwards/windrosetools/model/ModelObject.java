package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.UniqueIds;

import java.util.HashMap;

/**
 * Created by davidedwards on 6/13/17.
 */

public abstract class ModelObject  {

    public abstract String getKey();
    public abstract HashMap<String, String> getValue();
    public abstract WModelClass getWModelClass();
    public abstract String getDescription();
    public abstract UniqueIds getUniqueId();
}
