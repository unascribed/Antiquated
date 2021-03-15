package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.init.ASounds;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class AntiqueSkeletonArrowEntity extends ArrowEntity {

	public AntiqueSkeletonArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected SoundEvent getHitSound() {
		return ASounds.BOWHIT;
	}
	
}
