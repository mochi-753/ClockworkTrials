package com.mochi_753.clockwork_trials.common.command;

import com.mochi_753.clockwork_trials.common.entity.BeamEntity;
import com.mochi_753.clockwork_trials.common.util.TimeStopUtils;
import com.mochi_753.clockwork_trials.common.world.ParadoxDamageSource;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ClockworkTrialsCommand {
    public static int toggleTimeStop(CommandContext<CommandSourceStack> context, boolean enabled) {
        if (context.getSource().getLevel().isClientSide()) {
            context.getSource().sendFailure(Component.literal("This command can only be run on the server."));
            return 0;
        }

        ServerLevel level = context.getSource().getServer().overworld();
        if (enabled) {
            TimeStopUtils.stop(level);
        } else {
            TimeStopUtils.restart(level);
        }

        return 1;
    }

    public static int hurt(CommandContext<CommandSourceStack> context) {
        Entity entity = context.getSource().getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(new ParadoxDamageSource(livingEntity, livingEntity), Float.MAX_VALUE);
        }

        return 1;
    }

    public static int beamIrradiation(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof LivingEntity living) {
            BeamEntity beam = new BeamEntity(context.getSource().getLevel(), living);
            beam.setPos(living.position());
            context.getSource().getLevel().addFreshEntity(beam);
        }

        return 1;
    }
}
