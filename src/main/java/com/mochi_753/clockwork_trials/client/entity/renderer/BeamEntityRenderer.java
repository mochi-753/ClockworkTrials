package com.mochi_753.clockwork_trials.client.entity.renderer;

import com.mochi_753.clockwork_trials.client.util.RenderUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.BeamEntity;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class BeamEntityRenderer extends EntityRenderer<BeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/beam.png");
    private static final int COLOR_YELLOW = 0xFFFCF16E;

    public BeamEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(BeamEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        AABB aabb = pEntity.getBoundingBox();
        AABB localAABB = aabb.move(-pEntity.getX(), -pEntity.getY(), -pEntity.getZ());
        int beamColor = calculateColor(pEntity.getColor(), pEntity.tickCount);
        float beamAlpha = ((beamColor >> 24) & 0xFF) / 255F;
        float magicCircleScale = 1.1F + Mth.sin((float) pEntity.tickCount * 0.1F) * 0.1F;

        RenderUtils.with(pPoseStack, () -> {
            RenderUtils.with(pPoseStack, () -> {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
                RenderUtils.drawHollowPolygon(pPoseStack.last().pose(), pBuffer.getBuffer(RenderUtils.Type.LIGHTING_NO_CULL), 64, magicCircleScale, 0.25F, COLOR_YELLOW);
                pPoseStack.mulPose(Axis.ZP.rotationDegrees((float) pEntity.tickCount * 9F));
                RenderUtils.drawHollowPolygon(pPoseStack.last().pose(), pBuffer.getBuffer(RenderUtils.Type.LIGHTING_NO_CULL), 3, magicCircleScale - 0.1F, 0.35F, COLOR_YELLOW);
            });

            if (pEntity.tickCount > 80 && pEntity.tickCount < 180) {
                RenderUtils.with(pPoseStack, () -> {
                    pPoseStack.mulPose(Axis.YP.rotationDegrees((float) pEntity.tickCount * 5F));
                    RenderUtils.with(pPoseStack, () -> {
                        LevelRenderer.renderLineBox(pPoseStack, pBuffer.getBuffer(RenderType.lines()), localAABB, 1, 1, 1, beamAlpha);
                        RenderUtils.drawCubeFromAABB(pPoseStack.last().pose(), pBuffer.getBuffer(RenderUtils.Type.NO_CULL), localAABB, beamColor, face -> true);
                    });
                    RenderUtils.with(pPoseStack, () -> {
                        pPoseStack.mulPose(Axis.YP.rotationDegrees(45));
                        LevelRenderer.renderLineBox(pPoseStack, pBuffer.getBuffer(RenderType.lines()), localAABB, 1, 1, 1, beamAlpha);
                        RenderUtils.drawCubeFromAABB(pPoseStack.last().pose(), pBuffer.getBuffer(RenderUtils.Type.NO_CULL), localAABB, beamColor, face -> true);
                    });
                });
            }
        });
    }

    private int calculateColor(int color, int tickCount) {
        int alpha;
        if (tickCount >= 80 && tickCount <= 85) {
            alpha = (int) ((tickCount - 80) * 180.0 / 5.0);
        } else if (tickCount >= 175 && tickCount <= 180) {
            alpha = 180 - (int) ((tickCount - 175) * 180.0 / 5.0);
        } else {
            alpha = 180;
        }

        return (alpha << 24) | (color & 0x00FFFFFF);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BeamEntity beamEntity) {
        return TEXTURE;
    }
}
