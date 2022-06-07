package com.waterpicker.biomeskylighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class BiomeLightingSkyEngine extends SkyLightEngine {
    public BiomeLightingSkyEngine(LightChunkGetter chunkProvider) {
        super(chunkProvider);
    }

    protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
        int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);
        int lightValue = propagatedLevel;
        Level world = ((Level) chunkSource.getLevel());
        BlockPos blockPos = BlockPos.of(targetId);
        ChunkPos chunkPos = new ChunkPos(blockPos);

        BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
        if (blockGetter instanceof ChunkAccess chunkAccess) {
            Biome biome = chunkAccess.getNoiseBiome(
                    QuartPos.fromBlock(blockPos.getX()),
                    QuartPos.fromBlock(blockPos.getY()),
                    QuartPos.fromBlock(blockPos.getZ())
            );
            lightValue = BiomeMapLoader.getInstance().getLightValue(biome.getRegistryName()).orElse(0);
        }

        if (world.dimension() == Level.OVERWORLD) { //The Overworld is a use case
            if (propagatedLevel <= 7) {
                return Math.max(lightValue, propagatedLevel);
            } else {
                return Math.min(lightValue, propagatedLevel);
            }
        } else {
            return Math.min(lightValue, propagatedLevel);
        }
    }

//    @Override
//    protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
//        int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);
//        BlockPos blockPos = BlockPos.of(targetId);
//        ChunkPos chunkPos = new ChunkPos(blockPos);
//
//        BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
//        if (blockGetter instanceof ChunkAccess chunkAccess) {
//            Biome biome = chunkAccess.getNoiseBiome(
//                    QuartPos.fromBlock(blockPos.getX()),
//                    QuartPos.fromBlock(blockPos.getY()),
//                    QuartPos.fromBlock(blockPos.getZ())
//            );
//
//
//            OptionalInt optionalInt = BiomeMapLoader.getInstance().getLightValue(biome.getRegistryName());
//
//            if (optionalInt.isPresent()) {
//                int biomeLightLevel = optionalInt.getAsInt();
//
//                //This may look complex but basically propagate the biomeLightLevel value here!
//                if (targetId != Long.MAX_VALUE && sourceId != Long.MAX_VALUE) {
//                    int i2;
//                    int l1;
//                    int i = BlockPos.getX(sourceId);
//                    int j = BlockPos.getY(sourceId);
//                    int k = BlockPos.getZ(sourceId);
//                    int l = BlockPos.getX(targetId);
//                    int i1 = BlockPos.getY(targetId);
//                    int j1 = BlockPos.getZ(targetId);
//                    int k1 = Integer.signum(l - i);
//                    Direction direction = Direction.fromNormal(k1, l1 = Integer.signum(i1 - j), i2 = Integer.signum(j1 - k));
//                    if (direction == null) {
//                        throw new IllegalStateException(String.format("Light was spread in illegal direction %d, %d, %d", k1, l1, i2));
//                    }
//                    boolean flag = i == l && k == j1;
//                    boolean flag1 = flag && j > i1;
//                    return flag1 && level == 0 && biomeLightLevel == 0 ? 0 : Math.max(1, biomeLightLevel);
//                }
//            }
//        }
//        return propagatedLevel;
//    }
}
