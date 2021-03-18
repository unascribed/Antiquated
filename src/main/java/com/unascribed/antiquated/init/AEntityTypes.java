package com.unascribed.antiquated.init;

import com.unascribed.antiquated.entity.AntiqueArrowEntity;
import com.unascribed.antiquated.entity.AntiqueChickenEntity;
import com.unascribed.antiquated.entity.AntiqueCowEntity;
import com.unascribed.antiquated.entity.AntiqueCreeperEntity;
import com.unascribed.antiquated.entity.AntiquePigEntity;
import com.unascribed.antiquated.entity.AntiqueSheepEntity;
import com.unascribed.antiquated.entity.AntiqueSkeletonArrowEntity;
import com.unascribed.antiquated.entity.AntiqueSkeletonEntity;
import com.unascribed.antiquated.entity.AntiqueSpiderEntity;
import com.unascribed.antiquated.entity.AntiqueZombieEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class AEntityTypes {

	public static final EntityType<AntiquePigEntity> PIG = EntityType.Builder.create(AntiquePigEntity::new, SpawnGroup.CREATURE)
			.setDimensions(0.9f, 0.9f)
			.maxTrackingRange(10)
			.build("antiquated:pig");
	
	public static final EntityType<AntiqueCowEntity> COW = EntityType.Builder.create(AntiqueCowEntity::new, SpawnGroup.CREATURE)
			.setDimensions(0.9f, 1.3f)
			.maxTrackingRange(10)
			.build("antiquated:cow");
	
	public static final EntityType<AntiqueArrowEntity> ARROW = EntityType.Builder.create(AntiqueArrowEntity::new, SpawnGroup.MISC)
			.setDimensions(0.5f, 0.5f)
			.maxTrackingRange(4)
			.trackingTickInterval(20)
			.build("antiquated:arrow");
	
	public static final EntityType<AntiqueSkeletonArrowEntity> SKELETON_ARROW = EntityType.Builder.create(AntiqueSkeletonArrowEntity::new, SpawnGroup.MISC)
			.setDimensions(0.5f, 0.5f)
			.maxTrackingRange(4)
			.trackingTickInterval(20)
			.build("antiquated:skeleton_arrow");
	
	public static final EntityType<AntiqueSkeletonEntity> SKELETON = EntityType.Builder.create(AntiqueSkeletonEntity::new, SpawnGroup.MONSTER)
			.setDimensions(0.6f, 1.8f)
			.maxTrackingRange(8)
			.build("antiquated:skeleton");
	
	public static final EntityType<AntiqueZombieEntity> ZOMBIE = EntityType.Builder.create(AntiqueZombieEntity::new, SpawnGroup.MONSTER)
			.setDimensions(0.6f, 1.8f)
			.maxTrackingRange(8)
			.build("antiquated:zombie");
	
	public static final EntityType<AntiqueSpiderEntity> SPIDER = EntityType.Builder.create(AntiqueSpiderEntity::new, SpawnGroup.MONSTER)
			.setDimensions(1.4f, 0.9f)
			.maxTrackingRange(8)
			.build("antiquated:spider");
	
	public static final EntityType<AntiqueCreeperEntity> CREEPER = EntityType.Builder.create(AntiqueCreeperEntity::new, SpawnGroup.MONSTER)
			.setDimensions(0.6f, 1.8f)
			.maxTrackingRange(8)
			.build("antiquated:creeper");
	
	public static final EntityType<AntiqueSheepEntity> SHEEP = EntityType.Builder.create(AntiqueSheepEntity::new, SpawnGroup.CREATURE)
			.setDimensions(0.9f, 1.3f)
			.maxTrackingRange(10)
			.build("antiquated:sheep");
	
	public static final EntityType<AntiqueChickenEntity> CHICKEN = EntityType.Builder.create(AntiqueChickenEntity::new, SpawnGroup.CREATURE)
			.setDimensions(0.3f, 0.4f)
			.maxTrackingRange(10)
			.build("antiquated:chicken");
	
}
