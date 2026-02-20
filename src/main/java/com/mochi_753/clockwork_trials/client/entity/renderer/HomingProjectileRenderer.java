package com.mochi_753.clockwork_trials.client.entity.renderer;

import com.mochi_753.clockwork_trials.client.util.RenderUtils;
import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mochi_753.clockwork_trials.common.entity.HomingProjectile;
import com.mochi_753.clockwork_trials.common.util.ResourceLocationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HomingProjectileRenderer extends EntityRenderer<HomingProjectile> {
    public HomingProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(HomingProjectile animatable) {
        return ResourceLocationUtils.getResourceLocation(ClockworkTrials.MOD_ID, "textures/entity/homing_projectile.png");
    }

    @Override
    public void render(HomingProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        AABB aabb = entity.getBoundingBox();
        RenderUtils.with(poseStack, () -> {
            double dx = (aabb.minX + aabb.getXsize() / 2) - entity.getX();
            double dy = (aabb.minY + aabb.getYsize() / 2) - entity.getY();
            double dz = (aabb.minZ + aabb.getZsize() / 2) - entity.getZ();
            poseStack.translate(dx, dy, dz);
            poseStack.mulPose(Axis.XP.rotationDegrees((entity.tickCount % 360 + partialTick) * 5));
            poseStack.mulPose(Axis.YP.rotationDegrees((entity.tickCount % 360 + partialTick) * 10));
            poseStack.mulPose(Axis.ZP.rotationDegrees((entity.tickCount % 360 + partialTick) * 8));

            AABB localBox = new AABB(-aabb.getXsize() / 2, -aabb.getYsize() / 2, -aabb.getZsize() / 2, aabb.getXsize() / 2, aabb.getYsize() / 2, aabb.getZsize() / 2);

            RenderUtils.drawCubeFromAABB(poseStack.last().pose(), bufferSource.getBuffer(RenderUtils.Type.NO_CULL), localBox, entity.getColor(), face -> true);
            LevelRenderer.renderLineBox(poseStack, bufferSource.getBuffer(RenderType.lines()), localBox, 0, 0, 0, 0.75F);
        });
    }
}
