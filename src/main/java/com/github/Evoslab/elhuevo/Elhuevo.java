package com.github.Evoslab.elhuevo;

import com.github.Evoslab.elhuevo.entity.ElHuevoEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeKeys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Elhuevo implements ModInitializer {

	public static final String MODID = "elhuevo";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static final EntityType<ElHuevoEntity> EL_HUEVE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("elhuevo", "elhueve"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ElHuevoEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

	@Override
	public void onInitialize() {
		BiomeModifications.addSpawn(
				BiomeSelectors.includeByKey(BiomeKeys.ICE_SPIKES, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_HILLS, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TAIGA_MOUNTAINS),
				SpawnGroup.CREATURE,
				Elhuevo.EL_HUEVE,
				5 /* weight */,
				3 /* minGroupSize */,
				4 /* maxGroupSize */
		);
		LOGGER.debug(BiomeKeys.SAVANNA.getValue());
		FabricDefaultAttributeRegistry.register(EL_HUEVE, ElHuevoEntity.createMobAttributes());
		Registry.register(Registry.ITEM, new Identifier("elhuevo", "elhueve_spawn_egg"), new SpawnEggItem(EL_HUEVE, 0xFFFFF5, 0x1D2635, new Item.Settings().group(ItemGroup.MISC)));
		//Registry.register(Registry.STATUS_EFFECT, new Identifier("elhuevo", "egg_charge"), EGGCHARGE);

	}
	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}