package com.cookiejarmodding.el_huevo.client.render.entity;

import com.cookiejarmodding.el_huevo.client.model.HuevoModel;
import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class HuevoRenderer extends GeoEntityRenderer<Huevo> {
    public HuevoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HuevoModel());
        this.shadowRadius = 0.4F;
    }
}