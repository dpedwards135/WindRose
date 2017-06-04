package com.davidparkeredwards.windrosetools.model.company;

import android.location.Address;

import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;

/**
 * Created by davidedwards on 5/30/17.
 */

public class Company {

    private String companyId;
    private String companyName;
    private String phone;
    private JourneyType[] journeyTypes;
    private VehicleType[] vehicleTypes;//TripType rules all set here. - Change to JourneyType or ItineraryType? or need multiple classes?
    private String[] employeeIds;
    private String[] vehicleIds;
    private String[] containerIds;
    private String[] preferredLocationIds;
    private String[] companyLocationIds;

    //Optional
    private Address address;

}
