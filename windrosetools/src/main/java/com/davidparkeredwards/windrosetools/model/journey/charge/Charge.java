package com.davidparkeredwards.windrosetools.model.journey.charge;

import com.davidparkeredwards.windrosetools.model.journey.Journey;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.CouponCode;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.CustomCharge;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.GeoTimeAreaCharge;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.LineItemCharge;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.UnitCharge;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/2/17.
 */

public class Charge implements ChargeMethods{
    //This is basically an invoice that could in theory be formatted, printed, and sent as receipt or invoice

    private double fixedFare;
    private double mileageMultiplier;
    private double baseFlatFee;
    private double additionalStopFee;

    //Coupon Code
    private CouponCode couponCode;

    //Surcharge Variables - Add flats to base, add all percents then multiply by base and add to base
    private double tollsSurcharge;
    private double optionalsCharge;

    private ArrayList<CustomCharge> customCharges;
    private ArrayList<GeoTimeAreaCharge> geoTimeAreaCharges; //Journey will iterate through this list to get any that apply
    private ArrayList<UnitCharge> unitCharges;


    private double baseCharge = 0;
    private double totalFlatCharge = 0;
    private double totalPercentCharge = 0;
    private double totalCharge = 0;

    private double adminChargeOverride = NO_OVERRIDE;
    public static final double NO_OVERRIDE = -1;

    private double gratuity = 0; //Add Gratuity permitted to rules.


    public Charge(Journey journey, double adminChargeOverride, double gratuity) {

        this.adminChargeOverride = adminChargeOverride;
        if(this.adminChargeOverride != NO_OVERRIDE) {
            totalCharge = this.adminChargeOverride;
            if(totalCharge < 0) {
                totalCharge = 0;
            }
            return;
        }
        ChargeRule chargeRule = journey.getJourneyType().getChargeRule();



        //Calculate Base Fare
        fixedFare = chargeRule.getFixedFare();
        mileageMultiplier = chargeRule.getMileageMultiplier() * journey.getMapCalculatedMiles();
        baseFlatFee = chargeRule.getBaseFlatFee();
        additionalStopFee = chargeRule.getAdditionalStopFee() * journey.getAdditionalStops().length;
        baseCharge = fixedFare +
                mileageMultiplier +
                baseFlatFee +
                additionalStopFee;

        //Check and process coupon code
        couponCode = journey.getCouponCode();
        assignChargeToFlatOrPercent(couponCode, journey);

        //Calculate Surcharges and Discounts
        for(LineItemCharge charge : customCharges) {
            assignChargeToFlatOrPercent(charge, journey);
        }
        for(LineItemCharge charge : geoTimeAreaCharges) {
            assignChargeToFlatOrPercent(charge, journey);
        }
        for(LineItemCharge charge : unitCharges) {
            assignChargeToFlatOrPercent(charge, journey);
        }

        //Total everything up
        totalCharge = baseCharge + totalFlatCharge + (totalPercentCharge * baseCharge);

        //Check for negative total
        if(totalCharge < 0) {
            totalCharge = 0;
        }
        return;

    }


        /*

        JourneyType.FareType fareType = journey.getJourneyType().getFareType();

        switch(fareType) {
            case FIXED_FARE:
                totalCharge = chargeWithFixedFare();
                break;
            case METERED_FARE:
                totalCharge = chargeWithMeteredFare();
                break;
            case METER_CHECKED_FARE:
                totalCharge = chargeWithMeterCheckedFare();
                break;
            case MAP_CALCULATED_FARE:
                totalCharge = chargeWithMapcalculatedFare();
                break;
            case MILES_PLUS_WAIT_TIME:
                totalCharge = chargeWithMilesPlusWaitTimeFare();
                break;
        }
        */

    public void assignChargeToFlatOrPercent(LineItemCharge charge, Journey journey) {
        if(charge.isValidChargeForJourney(journey)) {
            totalFlatCharge += charge.getFlatCharge();
            totalPercentCharge += charge.getPercentCharge();
        } else {
            return;
        }

    }


}
