package com.davidparkeredwards.windrosetools.model.assets;

import com.davidparkeredwards.windrosetools.model.assets.type.ContainerType;
import com.davidparkeredwards.windrosetools.model.assets.type.TrailerType;

/**
 * Created by davidedwards on 5/31/17.
 */

public class Container implements Vessel {

    private ContainerType containerType;
    private double cargoCapacityM2;
    private double cargoCapacityKg;
    private int personCapacity;
    private ContainerType[] containableContainerTypes; //Containers cannot pull trailers

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
        return new TrailerType[0]; //Containers cannot pull trailers
    }
}
