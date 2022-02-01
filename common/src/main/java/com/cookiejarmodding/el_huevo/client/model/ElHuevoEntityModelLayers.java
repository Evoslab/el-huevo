package com.cookiejarmodding.el_huevo.client.model;

import com.cookiejarmodding.el_huevo.core.ElHuevo;
import com.cookiejarmodding.el_huevo.core.mixin.client.ModelLayersInvoker;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ElHuevoEntityModelLayers {
    public static final ModelLayerLocation EL_HUEVO = createLocation("el_huevo");

    static {
        new ImmutableMap.Builder<ModelLayerLocation, EntityModelLayerRegistry.TexturedModelDataProvider>()
                .put(ElHuevoEntityModelLayers.EL_HUEVO, ElHuevoEntityModel::getTexturedModelData)
                .build().forEach(EntityModelLayerRegistry::registerModelLayer);
    }

    private static ModelLayerLocation createLocation(String location) {
        return ModelLayersInvoker.invoke_createLocation(new ResourceLocation(ElHuevo.MOD_ID, location).toString(), "main");
    }
}