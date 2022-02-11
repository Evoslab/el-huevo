package com.cookiejarmodding.el_huevo.client.render.entity;

import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * @author StevenPlayzz
 */
@Environment(EnvType.CLIENT)
public class HuevoRenderer extends AnimatedEntityRenderer<Huevo> {
    private static final ResourceLocation[] DEFAULT_ANIMATIONS = new ResourceLocation[]{new ResourceLocation(ElHuevo.MOD_ID, "huevo.walk"), new ResourceLocation(ElHuevo.MOD_ID, "huevo.idle")};
    private static final ResourceLocation HUEVO_LOCATION = new ResourceLocation(ElHuevo.MOD_ID, "huevo");

    public HuevoRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(ElHuevo.MOD_ID, "huevo"), 0.4F);
    }

    @Override
    public ResourceLocation[] getAnimations(Huevo entity) {
        if (entity.isNoAnimationPlaying())
            return DEFAULT_ANIMATIONS;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(Huevo yeti) {
        return HUEVO_LOCATION;
    }

    //    @Override
//    public ResourceLocation getTextureTableLocation(Huevo entity) {
//        if (entity.isTame())
//            return switch (entity.getClothingColor()) {
//                case RED -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_red.png");
//                case BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_blue.png");
//                case CYAN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_cyan.png");
//                case GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_gray.png");
//                case LIME -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_lime.png");
//                case PINK -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_pink.png");
//                case BLACK -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_black.png");
//                case BROWN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_brown.png");
//                case GREEN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_green.png");
//                case ORANGE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_orange.png");
//                case PURPLE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_purple.png");
//                case YELLOW -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_yellow.png");
//                case MAGENTA -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_magenta.png");
//                case LIGHT_BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_blue.png");
//                case LIGHT_GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_gray.png");
//                default -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_white.png");
//            };
//        else
//            return new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_default.png");
//    }
}