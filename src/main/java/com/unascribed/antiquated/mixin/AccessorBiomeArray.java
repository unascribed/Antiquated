package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;

@Mixin(BiomeArray.class)
public interface AccessorBiomeArray {

	@Accessor("data")
	Biome[] antiquated$getData();
	
}
