package com.davidparkeredwards.windrosetools.model.geoTemporal;

import com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea.GeoPoint;
import com.davidparkeredwards.windrosetools.model.geoTemporal.timeArea.TimeWindow;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 5/31/17.
 */

public class GeoStop {

    GeoPoint geoPoint;
    OffsetDateTime appointmentTime;
    TimeWindow appointmentWindow;

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public OffsetDateTime getAppointmentTime() {
        return appointmentTime;
    }
}
