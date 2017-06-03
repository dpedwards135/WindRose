package com.davidparkeredwards.windrosetools.model.journey.type;

/**
 * Created by davidedwards on 5/31/17.
 */

public class UnitType {

    private String unitTypeId;
    private String unitTypeName;

    private UnitRule[] unitRules;

    private int unitType;
    public static final int PERSONS = 1;
    public static final int ENVELOPE = 2;
    public static final int PARCEL = 3;
    public static final int LTL = 4;
    public static final int BULK = 5; //Includes FTL

}
