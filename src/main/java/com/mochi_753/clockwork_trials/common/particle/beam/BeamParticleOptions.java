package com.mochi_753.clockwork_trials.common.particle.beam;

import com.mochi_753.clockwork_trials.common.register.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class BeamParticleOptions implements ParticleOptions {
    public final float r, g, b;

    public BeamParticleOptions(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticles.BEAM.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%f %f %f", r, g, b);
    }
}
