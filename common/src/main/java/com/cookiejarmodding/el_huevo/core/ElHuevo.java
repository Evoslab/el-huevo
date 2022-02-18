package com.cookiejarmodding.el_huevo.core;

import com.cookiejarmodding.el_huevo.client.render.entity.HuevoRenderer;
import com.cookiejarmodding.el_huevo.client.render.entity.YetiRenderer;
import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.common.entity.Yeti;
import com.cookiejarmodding.el_huevo.core.registry.ElHuevoEntities;
import com.cookiejarmodding.el_huevo.core.registry.ElHuevoItems;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.EntityAttributeRegistry;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ElHuevo {
    public static final String MOD_ID = "el_huevo";
    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(ElHuevo::onClientInit)
            .clientPostInit(ElHuevo::onClientPostInit)
            .commonInit(ElHuevo::onCommonInit)
            .commonPostInit(ElHuevo::onCommonPostInit)
            .dataInit(ElHuevo::onDataInit)
            .build();

    public static void onClientInit() {
        EntityRendererRegistry.register(ElHuevoEntities.HUEVO, HuevoRenderer::new);
        EntityRendererRegistry.register(ElHuevoEntities.YETI, YetiRenderer::new);
    }

    public static void onClientPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onCommonInit() {
        ElHuevoItems.ITEMS.register(PLATFORM);
        ElHuevoEntities.ENTITY_TYPES.register(PLATFORM);

        EntityAttributeRegistry.register(ElHuevoEntities.HUEVO, () -> Huevo.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.MAX_HEALTH, 8.5));
        EntityAttributeRegistry.register(ElHuevoEntities.YETI, Yeti::createAttributes);
    }

    public static void onCommonPostInit(Platform.ModSetupContext ctx) {
    }

    public static void onDataInit(Platform.DataSetupContext ctx) {
    }
}