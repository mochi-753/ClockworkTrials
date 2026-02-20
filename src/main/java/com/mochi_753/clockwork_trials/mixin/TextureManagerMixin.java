package com.mochi_753.clockwork_trials.mixin;

import com.mochi_753.clockwork_trials.client.data.TimeStopClientData;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (TimeStopClientData.isTimeStopped()) ci.cancel();
    }
}
