package com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea;

/**
 * Created by davidedwards on 6/3/17.
 */

public abstract class FullGeoPoint {

    //Add possible properties of any given GeoPoint Type here and use constructors to assign them in the Child
    private String name;

    private double longitude;
    private double latitude;
    private double geoFenceRadiusM;

    private String streetAddress;
    private String building;
    private String floor;
    private String room;
    private String roomLocationIdentifier;

    private String city;
    private String county;
    private String postalCode;
    private String country;
    private String continent;

    public String getName() {
        return name;
    }

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

    public String getRoomLocationIdentifier() {
        return roomLocationIdentifier;
    }
}

