package com.waterpicker.biomeskylighting;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BiomeSkyLightingConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final BiomeSkyLightingConfig INSTANCE;

    static {
        Pair<BiomeSkyLightingConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(BiomeSkyLightingConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public ForgeConfigSpec.BooleanValue enableVanillaBiomeSkyLighting;

    private BiomeSkyLightingConfig(ForgeConfigSpec.Builder builder) {
        this.enableVanillaBiomeSkyLighting = builder.comment("Should biome sky lighting be enabled for biomes with the minecraft namespace? This will not effect modded biomes.").define("enableVanillaBiomeSkyLighting", true);
    }
}
