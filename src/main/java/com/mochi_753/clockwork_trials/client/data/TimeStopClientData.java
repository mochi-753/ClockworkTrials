package com.mochi_753.clockwork_trials.client.data;

import com.mochi_753.clockwork_trials.client.util.SoundUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TimeStopClientData {
    private static boolean isTimeStopped = false;

    public static boolean isTimeStopped() {
        return isTimeStopped;
    }

    public static void setTimeStopped(boolean value, boolean playSound) {
        if (isTimeStopped == value) return;
        isTimeStopped = value;
        onTimeStopChanged(value, playSound);
    }

    private static void onTimeStopChanged(boolean value, boolean playSound) {
        ClockworkTrials.LOGGER.info("Time stop state has been synchronized to the client.");
        Minecraft minecraft = Minecraft.getInstance();

        if (playSound) {
            SoundUtils.playTimeStopSound();
        }

        if (value) {
            // 時間停止が開始した時の処理
        } else {
            minecraft.gameRenderer.shutdownEffect();
        }
    }
}
