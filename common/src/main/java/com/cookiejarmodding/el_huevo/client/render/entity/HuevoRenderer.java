package com.cookiejarmodding.el_huevo.client.render.entity;

import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * @author StevenPlayzz
 */
@Environment(EnvType.CLIENT)
public class HuevoRenderer extends AnimatedEntityRenderer<Huevo> {
    private static final ResourceLocation[] IDLE_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.idle")};
    private static final ResourceLocation[] WALK_ANIMATION = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.setup"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.walk")};
    private static final ResourceLocation HUEVO_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "huevo");

    private boolean isMoving = true;
    public HuevoRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "huevo"), 0.4F);
    }

    @Override
    public ResourceLocation[] getAnimations(Huevo entity) {
        if (isMoving)
            return WALK_ANIMATION;
        else if (entity.isNoAnimationPlaying())
            return IDLE_ANIMATION;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(Huevo entity) {
        return HUEVO_LOCATION;
    }

    @Override
    public void render(Huevo entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.shouldRiderSit());

        float limbSwingAmount = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            limbSwingAmount = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }
        this.isMoving = !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    //    @Override
//    protected void setupRotations(Huevo entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
//        super.setupRotations(entityLiving, matrixStack, ageInTicks, rotationYaw, partialTicks);
//        if (entityLiving.isTame()) {
//            switch (entityLiving.getClothingColor()) {
//                case RED -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_red.png"));
//                case BLUE -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_blue.png"));
//                case CYAN -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_cyan.png"));
//                case GRAY -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_gray.png"));
//                case LIME -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_lime.png"));
//                case PINK -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_pink.png"));
//                case BLACK -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_black.png"));
//                case BROWN -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_brown.png"));
//                case GREEN -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_green.png"));
//                case ORANGE -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_orange.png"));
//                case PURPLE -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_purple.png"));
//                case YELLOW -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_yellow.png"));
//                case MAGENTA -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_magenta.png"));
//                case LIGHT_BLUE -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_blue.png"));
//                case LIGHT_GRAY -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_gray.png"));
//                default -> this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_white.png"));
//            }
//        }
//        else
//            this.model.setTexture(new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_default.png"));
//    }
}