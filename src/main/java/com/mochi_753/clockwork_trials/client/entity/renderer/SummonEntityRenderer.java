package com.mochi_753.clockwork_trials.client.entity.renderer;

import com.mochi_753.clockwork_trials.client.entity.model.SummonEntityModel;
import com.mochi_753.clockwork_trials.client.util.RenderUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.SummonEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SummonEntityRenderer extends GeoEntityRenderer<SummonEntity> {
    private static final RandomSource RANDOM = RandomSource.create();

    private static final int COLOR_YELLOW = 0xFFFCF16E;
    private static final int COLOR_RED = 0xFFFF0000;
    private static final int COLOR_GREEN = 0xFF00FF00;
    private static final int COLOR_BLUE = 0xFF0000FF;

    public SummonEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SummonEntityModel());
    }

    private static void drawHollowPolygon(PoseStack poseStack, MultiBufferSource bufferSource, int vertices, float radius, float width, int color) {
        RenderUtils.drawHollowPolygon(poseStack.last().pose(), bufferSource.getBuffer(RenderUtils.Type.LIGHTING_NO_CULL), vertices, radius, width, color);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SummonEntity animatable) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/summon.png");
    }

    @Override
    public void render(SummonEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.drawEffects(poseStack, bufferSource, entity, partialTick);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void drawEffects(PoseStack poseStack, MultiBufferSource bufferSource, SummonEntity entity, float partialTicks) {
        final int ticks = entity.tickCount;

        final float riseOffset = Math.min((float) ticks / 40F, 1F);
        final float translateZOffset = (Math.min((float) ticks / 120F * 2F, 2F) - 1) * -2F;
        final float circleScale = Math.min((float) ticks / 40F * 2F, 2F);
        final float rotationDegrees = (float) ticks * 4F;
        final int randomDeg = RANDOM.nextIntBetweenInclusive(1, 360);

        RenderUtils.with(poseStack, () -> {
            poseStack.translate(0, riseOffset, 0);
            poseStack.mulPose(Axis.XP.rotationDegrees(90F));

            RenderUtils.with(poseStack, () -> {
                drawHollowPolygon(poseStack, bufferSource, 64, circleScale, 0.5F, COLOR_YELLOW);
            });

            if (ticks > 40) {
                RenderUtils.with(poseStack, () -> {
                    poseStack.mulPose(Axis.ZP.rotationDegrees(rotationDegrees));
                    drawHollowPolygon(poseStack, bufferSource, 4, 1.5F, 0.25F, COLOR_YELLOW);

                    poseStack.mulPose(Axis.ZP.rotationDegrees(45F));
                    drawHollowPolygon(poseStack, bufferSource, 4, 1.5F, 0.25F, COLOR_YELLOW);
                });
            }

            if (ticks > 80) {
                RenderUtils.with(poseStack, () -> {
                    poseStack.translate(0, 0, translateZOffset);

                    drawHollowPolygon(poseStack, bufferSource, 64, circleScale, 0.5F, COLOR_YELLOW);

                    poseStack.mulPose(Axis.ZP.rotationDegrees(-rotationDegrees));
                    drawHollowPolygon(poseStack, bufferSource, 4, 1.5F, 0.25F, COLOR_YELLOW);

                    poseStack.mulPose(Axis.ZP.rotationDegrees(45F));
                    drawHollowPolygon(poseStack, bufferSource, 4, 1.5F, 0.25F, COLOR_YELLOW);
                });
            }

            if (ticks > 160) {
                RenderUtils.with(poseStack, () -> {
                    poseStack.translate(0, 0, -1);
                    poseStack.mulPose(Axis.XP.rotationDegrees(randomDeg));
                    drawHollowPolygon(poseStack, bufferSource, 5, 0.8F, 0.5F, COLOR_RED);

                    poseStack.mulPose(Axis.YP.rotationDegrees(randomDeg));
                    drawHollowPolygon(poseStack, bufferSource, 5, 0.8F, 0.5F, COLOR_GREEN);

                    poseStack.mulPose(Axis.ZP.rotationDegrees(randomDeg));
                    drawHollowPolygon(poseStack, bufferSource, 5, 0.8F, 0.5F, COLOR_BLUE);
                });
            }
        });
    }
}
