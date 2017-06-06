package com.davidparkeredwards.windrosetools.model.company;

import android.location.Address;

import com.davidparkeredwards.windrosetools.R;
import com.davidparkeredwards.windrosetools.WindroseApplication;
import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;
import com.davidparkeredwards.windrosetools.model.journey.type.JourneyType;
import com.davidparkeredwards.windrosetools.wRecyclerView.WRecyclerConvertibleObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerFinalizeButtons;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObject;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerObjectBundle;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerTextEdit;
import com.davidparkeredwards.windrosetools.wRecyclerView.wRecyclerObjects.WRecyclerTextView;

import java.util.ArrayList;

/**
 * Created by davidedwards on 5/30/17.
 */

public class Company implements WRecyclerConvertibleObject{

    private String companyId;
    private String name;
    private String phone;
    private JourneyType[] journeyTypes;
    private VehicleType[] vehicleTypes;//TripType rules all set here. - Change to JourneyType or ItineraryType? or need multiple classes?
    private String[] employeeIds;
    private String[] vehicleIds;
    private String[] containerIds;
    private String[] preferredLocationIds;
    private String[] companyLocationIds;

    private static final String COMPANY_OBJECT = "company_object";
    private static final String COMPANYID = "company_id";
    private static final String NAME = "company_name";
    private static final String PHONE = "company_phone";
    private static final String COMPANY_FINALIZE_BUTTONS = "company_finalize_buttons";

    //Optional
    private Address address;

    @Override
    public WRecyclerObjectBundle getWRecyclerObjectsEditable() {

        ArrayList<WRecyclerObject> array = new ArrayList<>();
        array.add(new WRecyclerTextView(COMPANYID, companyId));
        array.add(new WRecyclerTextEdit(NAME,
                WindroseApplication.applicationContext.getResources().getString(R.string.company_name).toString(),
                name,
                WindroseApplication.applicationContext.getResources().getString(R.string.company_name_prompt).toString()
                ));
        array.add(new WRecyclerTextEdit(PHONE,
                WindroseApplication.applicationContext.getResources().getString(R.string.company_phone).toString(),
                phone,
                WindroseApplication.applicationContext.getResources().getString(R.string.company_phone_prompt).toString()));
        array.add(new WRecyclerFinalizeButtons(COMPANY_FINALIZE_BUTTONS, true, false, false));

        WRecyclerObjectBundle bundle = new WRecyclerObjectBundle(COMPANY_OBJECT, array);
        return bundle;
    }

    @Override
    public WRecyclerObjectBundle getWRecyclerObjectsViewable() {
        return null;
    }

    @Override
    public void from(WRecyclerObjectBundle bundle) {

    }
}
