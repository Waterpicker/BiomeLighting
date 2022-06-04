package com.waterpicker.biomelighting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("biomelighting")
public class BiomeLighting {
    private static Map<ResourceLocation, Integer> biomeMap;
    public BiomeLighting() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(this::biomeRegister);
    }

    private void biomeRegister(BiomeLightingRegister event) {
        event.register(Biomes.FOREST.location(), 0);
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
