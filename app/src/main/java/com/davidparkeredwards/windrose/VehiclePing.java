package com.davidparkeredwards.windrose;

/**
 * Created by davidedwards on 5/23/17.
 */

public class VehiclePing {

    public String location;
    public String localTime;
    public String driverName;

    public VehiclePing(String location, String localTime, String driverName) {
        this.location = location;
        this.localTime = localTime;
        this.driverName = driverName;
    }

}