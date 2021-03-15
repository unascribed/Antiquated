package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.ai.AntiqueMobAI;
import com.unascribed.antiquated.ai.DummyJumpControl;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AntiqueZombieEntity extends ZombieEntity implements AntiqueMonster {

	public AntiqueZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
		jumpControl = new DummyJumpControl();
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, new AntiqueMobAI(this));
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

	@Override
	public int getArmor() {
		return 0;
	}
	
	@Override
	public boolean canPickUpLoot() {
		return false;
	}
	
}
