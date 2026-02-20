package com.mochi_753.clockwork_trials.client.particle.beam;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BeamParticle extends TextureSheetParticle {
    public BeamParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, float r, float g, float b) {
        super(level, x, y, z);
        this.setSpriteFromAge(spriteSet);
        this.setColor(r, g, b);
        this.lifetime = 40;
        this.hasPhysics = false;
        this.gravity = 0.0F;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
