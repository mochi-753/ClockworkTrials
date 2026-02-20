package com.mochi_753.clockwork_trials.common.register;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.BeamEntity;
import com.mochi_753.clockwork_trials.common.entity.HomingProjectile;
import com.mochi_753.clockwork_trials.common.entity.SummonEntity;
import com.mochi_753.clockwork_trials.common.entity.TimekeeperEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ClockworkTrials.MOD_ID);

    public static final RegistryObject<EntityType<TimekeeperEntity>> TIMEKEEPER_ENTITY = ENTITY_TYPES.register("the_timekeeper",
            () -> EntityType.Builder.of(TimekeeperEntity::new, MobCategory.MONSTER)
                    .sized(1F, 2F)
                    .build(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "the_timekeeper").toString()));
    public static final RegistryObject<EntityType<SummonEntity>> SUMMON_ENTITY = ENTITY_TYPES.register("summon",
            () -> EntityType.Builder.of(SummonEntity::new, MobCategory.MISC)
                    .canSpawnFarFromPlayer()
                    .fireImmune()
                    .noSave()
                    .sized(1F, 1F)
                    .build(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "summon").toString()));
    public static final RegistryObject<EntityType<HomingProjectile>> HOMING_PROJECTILE = ENTITY_TYPES.register("homing_projectile",
            () -> EntityType.Builder.<HomingProjectile>of((HomingProjectile::new), MobCategory.MISC)
                    .canSpawnFarFromPlayer()
                    .fireImmune()
                    .noSummon()
                    .sized(0.5F, 0.5F)
                    .build(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "homing_projectile").toString()));
    public static final RegistryObject<EntityType<BeamEntity>> BEAM_ENTITY = ENTITY_TYPES.register("beam",
            () -> EntityType.Builder.<BeamEntity>of((BeamEntity::new), MobCategory.MISC)
                    .canSpawnFarFromPlayer()
                    .fireImmune()
                    .noSummon()
                    .sized(0.75F, 512F)
                    .build(ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "beam").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
