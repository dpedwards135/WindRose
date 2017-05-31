package com.davidparkeredwards.windrosetools.model.tripType;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 5/30/17.
 */

public abstract class JourneyType {
    private String name;

    //How is it routed?
    protected boolean hasOrigin;
    protected boolean hasDestination;
    protected boolean isAdditionalStopEnabled;
    protected boolean isFixedRoute;
    protected boolean isSharedTransport;

    //How is the fare calculated?
    protected int fareType;
    static final int FIXED_FARE = 1;
    static final int PRECALCULATED_FARE = 2;
    static final int METERED_FARE = 3;
    static final int METER_CHECKED_FARE = 4;

    //What is being transported?
    protected boolean isThingsTransport;
    protected boolean isPassengerTransport;

    protected OffsetDateTime

}
