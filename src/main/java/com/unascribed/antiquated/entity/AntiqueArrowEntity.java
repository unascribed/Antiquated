package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.init.ASounds;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class AntiqueArrowEntity extends ArrowEntity {

	public AntiqueArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
		super(entityType, world);
		setDamage(4);
	}

	@Override
	protected SoundEvent getHitSound() {
		return ASounds.BOWHIT;
	}
	
	@Override
	protected void onHit(LivingEntity target) {
	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity victim = entityHitResult.getEntity();
		Entity owner = this.getOwner();
		DamageSource src;
		if (owner == null) {
			src = DamageSource.arrow(this, this);
		} else {
			src = DamageSource.arrow(this, owner);
			if (owner instanceof LivingEntity) {
				((LivingEntity)owner).onAttacking(victim);
			}
		}
		
		if (victim.damage(src, (float)getDamage())) {
			if (victim instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) victim;

				if (!world.isClient && owner instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, owner);
					EnchantmentHelper.onTargetDamaged((LivingEntity) owner, livingEntity);
				}
			}

			playSound(getSound(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			remove();
		} else {
			setVelocity(getVelocity().multiply(-0.1D));
			yaw += 180.0F;
			prevYaw += 180.0F;
			if (!world.isClient && getVelocity().lengthSquared() < 1.0E-7D) {
				if (pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
					dropStack(this.asItemStack(), 0.1F);
				}
				remove();
			}
		}
	}
	
}
