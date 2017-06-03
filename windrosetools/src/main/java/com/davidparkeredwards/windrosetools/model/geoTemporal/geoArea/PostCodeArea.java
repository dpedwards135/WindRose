package com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea;

/**
 * Created by davidedwards on 6/3/17.
 */

public class PostCodeArea implements GeoArea {

    private String postalCode;

    @Override
    public GeoPoint[] getBorders() {
        return new GeoPoint[0]; //Postal Codes are strings of points with no border
    }

    @Override
    public boolean contains(GeoPoint geoPoint) {
        if(geoPoint.getPostalCode() == null)
            return false;

        if(geoPoint.getPostalCode().equals(postalCode)) {
            return true;
        } else {
            return false;
        }
    }
}
