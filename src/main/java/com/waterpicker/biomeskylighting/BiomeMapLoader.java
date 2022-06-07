package com.waterpicker.biomeskylighting;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class BiomeMapLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final BiomeMapLoader INSTANCE = new BiomeMapLoader();
	private final Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

	private BiomeMapLoader() {
	}

	public static BiomeMapLoader getInstance() {
		return INSTANCE;
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		biomeMap.clear();
		CompletableFuture<List<Optional<BiomeSkyLightingData>>> futurePatternList = ResourceUtil.loadResourcePathToCollection(manager, "", "biomelights.json", new ArrayList<>(), ResourceUtil.JSON_READER.andThenReader((a,b) -> JsonOps.INSTANCE.withParser(BiomeSkyLightingData.CODEC).andThen(DataResult::result).apply(a)));
		for (Optional<BiomeSkyLightingData> dataOptional : futurePatternList.join()) {
			dataOptional.ifPresent(data -> {
				if(!data.replace()) {
					biomeMap.clear();
				}

				biomeMap.putAll(data.biomelights());
			});
		}

		System.out.println("Derp: " + biomeMap);
	}

	public OptionalInt getLightValue(ResourceLocation block) {
		if (biomeMap.containsKey(block)) {
			return OptionalInt.of(biomeMap.get(block));
		}
		return OptionalInt.empty();
	}
}