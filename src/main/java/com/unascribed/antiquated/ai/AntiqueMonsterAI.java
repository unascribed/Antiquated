package com.unascribed.antiquated.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

public class AntiqueMonsterAI extends AntiqueCreatureAI {

	public AntiqueMonsterAI(MobEntity entity) {
		super(entity);
	}

	public AntiqueMonsterAI(MobEntity entity, float moveSpeed) {
		super(entity, moveSpeed);
	}

	public int attackStrength = 2;

	@Override
	protected Entity findPlayerToAttack()
	{
		PlayerEntity entityplayer = entity.world.getClosestPlayer(entity, 16);
		if(entityplayer != null && entity.canSee(entityplayer) && !entityplayer.abilities.invulnerable)
		{
			return entityplayer;
		} else
		{
			return null;
		}
	}

	@Override
	protected void attackEntity(Entity other, float f)
	{
		if(f < 2.5D && other.getBoundingBox().maxY > entity.getBoundingBox().minY && other.getBoundingBox().minY < entity.getBoundingBox().maxY)
		{
			attackTime = 20;
			other.damage(DamageSource.mob(entity), attackStrength);
		}
	}

	@Override
	protected float getBlockPathWeight(int i, int j, int k)
	{
		return 0.5f - entity.world.getBrightness(new BlockPos(i, j, k));
	}

	@Override
	public boolean canSpawnHere() {
		BlockPos bp = entity.getBlockPos();
		return entity.world.getLightLevel(LightType.SKY, bp) <= entity.world.random.nextInt(32) && entity.world.getLightLevel(bp) <= entity.world.random.nextInt(8) && super.canSpawnHere();
	}

}
