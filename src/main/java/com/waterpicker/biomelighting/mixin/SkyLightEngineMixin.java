package com.waterpicker.biomelighting.mixin;

import com.waterpicker.biomelighting.BiomeLighting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.SkyLightEngine;
import net.minecraft.world.level.lighting.SkyLightSectionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkyLightEngine.class)
public class SkyLightEngineMixin extends LayerLightEngineMixin {


    @Inject(method = "computeLevelFromNeighbor", at = @At("RETURN"), cancellable = true)
    void computeLevelFromNeighbor(long sourceId, long targetId, int level, CallbackInfoReturnable<Integer> cir) {

        BlockPos blockPos = BlockPos.of(targetId);
        ChunkPos chunkPos = new ChunkPos(blockPos);

        BlockGetter blockGetter = chunkSource.getChunkForLighting(chunkPos.x, chunkPos.z);
        if (blockGetter instanceof ChunkAccess chunkAccess) {
            Biome biome = chunkAccess.getNoiseBiome(
                    QuartPos.fromBlock(blockPos.getX()),
                    QuartPos.fromBlock(blockPos.getY()),
                    QuartPos.fromBlock(blockPos.getZ())
            );

            int propagatedLevel = cir.getReturnValue();
            if(BiomeLighting.getBiomeMap().containsKey(biome.getRegistryName())) {
                cir.setReturnValue(Math.min(BiomeLighting.getBiomeMap().get(biome.getRegistryName()), propagatedLevel));
            }
        }

        cir.setReturnValue(0);
    }
}
