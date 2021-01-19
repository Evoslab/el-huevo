package com.github.Evoslab.elhuevo.api;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.ArrayList;
import java.util.List;

public class SpawnBiomes {

    private static final List<Identifier> SNOWY_BIOMES = new ArrayList<>();

    static {
        SNOWY_BIOMES.add(BiomeKeys.ICE_SPIKES.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TUNDRA.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA_HILLS.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA_MOUNTAINS.getValue());
    }

    public static List<Identifier> getList() {
        return SNOWY_BIOMES;
    }

    public static void addEntry(Identifier biomeName) {
        if (biomeName != null) {
            for (Identifier id : getList()) {
                if (id.equals(biomeName))
                    return;
            }
            getList().add(biomeName);
        }
    }

    public static boolean isValidBiome(ServerWorld serverWorld, Biome biome) {
        MutableRegistry<Biome> registry = serverWorld.getRegistryManager().get(Registry.BIOME_KEY);
        Identifier biomeKey;

        if (registry.getKey(biome).isPresent()) {
            biomeKey = registry.getKey(biome).get().getValue();
        }
        else {
            return false;
        }

        for (Identifier identifier : SNOWY_BIOMES) {
            if (identifier.equals(biomeKey))
                return true;
        }
        return false;
    }
}
