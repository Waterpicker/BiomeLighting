package com.waterpicker.biomelighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;
import java.util.Map;

public class BiomeLightingRegister extends Event {
    Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

    public void register(ResourceLocation biomeResourceLocation, int lightLevel) {
        biomeMap.put(biomeResourceLocation, lightLevel);
    }

    public Map<ResourceLocation, Integer> getBiomeMap() {
        return biomeMap;
    }
}
