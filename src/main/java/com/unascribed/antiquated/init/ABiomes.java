package com.unascribed.antiquated.init;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biome.Precipitation;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class ABiomes {

	// TODO alpha sky/fog colors
	
	public static final Biome VALLEY = new Biome.Builder()
			.precipitation(Precipitation.RAIN)
			.category(Category.PLAINS)
			.depth(0)
			.scale(0)
			.temperature(0.8f)
			.downfall(0.4f)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x3545ff)
					.waterFogColor(0x3545ff)
					.fogColor(12638463)
					.skyColor(getSkyColor(0.8f))
					.moodSound(BiomeMoodSound.CAVE)
				.build())
			.spawnSettings(new SpawnSettings.Builder()
					.playerSpawnFriendly()
				.build())
			.generationSettings(new GenerationSettings.Builder()
					// not used for generation, but is used to decide where the player can spawn
					.surfaceBuilder(SurfaceBuilder.NOPE.withConfig(new TernarySurfaceConfig(ABlocks.GRASS.getDefaultState(), ABlocks.DIRT.getDefaultState(), ABlocks.DIRT.getDefaultState())))
				.build())
			.build();
	
	public static final Biome TUNDRA = new Biome.Builder()
			.precipitation(Precipitation.SNOW)
			.category(Category.ICY)
			.depth(0)
			.scale(0)
			.temperature(0f)
			.downfall(0.5f)
			.effects(new BiomeEffects.Builder()
					.waterColor(0x3545ff)
					.waterFogColor(0x3545ff)
					.fogColor(12638463)
					.skyColor(getSkyColor(0.8f))
					.moodSound(BiomeMoodSound.CAVE)
				.build())
			.spawnSettings(new SpawnSettings.Builder()
					.playerSpawnFriendly()
				.build())
			.generationSettings(new GenerationSettings.Builder()
					// not used for generation, but is used to decide where the player can spawn
					.surfaceBuilder(SurfaceBuilder.NOPE.withConfig(new TernarySurfaceConfig(ABlocks.GRASS.getDefaultState(), ABlocks.DIRT.getDefaultState(), ABlocks.DIRT.getDefaultState())))
				.build())
			.build();
	 
	private static int getSkyColor(float temperature) {
		float f = temperature / 3.0F;
		f = MathHelper.clamp(f, -1.0F, 1.0F);
		return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F,
				1.0F);
	}
			
}
