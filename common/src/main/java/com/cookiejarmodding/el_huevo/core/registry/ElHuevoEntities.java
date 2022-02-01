package com.cookiejarmodding.el_huevo.core.registry;

import com.cookiejarmodding.el_huevo.common.entity.ElHuevoEntity;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ElHuevoEntities {
    public static final PollinatedRegistry<EntityType<?>> ENTITY_TYPES = PollinatedRegistry.create(Registry.ENTITY_TYPE, ElHuevo.MOD_ID);

    public static final Supplier<EntityType<ElHuevoEntity>> EL_HUEVO = ENTITY_TYPES.register(
            "el_huevo", () -> EntityType.Builder
                    .of(ElHuevoEntity::new, MobCategory.MISC)
                    .sized(0.75f, 0.75f)
                    .clientTrackingRange(8)
                    .build("el_huevo")
    );
}