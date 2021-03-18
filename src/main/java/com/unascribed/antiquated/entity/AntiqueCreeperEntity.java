package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.ai.AntiqueAI;
import com.unascribed.antiquated.ai.AntiqueCreeperAI;
import com.unascribed.antiquated.ai.DummyJumpControl;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class AntiqueCreeperEntity extends CreeperEntity implements AntiqueMonster, AntiqueSpawnable {

	private AntiqueAI ai;

	public AntiqueCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
		super(entityType, world);
		jumpControl = new DummyJumpControl();
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, ai = new AntiqueCreeperAI(this));
	}
	
	@Override
	protected void initEquipment(LocalDifficulty difficulty) {
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		Antiquated.playDefaultStepSound(this, pos, state);
	}
	
	@Override
	public boolean isAttacking() {
		return true;
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
	
	@Override
	public boolean canSpawnHere() {
		return ai.canSpawnHere();
	}
	
}
