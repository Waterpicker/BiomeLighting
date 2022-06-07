package com.waterpicker.biomeskylighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;
import java.util.Map;

public class BiomeSkyLightingRegister extends Event {
    Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

    public void register(ResourceLocation biomeResourceLocation, int lightLevel) {
        if (biomeResourceLocation.getNamespace().equals("data/minecraft") && !BiomeSkyLightingConfig.INSTANCE.enableVanillaBiomeSkyLighting.get()) {

            return;
        }

        biomeMap.put(biomeResourceLocation, lightLevel);
    }

    public Map<ResourceLocation, Integer> getBiomeMap() {
        return biomeMap;
    }
}
