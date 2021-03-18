package com.unascribed.antiquated.ai;

import com.unascribed.antiquated.mixin.AccessorCreeperEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;

public class AntiqueCreeperAI extends AntiqueMonsterAI {

	public AntiqueCreeperAI(MobEntity entity) {
		this(entity, 0.7f);
	}

	public AntiqueCreeperAI(MobEntity entity, float moveSpeed) {
		super(entity, moveSpeed);
		this.fuse = 30;
		this.state = -1;
	}

	int timeSinceIgnited;
	int lastActiveTime;
	int fuse;
	int state;

	@Override
	public void tick() {
		this.lastActiveTime = this.timeSinceIgnited;
		if (this.timeSinceIgnited > 0 && this.state < 0) {
			--this.timeSinceIgnited;
		}
		if (this.state >= 0) {
			this.state = 2;
		}
		super.tick();
		if (this.state != 1) {
			this.state = -1;
		}
	}

	@Override
	protected void attackEntity(Entity other, float n) {
		if ((this.state <= 0 && n < 3.0f) || (this.state > 0 && n < 7.0f)) {
			if (this.timeSinceIgnited == 0) {
				((CreeperEntity)entity).ignite();
			}
			this.state = 1;
			++this.timeSinceIgnited;
			if (this.timeSinceIgnited == this.fuse) {
				((AccessorCreeperEntity)entity).antiquated$explode();
				entity.remove();
			}
			hasAttacked = true;
		}
	}


}
