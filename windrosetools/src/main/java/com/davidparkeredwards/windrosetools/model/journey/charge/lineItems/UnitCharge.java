package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/2/17.
 */

public class UnitCharge implements LineItemCharge {
    @Override
    public GeoTimeArea getGeoTimeArea() {
        return null;
    }

    @Override
    public int getFlatOrPercent() {
        return 0;
    }

    @Override
    public double getFlatCharge() {
        return 0;
    }

    @Override
    public double getPercentCharge() {
        return 0;
    }

    @Override
    public OffsetDateTime getStartDate() {
        return null;
    }

    @Override
    public OffsetDateTime getExpirationDate() {
        return null;
    }

    @Override
    public boolean isValidChargeForJourney(Journey journey) {
        return false;
    }
}
