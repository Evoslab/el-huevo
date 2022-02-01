package com.cookiejarmodding.el_huevo.core.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(ModelLayers.class)
public interface ModelLayersInvoker {
    @Invoker("createLocation")
    static ModelLayerLocation invoke_createLocation(String path, String model) {
        throw new AssertionError();
    }
}