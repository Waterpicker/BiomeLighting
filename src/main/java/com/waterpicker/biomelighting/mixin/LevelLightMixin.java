package com.waterpicker.biomelighting.mixin;

import com.waterpicker.biomelighting.BiomeLightingSkyEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.lighting.LightEventListener;
import net.minecraft.world.level.lighting.SkyLightSectionStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.level.lighting.SkyLightEngine.HORIZONTALS;

@Mixin(LevelLightEngine.class)
abstract public class LevelLightMixin implements LightEventListener {
    @Shadow @Final @Mutable private LayerLightEngine<?, ?> skyEngine;
    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(LightChunkGetter chunkProvider, boolean hasBlockLight, boolean hasSkyLight, CallbackInfo ci) {
            skyEngine = hasSkyLight ? new BiomeLightingSkyEngine(chunkProvider) : null;
    }
}
