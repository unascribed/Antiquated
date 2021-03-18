package com.unascribed.antiquated.port;

import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

public class AlphaSpawnerMonsters extends AlphaSpawnerAnimals {

	@SafeVarargs
	public AlphaSpawnerMonsters(int maxSpawns, Class<?> spawnBaseClass, EntityType<? extends LivingEntity>... spawnSubclasses) {
		super(maxSpawns, spawnBaseClass, spawnSubclasses);
	}

	@Override
	protected BlockPos func_1151_a(AlphaWorld world, final int n, final int n2) {
		return new BlockPos(n + world.rand.nextInt(16),
				world.rand.nextInt(world.rand.nextInt(120) + 8),
				n2 + world.rand.nextInt(16));
	}
}
