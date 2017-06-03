package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/1/17.
 */

public class GeoTimeAreaCharge implements LineItemCharge{

    private GeoTimeArea geoTimeArea;
    private int flatOrPercent;
    private OffsetDateTime startDate;
    private OffsetDateTime expirationDate;
    private double flatCharge;
    private double percentCharge;


    public boolean appliesToJourney(Journey journey) {
        for(GeoStop stop : journey.getAdditionalStops()) {
            if(geoTimeArea.contains(stop)) {
                return true;
            }
        }
        if (geoTimeArea.contains(journey.getOrigin())
                && geoTimeArea.contains(journey.getDestination())) {
            return true;
        } else {
            return false;
        }
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
    @Override
    public GeoTimeArea getGeoTimeArea() {
        return geoTimeArea;
    }
}
