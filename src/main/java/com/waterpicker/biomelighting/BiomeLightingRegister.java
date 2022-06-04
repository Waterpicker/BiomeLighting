package com.waterpicker.biomelighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;
import java.util.Map;

public class BiomeLightingRegister extends Event {
    Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

    public BiomeLightingRegister() {}

    public void register(ResourceLocation resourceLocation, int lightLevel) {
        biomeMap.put(resourceLocation, lightLevel);
    }

    public Map<ResourceLocation, Integer> getBiomeMap() {
        return biomeMap;
    }
}
