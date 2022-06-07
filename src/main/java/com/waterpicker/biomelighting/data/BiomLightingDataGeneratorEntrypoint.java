package com.waterpicker.biomelighting.data;

import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.biome.Biomes;

import java.util.function.BiConsumer;

public class BiomLightingDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(generator -> new BiomeLightProvider(generator) {
            @Override
            protected void generateLights(BiConsumer<String, JsonElement> consumer) {
                new BiomeLightBuilder("data/minecraft")
                        .biomeLight(Biomes.FOREST, 8)
                        .biomeLight(Biomes.PLAINS, 3)
                        .biomeLight(Biomes.DRIPSTONE_CAVES, 12)
                        .biomeLight(Biomes.LUSH_CAVES, 10)
                        .run(consumer);
            }
        });
    }
}
