package com.github.Evoslab.elhuevo.misc;

import com.github.Evoslab.elhuevo.Elhuevo;
import com.github.Evoslab.elhuevo.api.SpawnBiomes;
import com.github.Evoslab.elhuevo.entity.ElHuevoEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.List;
import java.util.Random;

public class ElhuevoSpawner implements Spawner {

    private int ticksUntilNextSpawn;
    private final int newTickTime = 60;

    /*
     * And this is where the magic should happen!
     * As it stands, this is just a slightly changed
     * version of CatSpawner. Tweak as you see fit.
     *
     * -Sarinsa
     */
    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (spawnAnimals && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            --this.ticksUntilNextSpawn;
            if (this.ticksUntilNextSpawn <= 0) {
                this.ticksUntilNextSpawn = this.newTickTime;
                PlayerEntity playerEntity = world.getRandomAlivePlayer();

                if (playerEntity != null) {
                    Random random = world.random;
                    int x = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    int z = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    BlockPos blockPos = playerEntity.getBlockPos().add(x, 0, z);

                    if (world.isRegionLoaded(blockPos.getX() - 10, blockPos.getY() - 10, blockPos.getZ() - 10, blockPos.getX() + 10, blockPos.getY() + 10, blockPos.getZ() + 10)) {
                        if (!SpawnBiomes.SNOWY_BIOMES.contains(BuiltinRegistries.BIOME.getId(world.getBiome(blockPos))))
                            return 0;
                        if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, Elhuevo.EL_HUEVE)) {
                            if (world.isNearOccupiedPointOfInterest(blockPos, 2)) {
                                return this.spawnInHouse(world, blockPos);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private int spawnInHouse(ServerWorld world, BlockPos pos) {
        if (world.getPointOfInterestStorage().count(PointOfInterestType.HOME.getCompletionCondition(), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED) > 4L) {
            List<ElHuevoEntity> list = world.getNonSpectatingEntities(ElHuevoEntity.class, (new Box(pos)).expand(48.0D, 8.0D, 48.0D));
            if (list.size() < 5) {
                return this.spawn(pos, world);
            }
        }
        return 0;
    }

    // here is where we should call itc
    private int spawn(BlockPos pos, ServerWorld world) {
        ElHuevoEntity entity = Elhuevo.EL_HUEVE.create(world);
        if (entity == null) {
            return 0;
        } else {
            entity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
            entity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            world.spawnEntityAndPassengers(entity);
            return 1;
        }
    }
}
