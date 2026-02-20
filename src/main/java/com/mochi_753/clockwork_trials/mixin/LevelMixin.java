package com.mochi_753.clockwork_trials.mixin;

import com.mochi_753.clockwork_trials.client.data.TimeStopClientData;
import com.mochi_753.clockwork_trials.common.entity.SummonEntity;
import com.mochi_753.clockwork_trials.common.entity.TimekeeperEntity;
import com.mochi_753.clockwork_trials.common.util.TimeStopUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Inject(method = "guardEntityTick", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), cancellable = true)
    private <T extends Entity> void guardEntityTick(Consumer<T> pConsumerEntity, T pEntity, CallbackInfo ci) {
        if ((pEntity instanceof Player player && (player.isCreative() || player.isSpectator())) ||
                pEntity instanceof TimekeeperEntity ||
                pEntity instanceof SummonEntity) return;

        Level level = (Level) (Object) this;
        if (level.isClientSide()) {
            if (DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> TimeStopClientData::isTimeStopped)) ci.cancel();
        } else {
            if (level.getServer() != null) {
                if (TimeStopUtils.isTimeStopped(level.getServer().overworld())) ci.cancel();
            }
        }
    }
}
