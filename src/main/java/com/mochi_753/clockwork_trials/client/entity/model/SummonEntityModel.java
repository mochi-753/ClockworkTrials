package com.mochi_753.clockwork_trials.client.entity.model;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.SummonEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SummonEntityModel extends GeoModel<SummonEntity> {
    @Override
    public ResourceLocation getModelResource(SummonEntity summonEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "geo/summon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummonEntity summonEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/summon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummonEntity summonEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "animations/summon.animation.json");
    }
}
