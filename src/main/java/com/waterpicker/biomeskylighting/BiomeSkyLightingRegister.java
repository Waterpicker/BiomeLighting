package com.waterpicker.biomeskylighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;
import java.util.Map;

public class BiomeSkyLightingRegister extends Event {
    Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

    public BiomeSkyLightingRegister() {}

    public void register(ResourceLocation resourceLocation, int lightLevel) {
        biomeMap.put(resourceLocation, lightLevel);
    }

    public Map<ResourceLocation, Integer> getBiomeMap() {
        return biomeMap;
    }
}
