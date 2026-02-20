package com.mochi_753.clockwork_trials.common.data;

import com.mochi_753.clockwork_trials.common.network.ClientboundSyncTimeStopPacket;
import com.mochi_753.clockwork_trials.common.register.ModNetworks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class TimeStopSavedData extends SavedData {
    private static final String DATA_NAME = "clockwork_trials_time_data";

    private boolean isTimeStopped = false;

    public TimeStopSavedData() {
    }

    public static TimeStopSavedData get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                TimeStopSavedData::load,
                TimeStopSavedData::new,
                DATA_NAME
        );
    }

    public static TimeStopSavedData load(CompoundTag compoundTag) {
        TimeStopSavedData data = new TimeStopSavedData();
        data.isTimeStopped = compoundTag.getBoolean("isTimeStopped");
        return data;
    }

    @Override
    public @NotNull CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putBoolean("isTimeStopped", isTimeStopped);
        return compoundTag;
    }

    public boolean isTimeStopped() {
        return isTimeStopped;
    }

    public void setTimeStopped(boolean value) {
        if (isTimeStopped == value) return;
        isTimeStopped = value;
        setDirty();

        ModNetworks.CHANNEL.send(PacketDistributor.ALL.noArg(), new ClientboundSyncTimeStopPacket(value, true));
    }
}
