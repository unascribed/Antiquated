package com.unascribed.antiquated.mixin;

import java.lang.ref.WeakReference;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.level.ServerWorldProperties;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

	@Inject(at=@At("TAIL"), method="setupSpawn")
	private static void setupSpawn(ServerWorld world, ServerWorldProperties serverWorldProperties, boolean bonusChest, boolean debugWorld, boolean bl, CallbackInfo ci) {
		if (world.getServer().isSinglePlayer() && Antiquated.isAntiqueBiome(world, world.getBiome(world.getSpawnPos()))) {
			BlockPos pos = new BlockPos(world.getSpawnPos().getX(), world.getTopY(Type.WORLD_SURFACE, world.getSpawnPos().getX(), world.getSpawnPos().getZ()), world.getSpawnPos().getZ());
			world.setSpawnPos(pos, 180);
			Structure s = world.getStructureManager().getStructure(new Identifier("antiquated", "indev_house"));
			StructurePlacementData spd = new StructurePlacementData();
			s.place(world, world.getSpawnPos().add(-3, -1, -3), spd, world.random);
			Antiquated.serverForHouseAdvancement = new WeakReference<>(world.getServer());
			if (world.getBiome(world.getSpawnPos()).getCategory() == Category.ICY) {
				world.setWeather(0, 1000000, true, false);
			}
		}
	}
	
}
