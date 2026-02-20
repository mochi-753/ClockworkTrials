package com.mochi_753.clockwork_trials.client.event;

import com.mochi_753.clockwork_trials.client.data.TimeStopClientData;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ClockworkTrials.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
    private static final ResourceLocation GRAYSCALE_EFFECT = ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "shaders/post/grayscale.json");

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END && TimeStopClientData.isTimeStopped()) {
            Minecraft.getInstance().gameRenderer.loadEffect(GRAYSCALE_EFFECT);
        }
    }
}
