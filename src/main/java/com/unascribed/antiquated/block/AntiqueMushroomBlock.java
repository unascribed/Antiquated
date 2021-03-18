package com.unascribed.antiquated.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class AntiqueMushroomBlock extends PlantBlock {

	public AntiqueMushroomBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOpaqueFullCube(world, pos);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos floorPos = pos.down();
		return world.getBaseLightLevel(pos, 0) < 13 && this.canPlantOnTop(world.getBlockState(floorPos), world, floorPos);
	}
}
