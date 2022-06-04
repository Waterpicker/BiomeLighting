package com.waterpicker.biomelighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("biomelighting")
public class BiomeLighting {
    private static Map<ResourceLocation, Integer> biomeMap;
    public BiomeLighting() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BiomeLightingConfig.CONFIG_SPEC);
        MinecraftForge.EVENT_BUS.addListener(this::biomeRegister);
    }

    private void biomeRegister(BiomeLightingRegister event) {
        //Values are reversed in lighting (15 - value in code = value in game)
        if(BiomeLightingConfig.INSTANCE.biomeLightingEnabled.get()) { /* see them in action turn on biomeLightingEnabled in config! */
            event.register(Biomes.FOREST.location(), 8);
            event.register(Biomes.PLAINS.location(), 3);
            event.register(Biomes.DRIPSTONE_CAVES.location(), 12);
            event.register(Biomes.LUSH_CAVES.location(), 10);
        }
    }

    public static Map<ResourceLocation, Integer> getBiomeMap() {
        return biomeMap;
    }

    public void onServerStarting(ServerAboutToStartEvent event) {
        BiomeLightingRegister biomeLightingRegister = new BiomeLightingRegister();
        MinecraftForge.EVENT_BUS.post(biomeLightingRegister);
        biomeMap = biomeLightingRegister.getBiomeMap();
    }
}
