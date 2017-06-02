package com.davidparkeredwards.windrosetools.model.journey.charge;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by davidedwards on 6/1/17.
 */

public enum WCurrencyType {

    US(Currency.getInstance(Locale.US));


    private Currency currency;

    private WCurrencyType(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return this.currency;
    }
}
