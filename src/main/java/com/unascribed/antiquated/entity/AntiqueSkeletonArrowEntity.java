package com.unascribed.antiquated.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class AntiqueSkeletonArrowEntity extends AntiqueArrowEntity {

	public AntiqueSkeletonArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
		super(entityType, world);
	}
	
}
