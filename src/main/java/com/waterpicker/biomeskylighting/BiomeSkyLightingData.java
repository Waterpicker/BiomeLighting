package com.waterpicker.biomeskylighting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record BiomeSkyLightingData(boolean replace, Map<ResourceLocation, Integer> biomelights) {
    public static final Codec<BiomeSkyLightingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("replace").forGetter(BiomeSkyLightingData::replace),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT).fieldOf("biomeslights").forGetter(BiomeSkyLightingData::biomelights))
            .apply(instance, BiomeSkyLightingData::new));
}
