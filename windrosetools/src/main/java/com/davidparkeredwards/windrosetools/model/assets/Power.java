package com.davidparkeredwards.windrosetools.model.assets;

import com.davidparkeredwards.windrosetools.model.assets.type.TrailerType;

/**
 * Created by davidedwards on 5/31/17.
 */

public interface Power {

    double getTopSpeed();
    TrailerType[] canPull();
    PowerType getPowerType();


    enum PowerType {
        DRIVING,
        PUBLIC_TRANSIT,
        WALKING,
        BICYCLE,
        AIRPLANE,
        AIR_CAR,
        AIR_DRONE;
    }
}
