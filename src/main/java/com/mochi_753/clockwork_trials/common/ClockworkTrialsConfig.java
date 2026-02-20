package com.mochi_753.clockwork_trials.common;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClockworkTrialsConfig {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        SERVER_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class Common {
        public final ForgeConfigSpec.IntValue entityTickSpeedMultiplier;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Clockwork Trials Common side config settings").push("general");

            entityTickSpeedMultiplier = builder
                    .comment("How many times faster should the entity's tick() be?")
                    .defineInRange("entityTickSpeedMultiplier", 10, 1, 10000);
        }
    }
}
