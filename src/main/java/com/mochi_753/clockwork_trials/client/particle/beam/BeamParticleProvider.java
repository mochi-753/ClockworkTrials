package com.mochi_753.clockwork_trials.client.particle.beam;

import com.mochi_753.clockwork_trials.common.particle.beam.BeamParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class BeamParticleProvider implements ParticleProvider<BeamParticleOptions> {
    private final SpriteSet spriteSet;

    public BeamParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public @Nullable Particle createParticle(BeamParticleOptions options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        BeamParticle particle = new BeamParticle(level, x, y, z, spriteSet, options.r(), options.g(), options.b());
        particle.setParticleSpeed(dx, dy, dz);
        return particle;
    }
}
