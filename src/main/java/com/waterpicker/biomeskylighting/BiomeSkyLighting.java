package com.waterpicker.biomeskylighting;

import com.google.gson.JsonElement;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;

@Mod("biomelighting")
public class BiomeSkyLighting {
    private static final Logger logger = LogManager.getLogger();

    public BiomeSkyLighting() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onDataGather);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BiomeSkyLightingConfig.CONFIG_SPEC);
        MinecraftForge.EVENT_BUS.addListener(this::onAddReloadListener);
    }

    public void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(BiomeMapLoader.getInstance());
    }

    public void onDataGather(GatherDataEvent event) {
        event.getGenerator().addProvider(new BiomeLightProvider(event.getGenerator()){
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

//    private void biomeRegister(BiomeLightingRegister event) {
//        //Values are reversed in lighting (15 - value in code = value in game)
//        if(BiomeLightingConfig.INSTANCE.biomeLightingEnabled.get()) { /* see them in action turn on biomeLightingEnabled in config! */
//            event.register(Biomes.FOREST.location(), 8);
//            event.register(Biomes.PLAINS.location(), 3);
//            event.register(Biomes.DRIPSTONE_CAVES.location(), 12);
//            event.register(Biomes.LUSH_CAVES.location(), 10);
//        }
//    }

//    public static Map<ResourceLocation, Integer> getBiomeMap() {
//        return biomeMap;
//    }
//
//    public void onServerStarting(ServerAboutToStartEvent event) {
//        BiomeSkyLightingRegister biomeSkyLightingRegister = new BiomeSkyLightingRegister();
//        MinecraftForge.EVENT_BUS.post(biomeSkyLightingRegister);
//        biomeMap = biomeSkyLightingRegister.getBiomeMap();
//    }

//    public static Logger getLogger() {
//        return logger;
//    }
}
