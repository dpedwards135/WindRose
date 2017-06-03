package com.davidparkeredwards.windrosetools.model.journey.type;

/**
 * Created by davidedwards on 6/3/17.
 */

public class UnitRule {

    /*
    Unit rules are properties of UnitTypes and are used to form and validate new units
    As a manager I want to be able to set custom rules about what types of units are allowed on
    particular journeytypes. These will be checked when someone sets up an order by simply asking a series of
    true false questions to make sure their unit conforms. All are boolean.
     */

    private String[] property;
    private boolean acceptedValue;

    public boolean unitIsValid(boolean unitActualValue) {
        if(unitActualValue == acceptedValue) {
            return true;
        } else {
            return false;
        }

    }
}
