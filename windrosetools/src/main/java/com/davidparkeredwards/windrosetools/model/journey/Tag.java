package com.davidparkeredwards.windrosetools.model.journey;

import java.util.List;

/**
 * Created by davidedwards on 6/12/17.
 */

public class Tag {

    public static final int BOOLEAN = 1;
    public static final int STRING = 2;
    public static final int NUMBER = 3;
    public static final int STRING_ARRAY = 4;
    public static final int NUMBER_ARRAY = 5;

    private String uniqueId;
    private String key;
    private List<String> value;
    private int valueType;

    public String getUniqueId() {
        return uniqueId;
    }

    public String getKey() {
        return key;
    }

    public List<String> getValue() {
        return value;
    }

    public int getValueType() {
        return valueType;
    }
}
