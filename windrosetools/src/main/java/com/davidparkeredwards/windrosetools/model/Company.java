package com.davidparkeredwards.windrosetools.model;

import android.location.Address;

import com.davidparkeredwards.windrosetools.model.journey.JourneyType;

/**
 * Created by davidedwards on 5/30/17.
 */

public class Company {

    private String companyId;
    private String companyName;
    private String phone;
    private JourneyType[] journeyTypes; //TripType rules all set here. - Change to JourneyType or ItineraryType? or need multiple classes?
    private String[] employeeIds;
    private String[] powerIds;
    private String[] containerIds;
    private String[] preferredLocationIds;
    private String[] companyLocationIds;

    //Optional
    private Address address;

}
