package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/2/17.
 */

public class CouponCode implements LineItemCharge{

    private GeoTimeArea geoTimeArea;
    private String code;
    private int flatOrPercent;
    private OffsetDateTime validFrom;
    private OffsetDateTime validUntil;
    private boolean singleUserCode;
    private String singleUserID;
    private int maxUsesPerUser;

    private double flatDiscount;
    private double percentDiscount;

    public CouponCode(String code, int flatOrPercent, double flatDiscount, double percentDiscount,
                      OffsetDateTime validFrom, OffsetDateTime validUntil, boolean singleUserCode,
                      String singleUserID, int maxUsesPerUser) {
        this.code = code;
        this.flatOrPercent = flatOrPercent;
        this.flatDiscount = 0-Math.abs(flatDiscount);
        this.percentDiscount = 0-Math.abs(percentDiscount);
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.singleUserCode = singleUserCode;
        this.singleUserID = singleUserID;
        this.maxUsesPerUser = maxUsesPerUser;
    }


    @Override
    public GeoTimeArea getGeoTimeArea() {
        return geoTimeArea;
    }

    @Override
    public int getFlatOrPercent() {
        return flatOrPercent;
    }

    @Override
    public double getFlatCharge(OffsetDateTime journeyDate) {
        return flatDiscount;
    }

    @Override
    public double getPercentCharge(OffsetDateTime journeyDate) {
        return percentDiscount;
    }

    @Override
    public OffsetDateTime getStartDate() {
        return validFrom;
    }

    @Override
    public OffsetDateTime getExpirationDate() {
        return validUntil;
    }

    @Override
    public boolean isValidChargeForJourney(Journey journey) {
        return false;
    }
}
