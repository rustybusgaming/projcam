package com.h1ggsk.projcam;

import com.h1ggsk.projcam.config.ProjcamConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

public class Projcam implements ModInitializer {

    @Override
    public void onInitialize() {
        MidnightConfig.init("projcam", ProjcamConfig.class);
    }
}
