package com.mochi_753.clockwork_trials.mixin;

import com.mochi_753.clockwork_trials.common.util.TimeStopUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "tickBlock", at = @At(value = "HEAD"), cancellable = true)
    private void tickBlock(BlockPos blockPos, Block block, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (TimeStopUtils.isTimeStopped(level)) ci.cancel();
    }

    @Inject(method = "tickChunk", at = @At(value = "HEAD"), cancellable = true)
    private void tickChunk(LevelChunk pChunk, int pRandomTickSpeed, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (TimeStopUtils.isTimeStopped(level)) ci.cancel();
    }

    @Inject(method = "tickFluid", at = @At(value = "HEAD"), cancellable = true)
    private void tickFluid(BlockPos blockPos, Fluid fluid, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (TimeStopUtils.isTimeStopped(level)) ci.cancel();
    }

    @Inject(method = "tickTime", at = @At(value = "HEAD"), cancellable = true)
    private void tickTime(CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (TimeStopUtils.isTimeStopped(level)) ci.cancel();
    }
}
