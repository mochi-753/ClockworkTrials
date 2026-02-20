package com.mochi_753.clockwork_trials.common.register;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ClockworkTrials.MOD_ID);

    public static final RegistryObject<Item> TIMEKEEPER_SPAWN_EGG = ITEMS.register("the_timekeeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TIMEKEEPER_ENTITY, 0xD57E36, 0x1D0000, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
