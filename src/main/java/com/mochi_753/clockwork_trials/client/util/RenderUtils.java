/*
 * This code is based on and partially modified from code by Sakurafuld.
 * 一部のコードははSakurafuld氏のコードを利用、改変したものです。
 * Copyright (c) 2025 Sakurafuld
 * Licensed under the MIT License.
 */

package com.mochi_753.clockwork_trials.client.util;

import com.mochi_753.clockwork_trials.common.ClockworkTrials;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {
    private RenderUtils() {
    }

    public static void with(PoseStack poseStack, Runnable runnable) {
        poseStack.pushPose();
        runnable.run();
        poseStack.popPose();
    }

    public static void drawCube(Matrix4f matrix, VertexConsumer buffer, float startX, float startY, float startZ, float endX, float endY, float endZ, int color, Predicate<Direction> directionPredicate) {
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        if (directionPredicate.test(Direction.DOWN)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(0, -1, 0).endVertex();
        }

        if (directionPredicate.test(Direction.UP)) {
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(0, 1, 0).endVertex();
        }

        if (directionPredicate.test(Direction.NORTH)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(0, 0, -1).endVertex();
        }

        if (directionPredicate.test(Direction.SOUTH)) {
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
        }

        if (directionPredicate.test(Direction.WEST)) {
            buffer.vertex(matrix, startX, startY, startZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, startY, endZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, endY, endZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
            buffer.vertex(matrix, startX, endY, startZ).color(r, g, b, alpha).normal(-1, 0, 0).endVertex();
        }

        if (directionPredicate.test(Direction.EAST)) {
            buffer.vertex(matrix, endX, startY, startZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, endY, startZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, endY, endZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
            buffer.vertex(matrix, endX, startY, endZ).color(r, g, b, alpha).normal(1, 0, 0).endVertex();
        }
    }

    public static void drawCubeFromAABB(Matrix4f matrix, VertexConsumer buffer, AABB aabb, int color, Predicate<Direction> directionPredicate) {
        drawCube(matrix, buffer, (float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ, color, directionPredicate);
    }

    public static void drawHollowPolygon(Matrix4f matrix, VertexConsumer buffer, int vertices, float radius, float width, int color) {
        if (vertices < 3) throw new IllegalArgumentException("The number of vertices must be at least 3.");

        float step = (float) (2 * Math.PI / vertices);

        int alpha = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        for (int i = 0; i < vertices; i++) {
            float angle0 = step * i;
            float angle1 = step * (i + 1);

            float x0 = Mth.cos(angle0);
            float y0 = Mth.sin(angle0);
            float x1 = Mth.cos(angle1);
            float y1 = Mth.sin(angle1);

            float outerX0 = x0 * radius;
            float outerY0 = y0 * radius;
            float outerX1 = x1 * radius;
            float outerY1 = y1 * radius;

            float innerX0 = x0 * (radius - width);
            float innerY0 = y0 * (radius - width);
            float innerX1 = x1 * (radius - width);
            float innerY1 = y1 * (radius - width);

            buffer.vertex(matrix, outerX0, outerY0, 0).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, outerX1, outerY1, 0).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, innerX1, innerY1, 0).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
            buffer.vertex(matrix, innerX0, innerY0, 0).color(r, g, b, alpha).normal(0, 0, 1).endVertex();
        }
    }

    public static class Type extends RenderType {
        public static final RenderType LIGHTING_NO_CULL = create(ClockworkTrials.MOD_ID + ":lightning_no_cull",
                DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_LINES_SHADER)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
                        .setOutputState(WEATHER_TARGET)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false)
        );

        public static final RenderType NO_CULL = create(ClockworkTrials.MOD_ID + ":lightning_no_cull",
                DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true, CompositeState.builder()
                        .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setOutputState(WEATHER_TARGET)
                        .setCullState(RenderStateShard.NO_CULL)
                        .createCompositeState(false)
        );

        public Type(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
            super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
        }
    }
}
