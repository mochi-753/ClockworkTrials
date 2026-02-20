package com.mochi_753.clockwork_trials.client.entity.sound;

import com.mochi_753.clockwork_trials.common.register.ModSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TimeKeeperSound extends AbstractTickableSoundInstance {
    private final Entity entity;

    public TimeKeeperSound(Entity entity) {
        super(ModSoundEvents.BGM.get(), SoundSource.MUSIC, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.5F;
        this.pitch = 1.0F;
        this.relative = false;
        updatePosition();
    }

    public void tick() {
        if (this.entity == null || !entity.isAlive()) {
            this.stop();
            return;
        }
        updatePosition();
    }

    private void updatePosition() {
        Vec3 pos = entity.position();
        this.x = (float) pos.x;
        this.y = (float) pos.y;
        this.z = (float) pos.z;
    }
}
