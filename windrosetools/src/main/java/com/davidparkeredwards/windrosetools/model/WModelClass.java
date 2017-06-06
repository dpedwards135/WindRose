package com.davidparkeredwards.windrosetools.model;

/**
 * Created by davidedwards on 6/6/17.
 */

public enum WModelClass {

    //AssetType
    CONTAINER_TYPE("container_type"),
    TRAILER_TYPE("trailer_type"),
    VEHICLE_TYPE("vehicle_type"),

    //Asset
    CONTAINER("container"),
    TRAILER("trailer"),
    VEHICLE("vehicle"),

    //Company
    COMPANY("company"),
    EMPLOYEE("employee"),
    FACILITY("facility"),

    //GeoTemporal
    GEO_STOP("geo_stop"),
    GEO_TIME_AREA("geo_time_area"),

    //GeoTemporal - GeoArea
    ADDRESS_POINT("address_point"),
    CUSTOM_GEO_POINT("custom_geo_point"),
    FULL_GEO_POINT("full_geo_point"),
    LAT_LONG_POINT("lat_long_point"),
    POST_CODE_AREA("post_code_area"),

    //GeoTemporal - TimeArea
    TIME_WINDOW("time_window"),

    //Journey
    ITINERARY("itinerary"),
    JOURNEY("journey"),
    JOURNEY_OPTION("journey_option"),
    TOLL("toll"),
    UNIT("unit"),

    //Journey - Charge
    CHARGE("charge"),
    CHARGE_RULE("charge_rule"),
    W_CURRENCY_TYPE("w_currency_type"),

    //Journey - Charge - LineItems
    COUPON_CODE("coupon_code"),
    CUSTOM_CHARGE("custom_charge"),
    GEO_TIME_AREA_CHARGE("geo_time_area_charge"),
    UNIT_CHARGE("unit_charge"),

    //Journey - Type
    JOURNEY_TYPE("journey_type"),
    UNIT_RULE("unit_rule"),
    UNIT_TYPE("unit_type");


    private String key;

    private WModelClass(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

}