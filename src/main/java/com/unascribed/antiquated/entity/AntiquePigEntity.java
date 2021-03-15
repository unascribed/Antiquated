package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.ai.AntiqueCreatureAI;
import com.unascribed.antiquated.ai.DummyJumpControl;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AntiquePigEntity extends PigEntity implements AntiqueCreature {

	public AntiquePigEntity(EntityType<? extends PigEntity> entityType, World world) {
		super(entityType, world);
		jumpControl = new DummyJumpControl();
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, new AntiqueCreatureAI(this));
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
	public boolean canBreedWith(AnimalEntity other) {
		return false;
	}
	
	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}
	
}
