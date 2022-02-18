package com.cookiejarmodding.el_huevo.client.render.entity;

import com.cookiejarmodding.el_huevo.common.entity.Yeti;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ocelot
 */
public class YetiRenderer extends AnimatedEntityRenderer<Yeti>
{
    private static final ResourceLocation[] DEFAULT_ANIMATIONS = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "yeti.setup"), new ResourceLocation(ElHuevo.MOD_ID, "yeti.swing_arms")};
    private static final ResourceLocation YETI_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "yeti");

    public YetiRenderer(EntityRendererProvider.Context context)
    {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "yeti"), 0.5F);
    }

    @Override
    protected void setupRotations(Yeti entity, PoseStack matrixStack, float ticksExisted, float rotY, float partialTicks)
    {
        super.setupRotations(entity, matrixStack, ticksExisted, rotY, partialTicks);
        if (!((double) entity.animationSpeed < 0.01))
        {
            float j = entity.animationPosition - entity.animationSpeed * (1.0F - partialTicks) + 6.0F;
            float k = (Math.abs(j % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * k));
        }
    }

    @Override
    public ResourceLocation[] getAnimations(Yeti entity)
    {
        if (entity.isNoAnimationPlaying())
            return DEFAULT_ANIMATIONS;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(Yeti yeti)
    {
        return YETI_LOCATION;
    }
}