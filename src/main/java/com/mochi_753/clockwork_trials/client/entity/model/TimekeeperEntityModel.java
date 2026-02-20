package com.mochi_753.clockwork_trials.client.entity.model;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.TimekeeperEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

@OnlyIn(Dist.CLIENT)
public class TimekeeperEntityModel extends GeoModel<TimekeeperEntity> {
    @Override
    public ResourceLocation getModelResource(TimekeeperEntity timekeeperEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "geo/the_timekeeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TimekeeperEntity timekeeperEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/the_timekeeper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TimekeeperEntity timekeeperEntity) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "animations/the_timekeeper.animation.json");
    }

    @Override
    public void setCustomAnimations(TimekeeperEntity animatable, long instanceId, AnimationState<TimekeeperEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityModelData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityModelData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}
