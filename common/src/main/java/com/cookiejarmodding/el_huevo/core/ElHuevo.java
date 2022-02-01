package com.cookiejarmodding.el_huevo.core;

import com.cookiejarmodding.el_huevo.client.render.entity.ElHuevoRender;
import com.cookiejarmodding.el_huevo.core.registry.ElHuevoEntities;
import com.cookiejarmodding.el_huevo.core.registry.ElHuevoItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;

public class ElHuevo {
    public static final String MOD_ID = "el_huevo";
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(ElHuevo::onClientInit)
            .clientPostInit(ElHuevo::onClientPostInit)
            .commonInit(ElHuevo::onCommonInit)
            .commonPostInit(ElHuevo::onCommonPostInit)
            .dataInit(ElHuevo::onDataInit)
            .build();

    public static void onClientInit() {}

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
        EntityRendererRegistry.register(ElHuevoEntities.EL_HUEVO, ElHuevoRender::new);
    }

    public static void onCommonInit() {
        GeckoLib.initialize();
        ElHuevoEntities.ENTITY_TYPES.register(PLATFORM);
        ElHuevoItems.ITEMS.register(PLATFORM);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}