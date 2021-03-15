package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.ai.AntiqueSpiderAI;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AntiqueSpiderEntity extends SpiderEntity implements AntiqueMonster {

	public AntiqueSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, new AntiqueSpiderAI(this));
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		// base step sound logic
		if (!state.getMaterial().isLiquid()) {
			BlockState blockState = this.world.getBlockState(pos.up());
			BlockSoundGroup blockSoundGroup = blockState.isOf(Blocks.SNOW)
					? blockState.getSoundGroup()
					: state.getSoundGroup();
			this.playSound(blockSoundGroup.getStepSound(),
					blockSoundGroup.getVolume() * 0.15F,
					blockSoundGroup.getPitch());
		}
	}
	
	@Override
	public boolean isBaby() {
		return false;
	}

}
