package com.cookiejarmodding.el_huevo.core.api;

import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SpawnBiomes {
    private static final List<ResourceLocation> SNOWY_BIOMES = new ArrayList<>();

    static {
        SNOWY_BIOMES.add(Biomes.ICE_SPIKES.location());
        SNOWY_BIOMES.add(Biomes.SNOWY_PLAINS.location());
        SNOWY_BIOMES.add(Biomes.SNOWY_TAIGA.location());
        SNOWY_BIOMES.add(Biomes.SNOWY_SLOPES.location());
        SNOWY_BIOMES.add(Biomes.SNOWY_BEACH.location());
        SNOWY_BIOMES.add(Biomes.TAIGA.location());
        SNOWY_BIOMES.add(Biomes.FROZEN_PEAKS.location());
        SNOWY_BIOMES.add(Biomes.MEADOW.location());
        SNOWY_BIOMES.add(Biomes.JAGGED_PEAKS.location());
        SNOWY_BIOMES.add(Biomes.OLD_GROWTH_PINE_TAIGA.location());
        SNOWY_BIOMES.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA.location());
    }

    public static List<ResourceLocation> getList() {
        return SNOWY_BIOMES;
    }

    public static void addEntry(ResourceLocation biomeName) {
        if (biomeName != null) {
            for (ResourceLocation id : getList()) {
                if (id.equals(biomeName))
                    return;
            }
            getList().add(biomeName);
        }
    }

    public static boolean isValidBiome(ServerLevel serverWorld, Biome biome) {
        WritableRegistry<Biome> registry = serverWorld.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY);
        ResourceLocation biomeKey;

        if (registry.getResourceKey(biome).isPresent()) {
            biomeKey = registry.getResourceKey(biome).get().location();
        }
        else {
            return false;
        }

        for (ResourceLocation identifier : SNOWY_BIOMES) {
            if (identifier.equals(biomeKey))
                return true;
        }
        return false;
    }
}