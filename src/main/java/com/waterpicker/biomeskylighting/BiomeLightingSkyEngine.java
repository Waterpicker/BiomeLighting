package com.waterpicker.biomeskylighting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.SkyLightEngine;

public class BiomeLightingSkyEngine extends SkyLightEngine {
    public BiomeLightingSkyEngine(LightChunkGetter chunkProvider) {
        super(chunkProvider);
    }

    @Override
    protected int computeLevelFromNeighbor(long sourceId, long targetId, int level) {
        int propagatedLevel = super.computeLevelFromNeighbor(sourceId, targetId, level);
        BlockPos blockPos = BlockPos.of(targetId);
        ChunkPos chunkPos = new ChunkPos(blockPos);

        BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
        if (blockGetter instanceof ChunkAccess chunkAccess) {
            Holder<Biome> biome = chunkAccess.getNoiseBiome(
                    QuartPos.fromBlock(blockPos.getX()),
                    QuartPos.fromBlock(blockPos.getY()),
                    QuartPos.fromBlock(blockPos.getZ())
            );
            if (biome.unwrapKey().isPresent()) {
                return Math.min(BiomeMapLoader.getInstance().getLightValue(biome.unwrapKey().get().location()).orElse(0), propagatedLevel);
            }
        }
        return propagatedLevel;
    }
}
