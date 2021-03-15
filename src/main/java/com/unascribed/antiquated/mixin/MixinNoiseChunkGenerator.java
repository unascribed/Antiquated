package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.port.Alpha112ChunkGenerator;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;

@Mixin(NoiseChunkGenerator.class)
public abstract class MixinNoiseChunkGenerator extends ChunkGenerator {

	public MixinNoiseChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
		super(biomeSource, structuresConfig);
	}
	
	@Shadow @Final
	private long seed;
	
	@Inject(at=@At("TAIL"), method="buildSurface")
	public void buildSurface(ChunkRegion region, Chunk chunk, CallbackInfo ci) {
		final boolean DEBUG = false;
		try {
			if (DEBUG) System.out.println("buildSurface tail");
			ProtoChunk alphaChunkWinter = null;
			ProtoChunk alphaChunkNotWinter = null;
			BlockPos.Mutable mut = new BlockPos.Mutable();
			for (int x = 0; x < 16; x++) {
				if (DEBUG) System.out.println("x: "+x);
				for (int z = 0; z < 16; z++) {
					if (DEBUG) System.out.println("z: "+z);
					Biome b = region.getBiome(mut.set(chunk.getPos().getStartX()+x, chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z)+1, chunk.getPos().getStartZ()+z));
					Identifier id = region.toServerWorld().getRegistryManager().get(Registry.BIOME_KEY).getId(b);
					if (id != null && id.getNamespace().equals("antiquated")) {
						boolean winter = b.getCategory() == Category.ICY;
						if (DEBUG) System.out.println("found alpha biome, winter: "+winter);
						ProtoChunk alphaChunk = winter ? alphaChunkWinter : alphaChunkNotWinter;
						if (alphaChunk == null) {
							if (DEBUG) System.out.println("need to generate");
							AlphaWorld w = new AlphaWorld(region, seed);
							w.winterMode = winter;
							if (DEBUG) System.out.println("starting alpha generator");
							alphaChunk = new Alpha112ChunkGenerator(w, seed).generate(chunk.getPos().x, chunk.getPos().z);
							if (DEBUG) System.out.println("alpha generator completed");
							if (winter) alphaChunkWinter = alphaChunk;
							else alphaChunkNotWinter = alphaChunk;
						}
						for (int y = 0; y < 128; y++) {
							if (DEBUG) System.out.println("y: "+y);
							mut.set(chunk.getPos().getStartX()+x, y, chunk.getPos().getStartZ()+z);
							region.setBlockState(mut, alphaChunk.getBlockState(mut), 0);
						}
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}
	
}
