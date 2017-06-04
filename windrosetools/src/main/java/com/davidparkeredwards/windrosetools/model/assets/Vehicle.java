package com.davidparkeredwards.windrosetools.model.assets;

import com.davidparkeredwards.windrosetools.model.assets.type.ContainerType;
import com.davidparkeredwards.windrosetools.model.assets.type.TrailerType;
import com.davidparkeredwards.windrosetools.model.assets.type.VehicleType;

/**
 * Created by davidedwards on 5/31/17.
 */

public class Vehicle implements Vessel, Power {

    private PowerType powerType;
    private VehicleType vehicleType;
    private String vehicleIdentifer;
    private int personCapacity;
    private double cargoCapacityM2;
    private double cargoCapacityKg;
    private double topSpeedKph; //   km/p/h
    private ContainerType[] containableContainerTypes;
    private TrailerType[] pullableTrailerTypes;

    @Override
    public double getVolumeCapacity() {
        return cargoCapacityM2;
    }

    @Override
    public double getWeightCapacity() {
        return cargoCapacityKg;
    }

    @Override
    public double getPersonCapacity() {
        return personCapacity;
    }

    @Override
    public ContainerType[] canContain() {
        return containableContainerTypes;
    }

    @Override
    public TrailerType[] canPull() {
        return pullableTrailerTypes;
    }

    @Override
    public PowerType getPowerType() {
        return null;
    }

    @Override
    public double getTopSpeed() {
        return topSpeedKph;
    }
}
