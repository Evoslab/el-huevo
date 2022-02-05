package com.cookiejarmodding.el_huevo.client.model;

import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class HuevoModel extends AnimatedGeoModel<Huevo> {
    @Override
    public ResourceLocation getModelLocation(Huevo huevo) {
        return new ResourceLocation(ElHuevo.MOD_ID, "geo/entity/huevo.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Huevo huevo) {
        if (huevo.isTame())
            return switch (huevo.getClothingColor()) {
                case RED -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_red.png");
                case BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_blue.png");
                case CYAN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_cyan.png");
                case GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_gray.png");
                case LIME -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_lime.png");
                case PINK -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_pink.png");
                case BLACK -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_black.png");
                case BROWN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_brown.png");
                case GREEN -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_green.png");
                case ORANGE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_orange.png");
                case PURPLE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_purple.png");
                case YELLOW -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_yellow.png");
                case MAGENTA -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_magenta.png");
                case LIGHT_BLUE -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_blue.png");
                case LIGHT_GRAY -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_light_gray.png");
                default -> new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_white.png");
            };
        else
            return new ResourceLocation(ElHuevo.MOD_ID, "textures/entity/huevo/huevo_default.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Huevo huevo) {
        return new ResourceLocation(ElHuevo.MOD_ID, "animations/entity/huevo.animation.json");
    }
}