package com.mochi_753.clockwork_trials.mixin;

import com.mochi_753.clockwork_trials.client.data.TimeStopClientData;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Shadow
    protected abstract void tickTime();

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    public void tick(BooleanSupplier pHasTimeLeft, CallbackInfo ci) {
        if (TimeStopClientData.isTimeStopped()) ci.cancel();
    }

    @Inject(method = "animateTick", at = @At(value = "HEAD"), cancellable = true)
    private void animateTick(int pPosX, int pPosY, int pPosZ, CallbackInfo ci) {
        if (TimeStopClientData.isTimeStopped()) ci.cancel();
    }
}
