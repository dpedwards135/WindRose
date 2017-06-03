package com.davidparkeredwards.windrosetools.model.geoTemporal.timeArea;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by davidedwards on 6/3/17.
 */

public interface TimeArea {

    //I think I can actually do away with the TimeWindow array in part if I keep the contain logic solid.
    //TimeWindow[] getTimeWindows(); //- All of these are OffsetDateTimes and give exact windows, even if they are many
    boolean contains(OffsetDateTime offsetDateTime); //- Returns whether a certain date time is within
}
