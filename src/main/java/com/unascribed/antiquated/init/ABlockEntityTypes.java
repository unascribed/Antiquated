package com.unascribed.antiquated.init;

import com.unascribed.antiquated.AntiqueChestBlockEntity;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.entity.BlockEntityType;

public class ABlockEntityTypes {

	public static final BlockEntityType<AntiqueChestBlockEntity> CHEST = new BlockEntityType<>(AntiqueChestBlockEntity::new, ImmutableSet.of(ABlocks.CHEST), null);
	
}
