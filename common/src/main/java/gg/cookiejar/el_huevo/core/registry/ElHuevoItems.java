package gg.cookiejar.el_huevo.core.registry;

import gg.cookiejar.el_huevo.core.ElHuevo;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ElHuevoItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, ElHuevo.MOD_ID);
}