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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public abstract class MixinChunkGenerator {
	
	@Shadow @Final
	private long worldSeed;
	
	@Inject(at=@At("TAIL"), method="generateFeatures")
	public void generateFeatures(ChunkRegion region, StructureAccessor accessor, CallbackInfo ci) {
		final boolean DEBUG = false;
		try {
			if (DEBUG) System.out.println("generateFeatures tail");
			ChunkPos cp = new ChunkPos(region.getCenterChunkX(), region.getCenterChunkZ());
			BlockPos.Mutable mut = new BlockPos.Mutable();
			out: for (int x = 0; x < 16; x++) {
				if (DEBUG) System.out.println("x: "+x);
				for (int z = 0; z < 16; z++) {
					if (DEBUG) System.out.println("z: "+z);
					Biome b = region.getBiome(mut.set(cp.getStartX()+x, 0, cp.getStartZ()+z));
					Identifier id = region.toServerWorld().getRegistryManager().get(Registry.BIOME_KEY).getId(b);
					if (DEBUG) System.out.println(id);
					if (id != null && id.getNamespace().equals("antiquated")) {
						if (DEBUG) System.out.println("found alpha biome, populate");
						AlphaWorld w = new AlphaWorld(region, worldSeed);
						w.winterMode = b.getCategory() == Category.ICY;
						new Alpha112ChunkGenerator(w, worldSeed).populate(cp.x, cp.z);
						if (DEBUG) System.out.println("alpha populator completed");
						break out;
					} else {
						if (DEBUG) System.out.println("not an alpha biome");
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}
	
}
