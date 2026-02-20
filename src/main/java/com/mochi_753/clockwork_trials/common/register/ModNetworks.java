package com.mochi_753.clockwork_trials.common.register;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.network.ClientboundSyncTimeStopPacket;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworks {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;
        CHANNEL.messageBuilder(ClientboundSyncTimeStopPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientboundSyncTimeStopPacket::encode)
                .decoder(ClientboundSyncTimeStopPacket::decode)
                .consumerMainThread(ClientboundSyncTimeStopPacket::handle)
                .add();
    }
}
