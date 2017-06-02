package com.davidparkeredwards.windrosetools.model.journey.charge;

import com.davidparkeredwards.windrosetools.model.journey.Journey;
import com.davidparkeredwards.windrosetools.model.journey.JourneyOption;
import com.davidparkeredwards.windrosetools.model.journey.Toll;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.CouponCode;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.LineItemCharge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by davidedwards on 6/2/17.
 */

public class Charge implements ChargeMethods{
    //This is basically the charge calculation for invoice that could in theory be formatted, printed, and sent as receipt or invoice

    //Base Fare variables
    private double fixedFare;
    private double mileageMultiplier;
    private double baseFlatFee;
    private double additionalStopFee;

    //Surcharge Variables - Add flats to base, add all percents then multiply by base and add to base
    private double tollsSurcharge;
    private double optionalsCharge;

    private ArrayList<CouponCode> couponCodes;
    private ArrayList<LineItemCharge> appliedLineItemCharges;
    private ArrayList<LineItemCharge> appliedCouponCodes;

    private double baseCharge = 0;
    private double totalFlatCharge = 0;
    private double surchargePercentCharge = 0;
    private double couponPercentDiscount = 0;
    private double couponFlatDiscount = 0;
    private double subTotalCharge = 0;
    private double totalDiscount = 0;
    private double totalCharge = 0;
    private double finalChargeToCustomer = 0;

    private double gratuity = 0;

    public Charge(Journey journey, double gratuity) {

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
        couponCodes = new ArrayList<CouponCode>(Arrays.asList(journey.getCouponCodes()));

        //Calculate Surcharges and Discounts
        for(Toll toll : journey.getMapCalculatedTolls()) {
            tollsSurcharge += toll.getFee();
        }

        for(JourneyOption option : journey.getJourneyOptions()) {
            optionalsCharge += option.getPrice();
        }

        for (LineItemCharge charge : journey.getCouponCodes()) {
            assignChargeToFlatOrPercent(charge, journey);
        }
        for (LineItemCharge charge : chargeRule.getLineItemCharges()) {
            assignChargeToFlatOrPercent(charge, journey);
        }

        //Total everything up
        subTotalCharge = (baseCharge + totalFlatCharge + (surchargePercentCharge * baseCharge));
        totalDiscount = couponFlatDiscount + (subTotalCharge * couponPercentDiscount);
        totalCharge = subTotalCharge - totalDiscount;

        //Check for negative total
        if (totalCharge < 0) {
            totalCharge = 0;
        }

        if(chargeRule.isGratuityIsAccepted()) {
            gratuity = journey.getGratuity();
        }

        finalChargeToCustomer = totalCharge + gratuity;

        return;


    }

    public void assignChargeToFlatOrPercent(LineItemCharge charge, Journey journey) {
        if(charge.isValidChargeForJourney(journey)) {
            if(charge.getClass() == CouponCode.class) {
                couponFlatDiscount += charge.getFlatCharge();
                couponPercentDiscount += charge.getPercentCharge();
                appliedCouponCodes.add(charge);
            } else {
                totalFlatCharge += charge.getFlatCharge();
                surchargePercentCharge += charge.getPercentCharge();
                appliedLineItemCharges.add(charge);
            }

        } else {
            return;
        }
    }
}
