package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoStop;
import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;
import com.davidparkeredwards.windrosetools.model.journey.charge.ChargeUtils;

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
    public double getFlatCharge(OffsetDateTime journeyDate) {
        if (ChargeUtils.isValidForDate(journeyDate, this)) {
            return flatCharge;
        } else {
            return 0;
        }
    }

    @Override
    public double getPercentCharge(OffsetDateTime journeyDate) {
        if (ChargeUtils.isValidForDate(journeyDate, this)) {
            return percentCharge;
        } else {
            return 0;
        }
    }

    @Override
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    @Override
    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public GeoTimeArea getGeoTimeArea() {
        return geoTimeArea;
    }

    public void setGeoTimeArea(GeoTimeArea geoTimeArea) {
        this.geoTimeArea = geoTimeArea;
    }
}
