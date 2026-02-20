package com.mochi_753.clockwork_trials.common.network;

import com.mochi_753.clockwork_trials.client.data.TimeStopClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundSyncTimeStopPacket(boolean value, boolean playSound) {
    public static void encode(ClientboundSyncTimeStopPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.value());
        buf.writeBoolean(packet.playSound());
    }

    public static ClientboundSyncTimeStopPacket decode(FriendlyByteBuf buf) {
        return new ClientboundSyncTimeStopPacket(buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(ClientboundSyncTimeStopPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TimeStopClientData.setTimeStopped(packet.value(), packet.playSound());
        });
    }
}
