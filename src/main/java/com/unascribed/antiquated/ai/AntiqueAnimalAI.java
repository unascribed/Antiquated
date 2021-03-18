package com.unascribed.antiquated.ai;

import com.unascribed.antiquated.init.ABlocks;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;

public class AntiqueAnimalAI extends AntiqueCreatureAI {

	public AntiqueAnimalAI(MobEntity entity) {
		super(entity);
	}
	
	@Override
	public boolean canSpawnHere() {
		BlockPos bp = entity.getBlockPos();
        return entity.world.getBlockState(bp.down()).isOf(ABlocks.GRASS) && entity.world.getLightLevel(bp) > 8 && super.canSpawnHere();
    }

}
