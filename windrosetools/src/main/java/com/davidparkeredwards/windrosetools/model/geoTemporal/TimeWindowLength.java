package com.davidparkeredwards.windrosetools.model.geoTemporal;

/**
 * Created by davidedwards on 5/31/17.
 */



public enum TimeWindowLength {

    NEVER(0),
    ALWAYS(-1),
    ONE_MIN(1),
    TEN_MINS(10),
    FIFTEEN_MINS(15),
    THIRTY_MINS(30),
    ONE_HOUR(60),
    TWO_HOURS(120),
    FOUR_HOURS(240),
    FIVE_HOURS(300),
    TEN_HOURS(600),
    ONE_DAY(1440),
    ONE_WEEK(10080),
    TWO_WEEK(20160);

    private int duration;

    private TimeWindowLength(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }
}