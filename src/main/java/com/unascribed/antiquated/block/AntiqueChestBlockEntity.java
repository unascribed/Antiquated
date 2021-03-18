package com.unascribed.antiquated.block;

import com.unascribed.antiquated.init.ABlockEntityTypes;

import net.minecraft.block.entity.ChestBlockEntity;

public class AntiqueChestBlockEntity extends ChestBlockEntity {

	public AntiqueChestBlockEntity() {
		super(ABlockEntityTypes.CHEST);
	}
	
	@Override
	public int getMaxCountPerStack() {
		return 100;
	}
	
	@Override
	public void tick() {
	}
	
}
