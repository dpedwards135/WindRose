package com.davidparkeredwards.windrosetools.model.journey;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.journey.charge.Charge;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.CouponCode;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;

/**
 * Created by davidedwards on 5/31/17.
 */

public class Journey {

    //All Journeys are charged once finalized, but the final charge can be set earlier.
    private String journeyId;
    private JourneyType journeyType; //All the rules come from here
    private String itineraryId;
    private JourneyStatus journeyStatus;
    private JourneyPayStatus journeyPayStatus;

    //How is it routed?
        //GeoStop includes GeoPoint, appointment time, and window
    private GeoStop origin;
    private GeoStop destination;
    private GeoStop[] additionalStops;
    private GeoStop[] fixedRouteNodes;
    private String requestedDriverId;

    //How is the fare calculated? - Run everything on all of these, and only use what you need, gather the rest for data.
    private double mapCalculatedMiles;
    private double mapCalculatedTimeSeconds;
    private double meteredTimeSeconds;
    private double meteredMiles;
    private double waitTimeSeconds;

    private JourneyOption[] journeyOptions;
    private Toll[] mapCalculatedTolls;
    private Charge mapCalculatedCharge;
    private CouponCode couponCode;
    private Charge finalCharge;
    private String paymentType;

    //What is being transported?
    private Unit[] units;


    public enum JourneyStatus {

        IN_CONFIGURATION("in_configuration"),
        ACCEPTED("accepted"),
        PLANNED("planned"),
        ASSIGNED("assigned"),
        EN_ROUTE_TO_FIRST_STOP("en_route_to_first_stop"),
        IN_PROGRESS("in_progress"),
        COMPLETED("completed"),
        FINALIZED("finalized");


        private String status;

        private JourneyStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

    }

    public enum JourneyPayStatus {

        UNPAID("unpaid"),
        AUTHORIZED("authorized"),
        PAID("paid"),
        PAYMENT_FAILED("payment_failed");


        private String status;

        private JourneyPayStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

    }

    public JourneyType getJourneyType() {
        return journeyType;
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public void setJourneyType(JourneyType journeyType) {
        this.journeyType = journeyType;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public JourneyStatus getJourneyStatus() {
        return journeyStatus;
    }

    public void setJourneyStatus(JourneyStatus journeyStatus) {
        this.journeyStatus = journeyStatus;
    }

    public JourneyPayStatus getJourneyPayStatus() {
        return journeyPayStatus;
    }

    public void setJourneyPayStatus(JourneyPayStatus journeyPayStatus) {
        this.journeyPayStatus = journeyPayStatus;
    }

    public GeoStop getOrigin() {
        return origin;
    }

    public void setOrigin(GeoStop origin) {
        this.origin = origin;
    }

    public GeoStop getDestination() {
        return destination;
    }

    public void setDestination(GeoStop destination) {
        this.destination = destination;
    }

    public GeoStop[] getAdditionalStops() {
        return additionalStops;
    }

    public void setAdditionalStops(GeoStop[] additionalStops) {
        this.additionalStops = additionalStops;
    }

    public GeoStop[] getFixedRouteNodes() {
        return fixedRouteNodes;
    }

    public void setFixedRouteNodes(GeoStop[] fixedRouteNodes) {
        this.fixedRouteNodes = fixedRouteNodes;
    }

    public String getRequestedDriverId() {
        return requestedDriverId;
    }

    public void setRequestedDriverId(String requestedDriverId) {
        this.requestedDriverId = requestedDriverId;
    }

    public double getMapCalculatedMiles() {
        return mapCalculatedMiles;
    }

    public void setMapCalculatedMiles(double mapCalculatedMiles) {
        this.mapCalculatedMiles = mapCalculatedMiles;
    }

    public double getMapCalculatedTimeSeconds() {
        return mapCalculatedTimeSeconds;
    }

    public void setMapCalculatedTimeSeconds(double mapCalculatedTimeSeconds) {
        this.mapCalculatedTimeSeconds = mapCalculatedTimeSeconds;
    }

    public double getMeteredTimeSeconds() {
        return meteredTimeSeconds;
    }

    public void setMeteredTimeSeconds(double meteredTimeSeconds) {
        this.meteredTimeSeconds = meteredTimeSeconds;
    }

    public double getMeteredMiles() {
        return meteredMiles;
    }

    public void setMeteredMiles(double meteredMiles) {
        this.meteredMiles = meteredMiles;
    }

    public double getWaitTimeSeconds() {
        return waitTimeSeconds;
    }

    public void setWaitTimeSeconds(double waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }

    public Charge getMapCalculatedCharge() {
        return mapCalculatedCharge;
    }

    public void setMapCalculatedCharge(Charge mapCalculatedCharge) {
        this.mapCalculatedCharge = mapCalculatedCharge;
    }

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }

    public Charge getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(Charge finalCharge) {
        this.finalCharge = finalCharge;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    public Toll[] getMapCalculatedTolls() {
        return mapCalculatedTolls;
    }

    public void setMapCalculatedTolls(Toll[] mapCalculatedTolls) {
        this.mapCalculatedTolls = mapCalculatedTolls;
    }

    public JourneyOption[] getJourneyOptions() {
        return journeyOptions;
    }

    public void setJourneyOptions(JourneyOption[] journeyOptions) {
        this.journeyOptions = journeyOptions;
    }

    public Unit[] getUnits() {
        return units;
    }

    public void setUnits(Unit[] units) {
        this.units = units;
    }
}
