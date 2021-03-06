package com.davidparkeredwards.windrosetools.model.journey.charge;

import com.davidparkeredwards.windrosetools.model.journey.charge.lineItems.LineItemCharge;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/2/17.
 */

public class ChargeUtils {

    public static final int FLAT_CHARGE = 1;
    public static final int PERCENT_CHARGE = 2;

    private static boolean isValidForDate(OffsetDateTime journeyDate, LineItemCharge charge) {
        if(journeyDate.isAfter(charge.getStartDate())
                && (journeyDate.isBefore(charge.getExpirationDate()))
                    || journeyDate.isEqual(charge.getExpirationDate())) {
            return true;
        } else {
            return false;
        }
    }
}
