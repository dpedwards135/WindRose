package com.davidparkeredwards.windrosetools.model.journey.charge;

import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.CouponCode;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.GeoTimeAreaCharge;
import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.GeoTimeAreaChargePercent;

import java.util.ArrayList;

/**
 * Created by davidedwards on 6/1/17.
 */

public class ChargeRule implements ChargeMethods{

    private String currency; //WCurrencyType to String

    private double fixedFare;
    private double mileageMultiplier;
    private double baseFlatFee;
    private double additionalStopFee;


    //Coupon Code
    private CouponCode couponCode;

    //Surcharge Variables - Add flats to base, add all percents then multiply by base and add to base
    private double tollsSurchargeFlat;
    private double optionalsCharge;
    private double couponDiscountFlat;
    private double additionalPassengerFlat;
    private double afterHoursFlat;
    private double peakFlat;
    private ArrayList<CustomChargeFlat> customChargeFlats;
    private ArrayList<GeoTimeAreaCharge> geoTimeAreaFlat; //Journey will iterate through this list to get any that apply


    private double couponDiscountPercent;
    private double additionalPassengerPercent;
    private double afterHoursPercent;
    private double peakPercent;
    private ArrayList<CustomChargePercent> customChargePercents;
    private ArrayList<GeoTimeAreaChargePercent> geoTimeAreaPercent; //Journey will iterate through this list to get any that apply

    private double baseCharge;
    private double totalFlatCharge;
    private double totalPercentCharge;
    private double totalCharge;

    //Black box? Calculate and it churns it out?

    public ChargeRule() {

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

    public CouponCode getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(CouponCode couponCode) {
        this.couponCode = couponCode;
    }

    public double getTollsSurchargeFlat() {
        return tollsSurchargeFlat;
    }

    public void setTollsSurchargeFlat(double tollsSurchargeFlat) {
        this.tollsSurchargeFlat = tollsSurchargeFlat;
    }

    public double getOptionalsCharge() {
        return optionalsCharge;
    }

    public void setOptionalsCharge(double optionalsCharge) {
        this.optionalsCharge = optionalsCharge;
    }

    public double getCouponDiscountFlat() {
        return couponDiscountFlat;
    }

    public void setCouponDiscountFlat(double couponDiscountFlat) {
        this.couponDiscountFlat = couponDiscountFlat;
    }

    public double getAdditionalPassengerFlat() {
        return additionalPassengerFlat;
    }

    public void setAdditionalPassengerFlat(double additionalPassengerFlat) {
        this.additionalPassengerFlat = additionalPassengerFlat;
    }

    public double getAfterHoursFlat() {
        return afterHoursFlat;
    }

    public void setAfterHoursFlat(double afterHoursFlat) {
        this.afterHoursFlat = afterHoursFlat;
    }

    public double getPeakFlat() {
        return peakFlat;
    }

    public void setPeakFlat(double peakFlat) {
        this.peakFlat = peakFlat;
    }

    public ArrayList<GeoTimeAreaCharge> getGeoTimeAreaFlat() {
        return geoTimeAreaFlat;
    }

    public void setGeoTimeAreaFlat(ArrayList<GeoTimeAreaCharge> geoTimeAreaFlat) {
        this.geoTimeAreaFlat = geoTimeAreaFlat;
    }

    public double getCouponDiscountPercent() {
        return couponDiscountPercent;
    }

    public void setCouponDiscountPercent(double couponDiscountPercent) {
        this.couponDiscountPercent = couponDiscountPercent;
    }

    public double getAdditionalPassengerPercent() {
        return additionalPassengerPercent;
    }

    public void setAdditionalPassengerPercent(double additionalPassengerPercent) {
        this.additionalPassengerPercent = additionalPassengerPercent;
    }

    public double getAfterHoursPercent() {
        return afterHoursPercent;
    }

    public void setAfterHoursPercent(double afterHoursPercent) {
        this.afterHoursPercent = afterHoursPercent;
    }

    public double getPeakPercent() {
        return peakPercent;
    }

    public void setPeakPercent(double peakPercent) {
        this.peakPercent = peakPercent;
    }

    public ArrayList<GeoTimeAreaChargePercent> getGeoTimeAreaPercent() {
        return geoTimeAreaPercent;
    }

    public void setGeoTimeAreaPercent(ArrayList<GeoTimeAreaChargePercent> geoTimeAreaPercent) {
        this.geoTimeAreaPercent = geoTimeAreaPercent;
    }

    public double getBaseCharge() {
        return baseCharge;
    }

    public void setBaseCharge(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    public double getTotalFlatCharge() {
        return totalFlatCharge;
    }

    public void setTotalFlatCharge(double totalFlatCharge) {
        this.totalFlatCharge = totalFlatCharge;
    }

    public double getTotalPercentCharge() {
        return totalPercentCharge;
    }

    public void setTotalPercentCharge(double totalPercentCharge) {
        this.totalPercentCharge = totalPercentCharge;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }


}



//If using Square (in theory) - Users set up their own accounts and are allowed to link them into
//the app. Every different type of currency will require a different Square account. All that is
//required for currency management in the app will be to set the type of currency required for each
//trip in the ChargeRule. That is the number and currency that will be passed to Square.