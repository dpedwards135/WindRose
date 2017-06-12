package com.davidparkeredwards.windrosetools.model.company;

import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.WModelClass;
import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;
import com.davidparkeredwards.windrosetools.wForm.WCheckBox;
import com.davidparkeredwards.windrosetools.wForm.WFinalizeButtons;
import com.davidparkeredwards.windrosetools.wForm.WForm;
import com.davidparkeredwards.windrosetools.wForm.WFormSource;
import com.davidparkeredwards.windrosetools.wForm.WGeoStop;
import com.davidparkeredwards.windrosetools.wForm.WSelectFrom;
import com.davidparkeredwards.windrosetools.wForm.WTextEdit;
import com.davidparkeredwards.windrosetools.wForm.WTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidedwards on 5/30/17.
 */

public class Company implements WFormSource {

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


    @Override
    public WForm getForm() {


        String userId = WindroseApplication.currentWUser.getWUserId();
        String companyId = WindroseApplication.getCompanyID();
        String classKey = CLASS_KEY;
        boolean isSubmitted = false;

        List<String> fieldIdOrder = new ArrayList<>();
        fieldIdOrder.add(COMPANYID);
        fieldIdOrder.add(NAME);
        fieldIdOrder.add(PHONE);
        fieldIdOrder.add(ADDRESS);
        fieldIdOrder.add(EMAIL_ADDRESS);
        fieldIdOrder.add(COMPANY_FINALIZE_BUTTONS);

        List<WCheckBox> checkBoxes = new ArrayList<>();

        List<WFinalizeButtons> finalizeButtons = new ArrayList<>();
        finalizeButtons.add(new WFinalizeButtons(COMPANY_FINALIZE_BUTTONS, true, true, true));

        List<WGeoStop> geoStops = new ArrayList<>();

        List<WSelectFrom> selectFroms = new ArrayList<>();

        List<WTextEdit> textEdits = new ArrayList<>();
        textEdits.add(new WTextEdit(NAME, "Company Name", name, "Company Name"));
        textEdits.add(new WTextEdit(PHONE, "Phone Number", phone, "Phone Number"));
        textEdits.add(new WTextEdit(EMAIL_ADDRESS, "Email Address", email, "name@example.com"));

        List<WTextView> textViews = new ArrayList<>();
        textViews.add(new WTextView(COMPANYID, companyId));

        String description = name;

        WForm wForm = new WForm(null, userId, companyId, fieldIdOrder, checkBoxes, finalizeButtons, geoStops,
                selectFroms, textEdits, textViews, classKey, isSubmitted, description);
        return wForm;    }

    @Override
    public boolean getIsType() {
        return false;
    }
}
