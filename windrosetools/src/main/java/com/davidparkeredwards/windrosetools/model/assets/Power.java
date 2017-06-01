package com.davidparkeredwards.windrosetools.model.assets;

import com.davidparkeredwards.windrosetools.model.assets.type.TrailerType;

/**
 * Created by davidedwards on 5/31/17.
 */

public interface Power {

    double getTopSpeed();
    TrailerType[] canPull();
}
