package com.davidparkeredwards.windrosetools.model.assets;

import com.davidparkeredwards.windrosetools.model.assets.type.ContainerType;
import com.davidparkeredwards.windrosetools.model.assets.type.TrailerType;

/**
 * Created by davidedwards on 5/31/17.
 */

public interface Vessel {

    double getVolumeCapacity();
    double getWeightCapacity();
    double getPersonCapacity();
    ContainerType[] canContain();
    TrailerType[] canPull();

}
