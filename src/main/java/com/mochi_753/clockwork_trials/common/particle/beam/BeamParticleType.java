package com.mochi_753.clockwork_trials.common.particle.beam;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class BeamParticleType extends ParticleType<BeamParticleOptions> {
    private static final Codec<BeamParticleOptions> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("r").forGetter(o -> o.r()),
                    Codec.FLOAT.fieldOf("g").forGetter(o -> o.g()),
                    Codec.FLOAT.fieldOf("b").forGetter(o -> o.b())
            ).apply(instance, BeamParticleOptions::new)
    );

    @SuppressWarnings("deprecation")
    private static final ParticleOptions.Deserializer<BeamParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public BeamParticleOptions fromCommand(ParticleType<BeamParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            return new BeamParticleOptions(r, g, b);
        }

        @Override
        public BeamParticleOptions fromNetwork(ParticleType<BeamParticleOptions> type, FriendlyByteBuf buf) {
            return new BeamParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
    };

    public BeamParticleType() {
        super(false, DESERIALIZER);
    }

    @Override
    public @NotNull Codec<BeamParticleOptions> codec() {
        return CODEC;
    }
}
