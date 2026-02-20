package com.mochi_753.clockwork_trials.common.util;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.data.TimeStopSavedData;
import net.minecraft.server.level.ServerLevel;

public class TimeStopUtils {
    public static boolean isTimeStopped = false;

    public static boolean isTimeStopped(ServerLevel level) {
        TimeStopSavedData data = TimeStopSavedData.get(level);
        return data.isTimeStopped();
    }

    public static void stop(ServerLevel level) {
        ClockworkTrials.LOGGER.info("Time stop has begun.");
        TimeStopSavedData data = TimeStopSavedData.get(level);
        data.setTimeStopped(true);
    }

    public static void restart(ServerLevel level) {
        ClockworkTrials.LOGGER.info("Time stop has ended.");
        TimeStopSavedData data = TimeStopSavedData.get(level);
        data.setTimeStopped(false);
    }
}
