package com.unascribed.antiquated.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

@Mixin(BlockEntityType.class)
public interface AccessorBlockEntityType {

	@Accessor("blocks")
	Set<Block> antiquated$getBlocks();
	
	@Accessor("blocks")
	void antiquated$setBlocks(Set<Block> blocks);
	
}
