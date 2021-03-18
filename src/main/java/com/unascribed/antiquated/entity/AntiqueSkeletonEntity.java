package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.ai.AntiqueAI;
import com.unascribed.antiquated.ai.AntiqueSkeletonAI;
import com.unascribed.antiquated.ai.DummyJumpControl;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class AntiqueSkeletonEntity extends SkeletonEntity implements AntiqueMonster, AntiqueSpawnable {

	private AntiqueAI ai;

	public AntiqueSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
		super(entityType, world);
		jumpControl = new DummyJumpControl();
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, ai = new AntiqueSkeletonAI(this));
	}
	
	@Override
	public void updateAttackType() {
	}
	
	@Override
	protected void initEquipment(LocalDifficulty difficulty) {
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		Antiquated.playDefaultStepSound(this, pos, state);
	}
	
	@Override
	public boolean isBaby() {
		return false;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ASounds.SKELETON_HURT;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ASounds.SKELETON_AMBIENT;
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
