package com.davidparkeredwards.windrosetools.model.geoTemporal.timeArea;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/3/17.
 */

public class TimeWindow {

    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    public enum WDayOfWeek {

        SUNDAY("Sunday"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday");


        private String day;

        private WDayOfWeek(String day) {
            this.day = day;
        }

        public String getDay() {
            return this.day;
        }
    }
}