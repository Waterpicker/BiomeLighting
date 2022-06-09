package com.waterpicker.biomeskylighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
            Holder<Biome> biome = chunkAccess.getNoiseBiome(
                    QuartPos.fromBlock(blockPos.getX()),
                    QuartPos.fromBlock(blockPos.getY()),
                    QuartPos.fromBlock(blockPos.getZ())
            );
            lightValue = BiomeMapLoader.getInstance().getLightValue(biome.value().getRegistryName()).orElse(0);
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
}
