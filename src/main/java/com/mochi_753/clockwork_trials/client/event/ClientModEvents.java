package com.mochi_753.clockwork_trials.client.event;

import com.mochi_753.clockwork_trials.client.entity.renderer.BeamEntityRenderer;
import com.mochi_753.clockwork_trials.client.entity.renderer.HomingProjectileRenderer;
import com.mochi_753.clockwork_trials.client.entity.renderer.SummonEntityRenderer;
import com.mochi_753.clockwork_trials.client.entity.renderer.TimekeeperEntityRenderer;
import com.mochi_753.clockwork_trials.client.particle.beam.BeamParticleProvider;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.register.ModEntities;
import com.mochi_753.clockwork_trials.common.register.ModParticles;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ClockworkTrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.TIMEKEEPER_ENTITY.get(), TimekeeperEntityRenderer::new);
        EntityRenderers.register(ModEntities.SUMMON_ENTITY.get(), SummonEntityRenderer::new);
        EntityRenderers.register(ModEntities.HOMING_PROJECTILE.get(), HomingProjectileRenderer::new);
        EntityRenderers.register(ModEntities.BEAM_ENTITY.get(), BeamEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BEAM.get(), BeamParticleProvider::new);
    }
}
