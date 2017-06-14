package com.davidparkeredwards.windrosetools.model;

import com.davidparkeredwards.windrosetools.wForm.DbBody;

import java.util.HashMap;

/**
 * Created by davidedwards on 6/14/17.
 */

public class WModelObjectKeyAndValue implements DbBody {

    private String key;
    private HashMap hashMap;

    public WModelObjectKeyAndValue(String key, HashMap hashMap) {
        this.key = key;
        this.hashMap = hashMap;
    }

    public String getKey() {
        return key;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    @Override
    public DbBody getDbBody() {
        return this;
    }
}
