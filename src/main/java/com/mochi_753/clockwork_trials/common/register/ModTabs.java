package com.mochi_753.clockwork_trials.common.register;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ClockworkTrials.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CLOCKWORK_TRIALS_TAB = TABS.register("clockwork_trials_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.clockwork_trials.clockwork_trials_tab"))
                    .icon(Items.CLOCK::getDefaultInstance)
                    .displayItems((pParam, pOutput) -> {
                        pOutput.accept(ModItems.TIMEKEEPER_SPAWN_EGG.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
