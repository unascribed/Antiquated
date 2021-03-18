package com.unascribed.antiquated.ai;

import com.unascribed.antiquated.port.BetaPathfinder;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

public abstract class AntiqueAI extends Goal {

	protected final MobEntity entity;
	protected BetaPathfinder pathfinder;
	
	public AntiqueAI(MobEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public boolean canStart() {
		return true;
	}
	
	@Override
	public void start() {
		pathfinder = new BetaPathfinder(new AlphaWorld(entity.world, 0));
	}
	
	@Override
	public void stop() {
		pathfinder = null;
	}
	
	public boolean canSpawnHere() {
		return entity.world.getCollisions(entity, entity.getBoundingBox(), e -> true).count() == 0 && !entity.world.containsFluid(entity.getBoundingBox());
	}

}
