package com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea;

/**
 * Created by davidedwards on 5/31/17.
 */

 public interface GeoPoint {

    double getLongitude();

    double getLatitude();

    double getGeoFenceRadiusM();

    String getStreetAddress();

    String getBuilding();

    String getFloor();

    String getRoom();

    String getCity();

    String getCounty();

    String getPostalCode();

    String getCountry();

    String getContinent();

    String getRoomLocationIdentifier();
}
