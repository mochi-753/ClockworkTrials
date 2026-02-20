package com.mochi_753.clockwork_trials.common.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtils {
    private ResourceLocationUtils() {
    }

    @SuppressWarnings("removal")
    public static ResourceLocation getResourceLocation(String p1, String p2) {
        return new ResourceLocation(p1, p2);
    }

    @SuppressWarnings("removal")
    public static ResourceLocation getResourceLocation(String p1) {
        return new ResourceLocation(p1);
    }
}
