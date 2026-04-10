package com.h1ggsk.projcam.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ProjcamConfig extends MidnightConfig {
    public static final String GENERAL = "general";

    @Entry(category = GENERAL)
    public static boolean followProjectiles = true;
}
