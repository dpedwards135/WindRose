package com.davidparkeredwards.windrosetools.model.journey.type;

/**
 * Created by davidedwards on 5/31/17.
 */

public class UnitType {

    private String unitTypeId;
    private String unitTypeName;

    private int unitType;
    public static final int PERSONS = 1;
    public static final int ENVELOPE = 2;
    public static final int PARCEL = 3;
    public static final int FREIGHT = 4;
    public static final int BULK = 5; //Includes FTL

    //Person properties


    //Envelope properties
    private String[] envelopeTypesNotPermitted;

    //Parcel properties
    private String[] parcelTypesNotPermitted;


    //Freight properties
    private String[] freightTypesNotPermitted;


    //Bulk properties
    private String[] bulkTypesNotPermitted;

}
