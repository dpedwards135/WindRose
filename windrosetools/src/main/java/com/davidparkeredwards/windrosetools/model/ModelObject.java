package com.davidparkeredwards.windrosetools.model;

import java.util.HashMap;

/**
 * Created by davidedwards on 6/13/17.
 */

public interface ModelObject {

    String getKey();
    HashMap<String, String> getValue();
    WModelClass getWModelClass();
}
