package com.davidparkeredwards.windrosetools.model.journey;

import com.davidparkeredwards.windrosetools.model.journey.type.UnitType;

/**
 * Created by davidedwards on 5/31/17.
 */

public class Unit {

    //This needs to be a class rather than an interface because I intend for the customer to fully
    //define the Unit, rather than providing them with Unit options. Sort of like JourneyType and Journey.

    private String unitId;
    private UnitType unitType;

    //Person properties
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String officialIdNumber;


    //Envelope properties - All below build on this - All metric units will always be saved in Metric
    // and converted before saving if necessary
    private double weight;

    //Parcel properties
    private double height;
    private double width;
    private double length;

    //Freight properties
    private String[] nmfcCodes;
    private String[] hsCodes;

    //Bulk properties

}
