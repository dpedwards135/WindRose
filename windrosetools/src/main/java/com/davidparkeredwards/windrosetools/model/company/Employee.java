package com.davidparkeredwards.windrosetools.model.company;

import com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea.GeoPoint;

/**
 * Created by davidedwards on 6/1/17.
 */

public class Employee {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailId;
    private String officialIdNumber;
    private int[] roles;
    static final int DRIVER = 1;
    static final int MANAGER = 2;
    static final int ADMIN = 3;
    static final int PROCESSOR = 4;
    static final int CUSTOMER_REP = 5;
    private GeoPoint startingAddress;
    private GeoPoint home;

}
