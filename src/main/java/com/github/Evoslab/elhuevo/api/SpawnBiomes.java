package com.github.Evoslab.elhuevo.api;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

import java.util.ArrayList;
import java.util.List;

public class SpawnBiomes {

    //hello, there is stuff in here that you are supposed to be able to mess around with

    public static final List<Identifier> SNOWY_BIOMES = new ArrayList<>();

    static {
        SNOWY_BIOMES.add(BiomeKeys.ICE_SPIKES.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TUNDRA.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA_HILLS.getValue());
        SNOWY_BIOMES.add(BiomeKeys.SNOWY_TAIGA_MOUNTAINS.getValue());
        // repeat this as many times needed :)
    }

}
