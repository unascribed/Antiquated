package com.unascribed.antiquated;

import com.unascribed.antiquated.init.ABlockEntityTypes;

import net.minecraft.block.entity.ChestBlockEntity;

public class AntiqueChestBlockEntity extends ChestBlockEntity {

	public AntiqueChestBlockEntity() {
		super(ABlockEntityTypes.CHEST);
	}
	
	@Override
	public int getMaxCountPerStack() {
		// can't stack to 99 because the engine has had a max stack of 64 for so long that >64 broke at some point and nobody noticed or would have cared
		return 64;
	}
	
	@Override
	public void tick() {
	}
	
}
