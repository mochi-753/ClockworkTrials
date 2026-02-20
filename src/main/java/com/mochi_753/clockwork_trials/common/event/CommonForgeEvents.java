package com.mochi_753.clockwork_trials.common.event;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.command.ClockworkTrialsCommand;
import com.mochi_753.clockwork_trials.common.data.TimeStopSavedData;
import com.mochi_753.clockwork_trials.common.network.ClientboundSyncTimeStopPacket;
import com.mochi_753.clockwork_trials.common.register.ModNetworks;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = ClockworkTrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    @SubscribeEvent
    public static void onRegisterCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal(ClockworkTrials.MOD_ID)
                .then(Commands.literal("time")
                        .requires(cs -> !cs.getLevel().isClientSide())
                        .requires(cs -> cs.hasPermission(3))
                        .then(Commands.literal("stop")
                                .executes(ctx -> ClockworkTrialsCommand.toggleTimeStop(ctx, true))
                        )
                        .then(Commands.literal("restart")
                                .executes(ctx -> ClockworkTrialsCommand.toggleTimeStop(ctx, false))
                        )
                )
                .then(Commands.literal("hurt")
                        .requires(cs -> cs.hasPermission(2))
                        .executes(ClockworkTrialsCommand::hurt)
                )
                .then(Commands.literal("beam")
                        .requires(cs -> cs.hasPermission(2))
                        .executes(ClockworkTrialsCommand::beamIrradiation)
                )
        );
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ServerLevel level = player.server.overworld();
            TimeStopSavedData data = TimeStopSavedData.get(level);

            ModNetworks.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundSyncTimeStopPacket(data.isTimeStopped(), false));
        }
    }
}
