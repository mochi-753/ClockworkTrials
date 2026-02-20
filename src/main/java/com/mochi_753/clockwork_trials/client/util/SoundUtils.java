package com.mochi_753.clockwork_trials.client.util;

import com.mochi_753.clockwork_trials.client.entity.sound.TimeKeeperSound;
import com.mochi_753.clockwork_trials.common.entity.TimekeeperEntity;
import com.mochi_753.clockwork_trials.common.register.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundUtils {
    public static void playBGM(TimekeeperEntity source) {
        Minecraft minecraft = Minecraft.getInstance();
        if (source != null && minecraft.level != null && minecraft.level.dimension().equals(source.level().dimension())) {
            minecraft.getSoundManager().play(new TimeKeeperSound(source));
        }
    }

    public static void playTimeStopSound() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(ModSoundEvents.TIME_STOP.get(), 1.0F, 0.5F));
        }
    }
}
