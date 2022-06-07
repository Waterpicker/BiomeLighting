package com.waterpicker.biomelighting;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.waterpicker.biomelighting.data.ResourceUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class BiomeMapLoader implements SimpleSynchronousResourceReloadListener, ModInitializer {
	private static final Logger LOGGER = LogManager.getLogger();
	private static BiomeMapLoader INSTANCE;
	private final Map<ResourceLocation, Integer> biomeMap = new HashMap<>();
	private ResourceLocation name = new ResourceLocation("biomelighting", "biomelights");

	private BiomeMapLoader() {
		INSTANCE = this;
	}

	public static BiomeMapLoader getInstance() {
		return INSTANCE;
	}

	@Override
	public void onInitialize() {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(BiomeMapLoader.getInstance());
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		biomeMap.clear();
		CompletableFuture<List<Optional<BiomeSkyLightingData>>> futurePatternList = ResourceUtil.loadResourcePathToCollection(manager, "", "biomelights.json", new ArrayList<>(), ResourceUtil.JSON_READER.andThenReader((a, b) -> JsonOps.INSTANCE.withParser(BiomeSkyLightingData.CODEC).andThen(DataResult::result).apply(a)));
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

	public OptionalInt getLightValue(Holder<Biome> biome) {
		return biome.unwrapKey().map(ResourceKey::location).filter(biomeMap::containsKey).stream().mapToInt(biomeMap::get).findFirst();
	}

	@Override
	public ResourceLocation getFabricId() {
		return name;
	}
}