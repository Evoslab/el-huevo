package com.cookiejarmodding.el_huevo.core.mixin.client;

import com.cookiejarmodding.el_huevo.core.ElHuevo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ModelLayers.class)
public class EntityModelLayersMixin {
    @Inject(method = "createLocation", at = @At("HEAD"), cancellable = true)
    private static void createLocation(String path, String model, CallbackInfoReturnable<ModelLayerLocation> cir) {
        ResourceLocation location = ResourceLocation.tryParse(path);
        if (location != null && location.getNamespace().equals(ElHuevo.MOD_ID)) {
            cir.setReturnValue(new ModelLayerLocation(location, model));
        }
    }
}