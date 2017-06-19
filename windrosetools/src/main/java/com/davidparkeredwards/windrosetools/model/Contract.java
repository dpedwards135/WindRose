package com.davidparkeredwards.windrosetools.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by davidedwards on 6/19/17.
 */

public class Contract extends DbObject{

    public Contract(String uniqueId, LinkedHashMap<String, List<String>> properties) {

        this.uniqueID = "contract" + uniqueId;
        this.properties = properties;
    }
}
