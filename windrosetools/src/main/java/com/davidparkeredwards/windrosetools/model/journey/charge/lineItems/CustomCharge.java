package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/2/17.
 */

public class CustomCharge implements LineItemCharge {

    private GeoTimeArea geoTimeArea;
    private int flatOrPercent;
    private double flatCharge;
    private double percentCharge;
    private OffsetDateTime startDate;
    private OffsetDateTime expirationDate;

    @Override
    public GeoTimeArea getGeoTimeArea() {
        return geoTimeArea;
    }

    @Override
    public int getFlatOrPercent() {
        return flatOrPercent;
    }

    @Override
    public double getFlatCharge() {
        return flatCharge;
    }

    @Override
    public double getPercentCharge() {
        return percentCharge;
    }

    @Override
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    @Override
    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isValidChargeForJourney(Journey journey) {
        return false;
    }
}
