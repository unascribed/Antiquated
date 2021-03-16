package com.unascribed.antiquated;

import com.unascribed.antiquated.init.ABlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class AntiqueFlowerBlock extends PlantBlock {

	public AntiqueFlowerBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return super.canPlantOnTop(floor, world, pos) || floor.isOf(ABlocks.GRASS) || floor.isOf(ABlocks.DIRT);
	}

}
