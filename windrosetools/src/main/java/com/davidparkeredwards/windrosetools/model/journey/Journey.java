package com.davidparkeredwards.windrosetools.model.journey;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;

/**
 * Created by davidedwards on 5/31/17.
 */

public class Journey {

    private String journeyId;
    private JourneyType journeyType; //All the rules come from here
    private String itineraryId;

    //How is it routed?
        //GeoStop includes GeoPoint, appointment time, and window
    private GeoStop origin;
    private GeoStop destination;
    private GeoStop[] additionalStops;
    private GeoStop[] fixedRouteNodes;
    private String requestedDriverId;

    //How is the fare calculated?
    private double fixedfare;
    private double preCalculatedMiles;
    private double preCalculatedTimeSeconds;
    private double meteredTimeSeconds;
    private double meteredMiles;
    private double waitTimeSeconds;

    //What is being transported?
    private Unit unit;
    private Unit[] colocatedUnits;

}
