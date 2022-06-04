package com.waterpicker.biomelighting;

import me.shedaniel.autoconfig.ConfigData;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BiomeLightingConfig implements ConfigData {

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final BiomeLightingConfig INSTANCE;

    static {
        Pair<BiomeLightingConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(BiomeLightingConfig::new);
        CONFIG_SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public ForgeConfigSpec.BooleanValue biomeLightingEnabled;

    private BiomeLightingConfig(ForgeConfigSpec.Builder builder) {
        this.biomeLightingEnabled = builder.comment("should biome lighting be enabled for minecraft's biomes, will not effect modded biomes").define("biomeLightingEnabled", true);
    }
}
