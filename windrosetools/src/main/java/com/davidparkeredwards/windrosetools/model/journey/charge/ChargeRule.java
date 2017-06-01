package com.davidparkeredwards.windrosetools.model.journey.charge;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/1/17.
 */

public class ChargeRule {

    //Base Charge Variables
    private double totalTime;
    private double totalMiles;
    private double additionalPassengers;
    private double additionalStops;
    private double waitTime;

    private double fixedFare;
    private double mileageMultiplier;
    private double baseFlatFee;
    private double additionalStopFee;

    private double baseCharge;

    //Coupon Code
    private String couponCode;

    //Surcharge Variables - Add flats to base, add all percents then multiply by base and add to base
    private double tollsSurchargeFlat;
    private double optionalsCharge;
    private double couponDiscountFlat;
    private double additionalPassengerFlat;
    private double afterHoursFlat;
    private double peakFlat;

    
    private double couponDiscountPercent;
    private double additionalPassengerPercent;
    private double afterHoursPercent;
    private double peakPercent;
    private ArrayList<GeoTimeAreaChargePercent> geoTimeAreaPercent; //Journey will iterate through this list to get any that apply
    private ArrayList<GeoTimeAreaChargeFlat> geoTimeAreaFlat; //Journey will iterate through this list to get any that apply

}
