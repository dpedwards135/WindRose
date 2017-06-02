package com.davidparkeredwards.windrosetools.model.geoTemporal;

import org.threeten.bp.OffsetTime;

/**
 * Created by davidedwards on 5/31/17.
 */

public class TimeArea {
    public static final int ANYTIME = -1;

    private TimeWindow[] includedTimes;
    private WLocalDate[] includedDates;
    private WDayOfWeek[] includedDaysOfWeek;



    /*
    From OffsetTime to OffsetTime - 00:00 to 23:59, or 12:45 to 12:45
    On OffsetDate[], OffsetDay[] - These days are ALL included
    Appointments and Windows - the appt is at the beginning of the window. Requires a client note that
    their responsibility is to be ready at the beginning of the window.
     */

    private class TimeWindow {

        private OffsetTime startTime;
        private OffsetTime endTime;

        //get and set from OffsetTime

    }

    private class WLocalDate {
        private String localDate;

        //get and set from LocalDate
    }

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


