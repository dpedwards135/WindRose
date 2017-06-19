package com.davidparkeredwards.windrosetools.model.company;

import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;

/**
 * Created by davidedwards on 5/30/17.
 */

public class Company {

    private String companyId;
    private String name;
    private String phone;
    private String address;
    private String email;
    private JourneyType[] journeyTypes;
    private VehicleType[] vehicleTypes;//TripType rules all set here. - Change to JourneyType or ItineraryType? or need multiple classes?
    private String[] employeeIds;
    private String[] vehicleIds;
    private String[] containerIds;
    private String[] preferredLocationIds;
    private String[] companyLocationIds;

    private static final String CLASS_KEY = WModelClass.COMPANY.getKey();
    private static final String COMPANYID = "company_id";
    private static final String NAME = "company_name";
    private static final String PHONE = "company_phone";
    private static final String ADDRESS = "address";
    private static final String EMAIL_ADDRESS = "email_address";
    private static final String COMPANY_FINALIZE_BUTTONS = "company_finalize_buttons";



}
