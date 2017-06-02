package com.davidparkeredwards.windrosetools.model.journey.type;

import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.JourneyOption;
import com.davidparkeredwards.windrosetools.model.journey.charge.ChargeRule;

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
    private FareType fareType;

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

    //Payment Calculation
    private String journeyTypeLaunchDate;   //JourneyType cannot be used for trips before this date
    private String journeyTypeExpirationDate; //JourneyType cannot be used for trips after this date
    private String afterHours; //Figure out how to apply Offset Time
    private String peakHours; //Figure out how to apply Offset Time
    private String[] paymentTypesAccepted;

    private ChargeRule chargeRule;

    /* Payment calculation: Calculate a pickup base rate and base rate per mile based on journeyType, then add or
     * subtract for GeoTimeAreas using either percentage bonus or fixed bonus. Additional:
     *  + or - GeoTimeAreas
        + or - for discount codes
        + or - for additional passengers
        + or - for additional stops
        + or - for different tripTypes
     *
     */

    public enum FareType {

        FIXED_FARE("fixed_fare"),
        MAP_CALCULATED_FARE("map_calculated_fare"),
        METERED_FARE("metered_fare"),
        METER_CHECKED_FARE("meter_checked_fare"),
        MILES_PLUS_WAIT_TIME("miles_plus_wait_time");


        private String fareType;

        private FareType(String fareType) {
            this.fareType = fareType;
        }

        public String getFareType() {
            return this.fareType;
        }

    }

    public enum PaymentTypes {

        CASH("cash"),
        CREDIT_DEBIT("credit_debit"),
        DIRECT_BILL("direct_bill"),
        ACH("ach"),
        CHECK("check");


        private String paymentType;

        private PaymentTypes(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getPaymentType() {
            return this.paymentType;
        }

    }

    public FareType getFareType() {
        return fareType;
    }

    public String getJourneyTypeId() {
        return journeyTypeId;
    }

    public void setJourneyTypeId(String journeyTypeId) {
        this.journeyTypeId = journeyTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public VehicleType[] getVehicleTypesAllowed() {
        return vehicleTypesAllowed;
    }

    public void setVehicleTypesAllowed(VehicleType[] vehicleTypesAllowed) {
        this.vehicleTypesAllowed = vehicleTypesAllowed;
    }

    public boolean isHasOrigin() {
        return hasOrigin;
    }

    public void setHasOrigin(boolean hasOrigin) {
        this.hasOrigin = hasOrigin;
    }

    public boolean isHasDestination() {
        return hasDestination;
    }

    public void setHasDestination(boolean hasDestination) {
        this.hasDestination = hasDestination;
    }

    public int getMaxAdditionalStopsAllowed() {
        return maxAdditionalStopsAllowed;
    }

    public void setMaxAdditionalStopsAllowed(int maxAdditionalStopsAllowed) {
        this.maxAdditionalStopsAllowed = maxAdditionalStopsAllowed;
    }

    public boolean isFixedRoute() {
        return isFixedRoute;
    }

    public void setFixedRoute(boolean fixedRoute) {
        isFixedRoute = fixedRoute;
    }

    public boolean isSharedTransport() {
        return isSharedTransport;
    }

    public void setSharedTransport(boolean sharedTransport) {
        isSharedTransport = sharedTransport;
    }

    public boolean isSupportsColocation() {
        return supportsColocation;
    }

    public void setSupportsColocation(boolean supportsColocation) {
        this.supportsColocation = supportsColocation;
    }

    public int getCanRequestSpecificDriver() {
        return canRequestSpecificDriver;
    }

    public void setCanRequestSpecificDriver(int canRequestSpecificDriver) {
        this.canRequestSpecificDriver = canRequestSpecificDriver;
    }

    public void setFareType(FareType fareType) {
        this.fareType = fareType;
    }

    public UnitType[] getUnitTypesPermitted() {
        return unitTypesPermitted;
    }

    public void setUnitTypesPermitted(UnitType[] unitTypesPermitted) {
        this.unitTypesPermitted = unitTypesPermitted;
    }

    public int getMaxUnitsAllowed() {
        return maxUnitsAllowed;
    }

    public void setMaxUnitsAllowed(int maxUnitsAllowed) {
        this.maxUnitsAllowed = maxUnitsAllowed;
    }

    public GeoStop[] getFixedRouteNodes() {
        return fixedRouteNodes;
    }

    public void setFixedRouteNodes(GeoStop[] fixedRouteNodes) {
        this.fixedRouteNodes = fixedRouteNodes;
    }

    public GeoTimeArea[] getOriginArea() {
        return originArea;
    }

    public void setOriginArea(GeoTimeArea[] originArea) {
        this.originArea = originArea;
    }

    public GeoTimeArea[] getDestinationArea() {
        return destinationArea;
    }

    public void setDestinationArea(GeoTimeArea[] destinationArea) {
        this.destinationArea = destinationArea;
    }

    public GeoTimeArea[] getAdditionalStopArea() {
        return additionalStopArea;
    }

    public void setAdditionalStopArea(GeoTimeArea[] additionalStopArea) {
        this.additionalStopArea = additionalStopArea;
    }

    public boolean isOriginHasAppointmentTime() {
        return originHasAppointmentTime;
    }

    public void setOriginHasAppointmentTime(boolean originHasAppointmentTime) {
        this.originHasAppointmentTime = originHasAppointmentTime;
    }

    public int getOriginWindowMinutes() {
        return originWindowMinutes;
    }

    public void setOriginWindowMinutes(int originWindowMinutes) {
        this.originWindowMinutes = originWindowMinutes;
    }

    public boolean isDestinationHasAppointmentTime() {
        return destinationHasAppointmentTime;
    }

    public void setDestinationHasAppointmentTime(boolean destinationHasAppointmentTime) {
        this.destinationHasAppointmentTime = destinationHasAppointmentTime;
    }

    public int getDestinationWindowMinutes() {
        return destinationWindowMinutes;
    }

    public void setDestinationWindowMinutes(int destinationWindowMinutes) {
        this.destinationWindowMinutes = destinationWindowMinutes;
    }

    public boolean isAdditionalStopsHaveAppointmentTime() {
        return additionalStopsHaveAppointmentTime;
    }

    public void setAdditionalStopsHaveAppointmentTime(boolean additionalStopsHaveAppointmentTime) {
        this.additionalStopsHaveAppointmentTime = additionalStopsHaveAppointmentTime;
    }

    public int getAdditionalStopsWindowMinutes() {
        return additionalStopsWindowMinutes;
    }

    public void setAdditionalStopsWindowMinutes(int additionalStopsWindowMinutes) {
        this.additionalStopsWindowMinutes = additionalStopsWindowMinutes;
    }

    public JourneyOption[] getOptions() {
        return options;
    }

    public void setOptions(JourneyOption[] options) {
        this.options = options;
    }

    public double getGoalJourneyDurationInSeconds() {
        return goalJourneyDurationInSeconds;
    }

    public void setGoalJourneyDurationInSeconds(double goalJourneyDurationInSeconds) {
        this.goalJourneyDurationInSeconds = goalJourneyDurationInSeconds;
    }

    public double getMaxJourneyDurationInSeconds() {
        return maxJourneyDurationInSeconds;
    }

    public void setMaxJourneyDurationInSeconds(double maxJourneyDurationInSeconds) {
        this.maxJourneyDurationInSeconds = maxJourneyDurationInSeconds;
    }

    public int getMaxStopsPerUnitOnJourney() {
        return maxStopsPerUnitOnJourney;
    }

    public void setMaxStopsPerUnitOnJourney(int maxStopsPerUnitOnJourney) {
        this.maxStopsPerUnitOnJourney = maxStopsPerUnitOnJourney;
    }

    public double getEstimatedSecondsPerStop() {
        return estimatedSecondsPerStop;
    }

    public void setEstimatedSecondsPerStop(double estimatedSecondsPerStop) {
        this.estimatedSecondsPerStop = estimatedSecondsPerStop;
    }

    public String getJourneyTypeLaunchDate() {
        return journeyTypeLaunchDate;
    }

    public void setJourneyTypeLaunchDate(String journeyTypeLaunchDate) {
        this.journeyTypeLaunchDate = journeyTypeLaunchDate;
    }

    public String getJourneyTypeExpirationDate() {
        return journeyTypeExpirationDate;
    }

    public void setJourneyTypeExpirationDate(String journeyTypeExpirationDate) {
        this.journeyTypeExpirationDate = journeyTypeExpirationDate;
    }

    public String getAfterHours() {
        return afterHours;
    }

    public void setAfterHours(String afterHours) {
        this.afterHours = afterHours;
    }

    public String getPeakHours() {
        return peakHours;
    }

    public void setPeakHours(String peakHours) {
        this.peakHours = peakHours;
    }

    public String[] getPaymentTypesAccepted() {
        return paymentTypesAccepted;
    }

    public void setPaymentTypesAccepted(String[] paymentTypesAccepted) {
        this.paymentTypesAccepted = paymentTypesAccepted;
    }

    public ChargeRule getChargeRule() {
        return chargeRule;
    }

    public void setChargeRule(ChargeRule chargeRule) {
        this.chargeRule = chargeRule;
    }
}
