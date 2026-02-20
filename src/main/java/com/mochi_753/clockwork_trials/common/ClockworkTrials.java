package com.mochi_753.clockwork_trials.common;

import com.mochi_753.clockwork_trials.common.register.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ClockworkTrials.MOD_ID)
public class ClockworkTrials {
    public static final String MOD_ID = "clockwork_trials";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public ClockworkTrials() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus eventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.COMMON, ClockworkTrialsConfig.SERVER_SPEC, "clockwork_trials/clockwork_trials-common.toml");

        ModEntities.register(eventBus);
        ModItems.register(eventBus);
        ModParticles.register(eventBus);
        ModSoundEvents.register(eventBus);
        ModTabs.register(eventBus);

        ModNetworks.init();
    }
}
