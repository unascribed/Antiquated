package com.unascribed.antiquated.ai;

import com.unascribed.antiquated.port.PathEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AntiqueCreatureAI extends AntiqueAI {

	public AntiqueCreatureAI(MobEntity entity) {
		this(entity, 0.7f);
	}

	public AntiqueCreatureAI(MobEntity entity, float moveSpeed) {
		super(entity);
		this.moveSpeed = moveSpeed;
	}

	private PathEntity pathToEntity;
	protected Entity playerToAttack;
	protected boolean hasAttacked;

	protected float moveSpeed;

	protected float moveStrafing;
	protected float moveForward;
	
	public int attackTime;

	@Override
	public void tick() {
		moveStrafing *= 0.98F;
		moveForward *= 0.98F;
		entity.getMoveControl().strafeTo(moveForward, moveStrafing);
		
		if (attackTime > 0) attackTime--;

		hasAttacked = false;
		float f = 16F;
		if(playerToAttack == null)
		{
			playerToAttack = findPlayerToAttack();
			if(playerToAttack != null)
			{
				pathToEntity = pathfinder.createEntityPathTo(entity, playerToAttack, f);
			}
		} else
			if(!playerToAttack.isAlive())
			{
				playerToAttack = null;
			} else
			{
				float f1 = playerToAttack.distanceTo(entity);
				if(entity.canSee(playerToAttack))
				{
					attackEntity(playerToAttack, f1);
				}
			}
		if(!hasAttacked && playerToAttack != null && (pathToEntity == null || entity.world.random.nextInt(20) == 0))
		{
			pathToEntity = pathfinder.createEntityPathTo(entity, playerToAttack, f);
		} else
			if(pathToEntity == null && entity.world.random.nextInt(80) == 0 || entity.world.random.nextInt(80) == 0)
			{
				boolean flag = false;
				int j = -1;
				int k = -1;
				int l = -1;
				float f2 = -99999F;
				for(int i1 = 0; i1 < 10; i1++)
				{
					int j1 = MathHelper.floor((entity.getPos().x + entity.world.random.nextInt(13)) - 6D);
					int k1 = MathHelper.floor((entity.getPos().y + entity.world.random.nextInt(7)) - 3D);
					int l1 = MathHelper.floor((entity.getPos().z + entity.world.random.nextInt(13)) - 6D);
					float f3 = getBlockPathWeight(j1, k1, l1);
					if(f3 > f2)
					{
						f2 = f3;
						j = j1;
						k = k1;
						l = l1;
						flag = true;
					}
				}

				if(flag)
				{
					pathToEntity = pathfinder.createEntityPathTo(entity, j, k, l, 10F);
				}
			}
		int i = MathHelper.floor(entity.getPos().y);
		boolean flag1 = entity.isTouchingWater();
		boolean flag2 = entity.isInLava();
		entity.pitch = 0.0F;
		if(pathToEntity == null || entity.world.random.nextInt(100) == 0)
		{
			pathToEntity = null;
			return;
		}
		Vec3d vec3d = pathToEntity.getPosition(entity);
		for(double d = entity.getWidth() * 2.0F; vec3d != null && vec3d.squaredDistanceTo(entity.getPos().x, vec3d.y, entity.getPos().z) < d * d;)
		{
			pathToEntity.incrementPathIndex();
			if(pathToEntity.isFinished())
			{
				vec3d = null;
				pathToEntity = null;
			} else
			{
				vec3d = pathToEntity.getPosition(entity);
			}
		}

		entity.setJumping(false);
		if(vec3d != null)
		{
			double d1 = vec3d.x - entity.getPos().x;
			double d2 = vec3d.z - entity.getPos().z;
			double d3 = vec3d.y - i;
			float f4 = (float)((Math.atan2(d2, d1) * 180D) / 3.1415927410125732D) - 90F;
			float f5 = f4 - entity.yaw;
			moveForward = moveSpeed;
			for(; f5 < -180F; f5 += 360F) { }
			for(; f5 >= 180F; f5 -= 360F) { }
			if(f5 > 30F)
			{
				f5 = 30F;
			}
			if(f5 < -30F)
			{
				f5 = -30F;
			}
			entity.yaw += f5;
			if(hasAttacked && playerToAttack != null)
			{
				double d4 = playerToAttack.getPos().x - entity.getPos().x;
				double d5 = playerToAttack.getPos().z - entity.getPos().z;
				float f7 = entity.yaw;
				entity.yaw = (float)((Math.atan2(d5, d4) * 180D) / 3.1415927410125732D) - 90F;
				float f6 = (((f7 - entity.yaw) + 90F) * 3.141593F) / 180F;
				moveStrafing = -MathHelper.sin(f6) * moveForward * 1.0F;
				moveForward = MathHelper.cos(f6) * moveForward * 1.0F;
			}
			if(d3 > 0.0D)
			{
				entity.setJumping(true);
			}
		}
		if(playerToAttack != null)
		{
			faceEntity(playerToAttack, 30F);
		}
		if(entity.horizontalCollision)
		{
			entity.setJumping(true);
		}
		if(entity.world.random.nextFloat() < 0.8F && (flag1 || flag2))
		{
			entity.setJumping(true);
		}
	}

	protected void attackEntity(Entity entity, float f)
	{
	}

	protected float getBlockPathWeight(int i, int j, int k)
	{
		return 0.0F;
	}

	protected Entity findPlayerToAttack()
	{
		return null;
	}

	public void faceEntity(Entity other, float f)
	{
		double d = other.getPos().x - entity.getPos().x;
		double d2 = other.getPos().z - entity.getPos().z;
		double d1;
		if(other instanceof LivingEntity)
		{
			LivingEntity entityliving = (LivingEntity)other;
			d1 = (entityliving.getPos().y + entityliving.getEyeHeight(entityliving.getPose())) - (entity.getPos().y + entity.getEyeHeight(entity.getPose()));
		} else
		{
			d1 = (other.getBoundingBox().minY + other.getBoundingBox().maxY) / 2D - (entity.getPos().y + entity.getEyeHeight(entity.getPose()));
		}
		double d3 = MathHelper.sqrt(d * d + d2 * d2);
		float f1 = (float)((Math.atan2(d2, d) * 180D) / 3.1415927410125732D) - 90F;
		float f2 = (float)((Math.atan2(d1, d3) * 180D) / 3.1415927410125732D);
		entity.pitch = -updateRotation(entity.pitch, f2, f);
		entity.yaw = updateRotation(entity.yaw, f1, f);
	}

	private float updateRotation(float f, float f1, float f2)
	{
		float f3;
		for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
		for(; f3 >= 180F; f3 -= 360F) { }
		if(f3 > f2)
		{
			f3 = f2;
		}
		if(f3 < -f2)
		{
			f3 = -f2;
		}
		return f + f3;
	}
	
	@Override
	public boolean canSpawnHere() {
        return super.canSpawnHere() && getBlockPathWeight(MathHelper.floor(entity.getX()), MathHelper.floor(entity.getY()), MathHelper.floor(entity.getZ())) > 0;
    }

}
