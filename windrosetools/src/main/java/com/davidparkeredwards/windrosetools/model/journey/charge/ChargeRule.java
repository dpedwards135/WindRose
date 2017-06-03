package com.davidparkeredwards.windrosetools.model.journey.charge;

import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.LineItemCharge;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/1/17.
 */

public class ChargeRule implements ChargeMethods{

    private String currency; //WCurrencyType to String

    private FareType fareType;

    private double fixedFare;
    private double mileageMultiplier;
    private double baseFlatFee;
    private double additionalStopFee;

    private ArrayList<LineItemCharge> lineItemCharges;

    private boolean chargeTollFees;
    private boolean canUseMultipleCouponCodes;
    private boolean gratuityIsAccepted;

    private String[] paymentTypesAccepted;
    private int maxCouponCodesAccepted = 1; //Default is one, allows for a voucher, doesn't allow for overkill

    public ChargeRule(String currency, FareType fareType, double fixedFare, double mileageMultiplier,
                      double baseFlatFee, double additionalStopFee, ArrayList<LineItemCharge> lineItemCharges,
                      boolean chargeTollFees, boolean canUseMultipleCouponCodes, boolean gratuityIsAccepted,
                      String[] paymentTypesAccepted, int maxCouponCodesAccepted) {
        this.currency = currency;
        this.fareType = fareType;
        this.fixedFare = fixedFare;
        this.mileageMultiplier = mileageMultiplier;
        this.baseFlatFee = baseFlatFee;
        this.additionalStopFee = additionalStopFee;
        this.lineItemCharges = lineItemCharges;
        this.chargeTollFees = chargeTollFees;
        this.canUseMultipleCouponCodes = canUseMultipleCouponCodes;
        this.gratuityIsAccepted = gratuityIsAccepted;
        this.paymentTypesAccepted = paymentTypesAccepted;
        this.maxCouponCodesAccepted = maxCouponCodesAccepted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFixedFare() {
        return fixedFare;
    }

    public void setFixedFare(double fixedFare) {
        this.fixedFare = fixedFare;
    }

    public double getMileageMultiplier() {
        return mileageMultiplier;
    }

    public void setMileageMultiplier(double mileageMultiplier) {
        this.mileageMultiplier = mileageMultiplier;
    }

    public double getBaseFlatFee() {
        return baseFlatFee;
    }

    public void setBaseFlatFee(double baseFlatFee) {
        this.baseFlatFee = baseFlatFee;
    }

    public double getAdditionalStopFee() {
        return additionalStopFee;
    }

    public void setAdditionalStopFee(double additionalStopFee) {
        this.additionalStopFee = additionalStopFee;
    }

    public ArrayList<LineItemCharge> getLineItemCharges() {
        return lineItemCharges;
    }

    public void setLineItemCharges(ArrayList<LineItemCharge> lineItemCharges) {
        this.lineItemCharges = lineItemCharges;
    }

    public boolean isCanUseMultipleCouponCodes() {
        return canUseMultipleCouponCodes;
    }

    public void setCanUseMultipleCouponCodes(boolean canUseMultipleCouponCodes) {
        this.canUseMultipleCouponCodes = canUseMultipleCouponCodes;
    }

    public boolean isGratuityIsAccepted() {
        return gratuityIsAccepted;
    }

    public void setGratuityIsAccepted(boolean gratuityIsAccepted) {
        this.gratuityIsAccepted = gratuityIsAccepted;
    }

    public boolean isChargeTollFees() {
        return chargeTollFees;
    }

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
}



//If using Square (in theory) - Users set up their own accounts and are allowed to link them into
//the app. Every different type of currency will require a different Square account. All that is
//required for currency management in the app will be to set the type of currency required for each
//trip in the ChargeRule. That is the number and currency that will be passed to Square.