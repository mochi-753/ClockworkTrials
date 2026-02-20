package com.mochi_753.clockwork_trials.client.entity.renderer;

import com.mochi_753.clockwork_trials.client.entity.model.TimekeeperEntityModel;
import com.mochi_753.clockwork_trials.client.util.RenderUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.TimekeeperEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class TimekeeperEntityRenderer extends GeoEntityRenderer<TimekeeperEntity> {
    public TimekeeperEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TimekeeperEntityModel());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TimekeeperEntity animatable) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/the_timekeeper.png");
    }

    @Override
    public void render(TimekeeperEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source, int packedLight) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
        this.drawAura(poseStack, source, entity, partialTicks);
        super.render(entity, entityYaw, partialTicks, poseStack, source, packedLight);
    }

    private void drawAura(PoseStack poseStack, MultiBufferSource source, TimekeeperEntity entity, float partialTicks) {
        float xRot = Mth.cos(entity.tickCount / 6F) * 6F;

        RenderUtils.with(poseStack, () -> {
            poseStack.translate(0, entity.getBbHeight() / 2, 0);
            poseStack.scale(1, 1, 1);

            RenderUtils.with(poseStack, () -> {
                poseStack.mulPose(Axis.XP.rotationDegrees(120 + xRot));
                poseStack.mulPose(Axis.XP.rotationDegrees(Mth.rotLerp(partialTicks, ((entity.tickCount - 1) % 360F) * 3F, (entity.tickCount % 360F) * 3F)));

                RenderUtils.drawHollowPolygon(poseStack.last().pose(), source.getBuffer(RenderUtils.Type.LIGHTING_NO_CULL), 3, 3, 0.5F, 0xAFFFEF6C);
            });

            RenderUtils.with(poseStack, () -> {
                poseStack.mulPose(Axis.XP.rotationDegrees(70 - xRot));
                poseStack.mulPose(Axis.ZN.rotationDegrees(Mth.rotLerp(partialTicks, ((entity.tickCount - 1) % 360f) * 3f, (entity.tickCount % 360f) * 3f)));

                RenderUtils.drawHollowPolygon(poseStack.last().pose(), source.getBuffer(RenderUtils.Type.LIGHTING_NO_CULL), 5, 4, 0.5F, 0xAFFCF16E);
            });
        });
    }
}
