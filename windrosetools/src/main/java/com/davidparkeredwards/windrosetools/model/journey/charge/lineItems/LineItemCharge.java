package com.davidparkeredwards.windrosetools.model.journey.charge.lineItems;

import com.davidparkeredwards.windrosetools.model.geoTemporal.GeoTimeArea;
import com.davidparkeredwards.windrosetools.model.journey.Journey;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/2/17.
 */

public interface LineItemCharge {

    GeoTimeArea getGeoTimeArea();

    int getFlatOrPercent();

    double getFlatCharge();

    double getPercentCharge();

    OffsetDateTime getStartDate();

    OffsetDateTime getExpirationDate();

    boolean isValidChargeForJourney(Journey journey);

}
