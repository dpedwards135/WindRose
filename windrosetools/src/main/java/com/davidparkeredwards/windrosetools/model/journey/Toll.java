package com.davidparkeredwards.windrosetools.model.journey;

/**
 * Created by davidedwards on 6/2/17.
 */

public class Toll {

    private String location;
    private double fee;

    public Toll(String location, double fee) {
        this.location = location;
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }
}
