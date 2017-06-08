package com.davidparkeredwards.windrosetools.model.geoTemporal.geoArea;

/**
 * Created by davidedwards on 6/3/17.
 */

public interface GeoArea {
        /*
    Change TimeArea and GeoArea to interfaces so that you can use any number of devices to create them.

    Implement following methods:
    TimeWindow[] getTimeWindows(); - All of these are OffsetDateTimes and give exact windows, even if they are many
    boolean contains(OffsetDateTime); - Returns whether a certain date time is within

    GeoArea:
    Border[] getAreaBorders(); - All of these are lists of lat/long pairs that represent the border of an area
    boolean contains(GeoPoint.centerPoint); //PolyUtil.containsLocation(LatLng point, List polygon, boolean geodesic)

        Border = LatLong[];

    Saving to Firebase - Everything that implements FBSavable will have a DatabaseReference that can be used to save and retrieve
        - boolean saveToFirebase(FBSavable, DBRef); - Includes methods to convert to JSONObject.
        - FBSavable retrieveFromFB(new FBSavable, DBRef); - Includes methods to reconstitute fromRecyclerBundle JSONObject
                - Still try to keep model objects to primitive types as long as it doesn't impose a burden
                - That will help make saving and reconstitution process easier.

     */

    GeoPoint[] getBorders(); //May be null for some types, like postal code
    boolean contains(GeoPoint geoPoint);
}
