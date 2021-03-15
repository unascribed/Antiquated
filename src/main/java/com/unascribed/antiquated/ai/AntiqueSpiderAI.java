package com.unascribed.antiquated.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class AntiqueSpiderAI extends AntiqueMobAI {

	public AntiqueSpiderAI(MobEntity entity) {
		super(entity);
	}

	@Override
	protected Entity findPlayerToAttack()
	{
		float f = entity.getBrightnessAtEyes();
		if(f < 0.5F)
		{
			return entity.world.getClosestPlayer(entity, 16);
		} else
		{
			return null;
		}
	}

	@Override
	protected void attackEntity(Entity other, float f)
	{
		float f1 = other.getBrightnessAtEyes();
		if(f1 > 0.5F && other.world.random.nextInt(100) == 0)
		{
			playerToAttack = null;
			return;
		}
		if(f > 2.0F && f < 6F && other.world.random.nextInt(10) == 0)
		{
			if(entity.isOnGround())
			{
				double d = other.getPos().x - entity.getPos().x;
				double d1 = other.getPos().z - entity.getPos().z;
				float f2 = MathHelper.sqrt(d * d + d1 * d1);
				entity.setVelocity(
						(d / f2) * 0.5D * 0.80000001192092896D + entity.getVelocity().x * 0.20000000298023224D,
						0.40000000596046448D,
						(d1 / f2) * 0.5D * 0.80000001192092896D + entity.getVelocity().z * 0.20000000298023224
						);
			}
		} else
		{
			super.attackEntity(other, f);
		}
	}

}
