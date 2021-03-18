package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.ai.AntiqueAI;
import com.unascribed.antiquated.ai.AntiqueSpiderAI;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AntiqueSpiderEntity extends SpiderEntity implements AntiqueMonster, AntiqueSpawnable {

	private AntiqueAI ai;

	public AntiqueSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, ai = new AntiqueSpiderAI(this));
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
	public boolean canSpawnHere() {
		return ai.canSpawnHere();
	}

}
