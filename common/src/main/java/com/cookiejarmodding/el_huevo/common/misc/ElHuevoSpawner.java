package com.cookiejarmodding.el_huevo.common.misc;

import com.cookiejarmodding.el_huevo.api.biome.SpawnBiomes;
import com.cookiejarmodding.el_huevo.common.entity.Huevo;
import com.cookiejarmodding.el_huevo.core.registry.ElHuevoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

/**
 * @author Sarinsa, StevenPlayzz
 */
public class ElHuevoSpawner implements CustomSpawner {
    private int ticksUntilNextSpawn;

    @Override
    @SuppressWarnings("deprecation")
    public int tick(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies) {
        if (spawnFriendlies && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            --this.ticksUntilNextSpawn;
            if (this.ticksUntilNextSpawn <= 0) {
                this.ticksUntilNextSpawn = 600;
                Player player = level.getRandomPlayer();

                if (player != null) {
                    Random random = level.random;
                    int x = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    int z = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    BlockPos pos = player.getOnPos().offset(x, 0, z);

                    if (level.hasChunksAt(pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10, pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10)) {

                        if (!SpawnBiomes.isValidBiome(level, level.getBiome(pos)))
                            return 0;

                        if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, level, pos, ElHuevoEntities.HUEVO.get())) {
                            if (level.isCloseToVillage(pos, 2)) {
                                return this.spawnInHouse(level, pos);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private int spawnInHouse(ServerLevel level, BlockPos pos) {
        if (level.getPoiManager().getCountInRange(PoiType.HOME.getPredicate(), pos, 48, PoiManager.Occupancy.IS_OCCUPIED) > 4L) {
            List<Huevo> list = level.getEntitiesOfClass(Huevo.class, (new AABB(pos)).inflate(48.0D, 8.0D, 48.0D));
            if (list.size() < 5) {
                return this.spawn(pos, level);
            }
        }
        return 0;
    }

    private int spawn(BlockPos pos, ServerLevel level) {
        Huevo entity = ElHuevoEntities.HUEVO.get().create(level);
        if (entity == null) {
            return 0;
        } else {
            entity.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null, null);
            entity.moveTo(pos, 0.0F, 0.0F);
            level.addFreshEntityWithPassengers(entity);
            return 1;
        }
    }
}