package com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea;

/**
 * Created by davidedwards on 5/31/17.
 */

public class GeoPoint {

    private double longitude;
    private double latitude;
    private double geoFenceRadiusM;

    private String streetAddress;
    private String building;
    private String floor;
    private String room;

    private String city;
    private String county;
    private String postalCode;
    private String country;
    private String continent;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getGeoFenceRadiusM() {
        return geoFenceRadiusM;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom() {
        return room;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }
}
