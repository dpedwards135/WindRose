package com.davidparkeredwards.windrosetools.model.geoTemporal;

import com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea.GeoArea;
import com.davidparkeredwards.windrosetools.model.geoTemporal.timeArea.TimeArea;

/**
 * Created by davidedwards on 5/31/17.
 */

public class GeoTimeArea {

    private String name;
    private TimeArea[] timeAreas;
    private GeoArea[] geoAreas;

    public boolean contains(GeoStop stop) {
        boolean containsTime = false;
        boolean containsGeoPoint = false;
        for(TimeArea timeArea : timeAreas) {
            if (timeArea.contains(stop.getAppointmentTime())) {
                containsTime = true;
                break;
            }
        }
        for(GeoArea geoArea : geoAreas) {
            if (geoArea.contains(stop.getGeoPoint())) {
                containsGeoPoint = true;
                break;
            }
        }

        return (containsTime && containsGeoPoint);
    }
}
