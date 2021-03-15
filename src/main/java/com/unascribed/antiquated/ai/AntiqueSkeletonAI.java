package com.unascribed.antiquated.ai;

import com.unascribed.antiquated.entity.AntiqueSkeletonArrowEntity;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class AntiqueSkeletonAI extends AntiqueMobAI {

	public AntiqueSkeletonAI(MobEntity entity) {
		super(entity);
	}
	
	public AntiqueSkeletonAI(MobEntity entity, float moveSpeed) {
		super(entity, moveSpeed);
	}
	
	// TODO mob infighting/revenge AI
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void attackEntity(Entity other, float f)
	{
		if(f < 10F)
		{
			double d = other.getPos().x - entity.getPos().x;
			double d1 = other.getPos().z - entity.getPos().z;
			if(attackTime == 0)
			{
				AntiqueSkeletonArrowEntity entityarrow = new AntiqueSkeletonArrowEntity(AEntityTypes.SKELETON_ARROW, entity.world);
				entityarrow.setOwner(entity);
				entityarrow.refreshPositionAndAngles(
						entity.getPos().x - MathHelper.cos((entity.yaw / 180F) * 3.141593F) * 0.16F,
						((entity.getPos().y + entity.getEyeHeight(entity.getPose())) - 0.10000000149011612D) + 1.3999999761581421D,
						entity.getPos().z - MathHelper.sin((entity.yaw / 180F) * 3.141593F) * 0.16F,
					entity.yaw, entity.pitch);
				entityarrow.setVelocity(
						-MathHelper.sin((entity.yaw / 180F) * 3.141593F) * MathHelper.cos((entity.pitch / 180F) * 3.141593F),
						MathHelper.cos((entity.yaw / 180F) * 3.141593F) * MathHelper.cos((entity.pitch / 180F) * 3.141593F),
						-MathHelper.sin((entity.pitch / 180F) * 3.141593F),
						1.5f, 1.0f
						);
				double d2 = other.getPos().y - 0.20000000298023224D - entityarrow.getPos().y;
				float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
				entity.world.playSound(null, entity.getPos().x, entity.getPos().y, entity.getPos().z, ASounds.BOW, SoundCategory.HOSTILE, 1, 1.0F / (entity.world.random.nextFloat() * 0.4f + 0.8f));
				entity.world.spawnEntity(entityarrow);
				entityarrow.setVelocity(d, d2 + f1, d1, 0.6F, 12F);
				attackTime = 30;
			}
			entity.yaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
			hasAttacked = true;
		}
	}

}
