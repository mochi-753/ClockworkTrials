package com.mochi_753.clockwork_trials.common.register;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ClockworkTrials.MOD_ID);

    public static final RegistryObject<SoundEvent> BELL_RINGS = SOUND_EVENTS.register("bell_rings",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "bell_rings")));
    public static final RegistryObject<SoundEvent> TICKTOCK = SOUND_EVENTS.register("ticktock",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "ticktock")));
    public static final RegistryObject<SoundEvent> TIME_STOP = SOUND_EVENTS.register("time_stop",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "time_stop")));
    public static final RegistryObject<SoundEvent> BEAM01 = SOUND_EVENTS.register("beam01",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "beam01")));
    public static final RegistryObject<SoundEvent> BEAM02 = SOUND_EVENTS.register("beam02",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "beam02")));
    public static final RegistryObject<SoundEvent> BGM = SOUND_EVENTS.register("bgm",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "bgm")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
