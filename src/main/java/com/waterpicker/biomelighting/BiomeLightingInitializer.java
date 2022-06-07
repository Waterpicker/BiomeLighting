package com.waterpicker.biomelighting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class BiomeLightingInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(BiomeMapLoader.getInstance());
    }
}