package com.davidparkeredwards.windrosetools.model.journey.type;

import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.JourneyOption;

/**
 * Created by davidedwards on 5/30/17.
 */

public class JourneyType {
    /* One Unit per Journey
     * This is not a Journey, this is just the rules for setting one up
     * Journey Types can be created and deactivated, so that older journeys can still be validated
     * against the type the type they were created from
     *
     * As users fill out the journey request each step is validated as they go and errors are pointed
     * out for correction so that by the time they get to submit they have one that conforms with all
     * of the field rules.
     *
     * At the end the JourneyAuthorization rules are checked, these are the ones that ought to be
     * displayed to the customer as they go anyway. These include:
     *  1. Pickup time
     *
     */


    private String journeyTypeId;
    private String typeName;
    private VehicleType[] vehicleTypesAllowed;

    //How is it routed?
    private boolean hasOrigin;
    private boolean hasDestination;
    private int maxAdditionalStopsAllowed;
    private boolean isFixedRoute;
    private boolean isSharedTransport;
    private boolean supportsColocation;

    private int canRequestSpecificDriver;
    static final int NO_REQUEST = 1;
    static final int SOFT_REQUEST = 2;
    static final int HARD_REQUEST = 3;

    //How is the fare calculated?
        //Do everything in seconds for precision
    private int fareType;
    static final int FIXED_FARE = 1;
    static final int PRECALCULATED_FARE = 2;
    static final int METERED_FARE = 3;
    static final int METER_CHECKED_FARE = 4;
    static final int MILES_PLUS_WAIT_TIME = 5;

    //What is being transported?
    private UnitType[] unitTypesPermitted;
    private int maxUnitsAllowed;

    //Where can Journey take place?
        //GeoArea includes GeoPoints and preferredLocations
    private GeoStop[] fixedRouteNodes;
    private GeoTimeArea[] originArea;
    private GeoTimeArea[] destinationArea;
    private GeoTimeArea[] additionalStopArea;

    //Time constraints
    private boolean originHasAppointmentTime;
    private int originWindowMinutes;
    private boolean destinationHasAppointmentTime;
    private int destinationWindowMinutes;
    private boolean additionalStopsHaveAppointmentTime;
    private int additionalStopsWindowMinutes;

    //Options available
    private JourneyOption[] options;

    //Journey Rules
    private double goalJourneyDurationInSeconds;
    private double maxJourneyDurationInSeconds;
    private int maxStopsPerUnitOnJourney;
    private double estimatedSecondsPerStop;




}
