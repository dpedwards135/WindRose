package com.davidparkeredwards.windrosetools.model;

/**
 * Created by davidedwards on 6/6/17.
 */

public enum WModelClass {

    //Contract that tells what kind of Class it is and where it is indexed
    //Index logic, x has y. ie A company has vehicles. A user doesn't have vehicles.
    //A user has companies, but not business units. A company doesn't have companies.

    //WUser
    W_USER("w_user", true, true, false),

    //AssetType
    CONTAINER_TYPE("container_type", true, true, false),
    TRAILER_TYPE("trailer_type", true, true, false),
    VEHICLE_TYPE("vehicle_type", true, true, true),

    //Asset
    CONTAINER("container", true, true, false),
    TRAILER("trailer", true, true, false),
    VEHICLE("vehicle", true, true, false),

    //Company
    COMPANY("company", true, false, true),
    BUSINESS_UNIT("business_unit", true, true, false),
    EMPLOYEE("employee", true, true, false),
    FACILITY("facility", true, true, false),

    //GeoTemporal
    GEO_STOP("geo_stop", true, true, false),
    GEO_TIME_AREA("geo_time_area", true, true, false),

    //GeoTemporal - GeoArea
    ADDRESS_POINT("address_point", true, true, true),
    CUSTOM_GEO_POINT("custom_geo_point", true, true, true),
    FULL_GEO_POINT("full_geo_point", true, true, true),
    LAT_LONG_POINT("lat_long_point", true, true, true),
    POST_CODE_AREA("post_code_area", true, true, true),

    //GeoTemporal - TimeArea
    TIME_WINDOW("time_window", true, true, false),

    //Journey
    ITINERARY("itinerary", true, true, true),
    JOURNEY("journey", true, true, true),
    JOURNEY_OPTION("journey_option", true, true, true),
    TOLL("toll", true, true, false),
    UNIT("unit", true, true, true),

    //Journey - Charge
    CHARGE("charge", true, true, true),
    CHARGE_RULE("charge_rule", true, true, false),
    W_CURRENCY_TYPE("w_currency_type", true, true, false),

    //Journey - Charge - LineItems
    COUPON_CODE("coupon_code", true, true, true),
    CUSTOM_CHARGE("custom_charge", true, true, true),
    GEO_TIME_AREA_CHARGE("geo_time_area_charge", true, true, true),
    UNIT_CHARGE("unit_charge", true, true, true),

    //Journey - Type
    JOURNEY_TYPE("journey_type", true, true, false),
    UNIT_RULE("unit_rule", true, true, false),
    UNIT_TYPE("unit_type", true, true, false);


    private String key;
    private boolean isUserIndexed;
    private boolean isCompanyIndexed;
    private boolean isWindroseIndexed;

    private WModelClass(String key, boolean isWindroseIndexed, boolean isCompanyIndexed, boolean isUserIndexed) {
        this.key = key;
        this.isCompanyIndexed = isCompanyIndexed;
        this.isUserIndexed = isUserIndexed;
        this.isWindroseIndexed = isWindroseIndexed;
    }

    public String getKey() {
        return this.key;
    }

    public boolean getIsUserIndexed() {
        return this.isUserIndexed;
    }

    public boolean getIsCompanyIndexed() {
        return this.isCompanyIndexed;
    }

    public boolean getIsWindroseIndexed() {
        return this.isWindroseIndexed;
    }

}