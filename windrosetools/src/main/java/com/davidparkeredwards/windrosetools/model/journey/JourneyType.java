package com.davidparkeredwards.windrosetools.model.journey;

/**
 * Created by davidedwards on 5/30/17.
 */

public class JourneyType {
    /* One Unit per Journey
     * This is not a Journey, this is just the rules for setting one up
     * Journey Types can be created and deactivated, so that older journeys can still be validated
     * against the type the type they were created from
     */


    private String typeName;

    //How is it routed?
    protected boolean hasOrigin;
    protected boolean hasDestination;
    protected boolean isAdditionalStopEnabled;
    protected boolean isFixedRoute;
    protected boolean isSharedTransport;

    //How is the fare calculated?
        //Do everything in seconds for precision
    protected int fareType;
    static final int FIXED_FARE = 1;
    static final int PRECALCULATED_FARE = 2;
    static final int METERED_FARE = 3;
    static final int METER_CHECKED_FARE = 4;
    static final int MILES_PLUS_WAIT_TIME = 5;

    //What is being transported?
    protected boolean isThingsJourney;
    protected boolean isPassengerJourney;

    //Where can Journey take place?
        //GeoArea includes GeoPoints and preferredLocations
    protected GeoStop[] fixedRouteNodes;
    protected GeoTimeArea[] originArea;
    protected GeoTimeArea[] destinationArea;
    protected GeoTimeArea[] additionalStopArea;

    //Time constraints
    protected boolean originHasAppointmentTime;
    protected int originWindowMinutes;
    protected boolean destinationHasAppointmentTime;
    protected int destinationWindowMinutes;
    protected boolean additionalStopsHaveAppointmentTime;
    protected int additionalStopsWindowMinutes;

    //Journey Rules
    protected JourneyAuthorizationRules[] journeyAuthorizationRules;
    protected JourneyExecutionRules[] journeyExecutionRules;



}
