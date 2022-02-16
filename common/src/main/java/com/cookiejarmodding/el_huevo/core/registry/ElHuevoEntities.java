package com.cookiejarmodding.el_huevo.core.registry;

import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.api.item.SpawnEggItemBase;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ElHuevoEntities {
    public static final PollinatedRegistry<EntityType<?>> ENTITY_TYPES = PollinatedRegistry.create(Registry.ENTITY_TYPE, ElHuevo.MOD_ID);

    public static final Supplier<EntityType<Huevo>> HUEVO = register("huevo", EntityType.Builder.of(Huevo::new, MobCategory.MISC).sized(0.75F, 0.75F).clientTrackingRange(16), 0xFFFFF5, 0x1D2635);

    /**
     * Registers a new entity with an egg under the specified id.
     *
     * @param id             The id of the entity
     * @param builder        The entity builder
     * @param primaryColor   The egg color of the egg item
     * @param secondaryColor The spot color for the egg item
     * @param <T>            The type of entity being created
     */
    private static <T extends Mob> Supplier<EntityType<T>> register(String id, EntityType.Builder<T> builder, int primaryColor, int secondaryColor) {
        Supplier<EntityType<T>> object = register(id, builder);
        ElHuevoItems.ITEMS.register(id + "_spawn_egg", () -> new SpawnEggItemBase<>(object, primaryColor, secondaryColor, true, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return object;
    }

    /**
     * Registers a new entity under the specified id.
     *
     * @param id      The id of the entity
     * @param builder The entity builder
     * @param <T>     The type of entity being created
     */
    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(id, () -> builder.build(id));
    }
}