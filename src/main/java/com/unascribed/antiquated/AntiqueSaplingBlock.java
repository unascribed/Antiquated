package com.unascribed.antiquated;

import java.util.Random;

import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.port.WorldGenBigTree;
import com.unascribed.antiquated.port.WorldGenNotchTree;
import com.unascribed.antiquated.port.WorldGenTrees;
import com.unascribed.antiquated.port.WorldGenerator;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class AntiqueSaplingBlock extends PlantBlock {

	private static final IntProperty AGE = IntProperty.of("age", 0, 15);
	
	public AntiqueSaplingBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(ABlocks.GRASS) || floor.isOf(ABlocks.DIRT);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(5) == 0) {
			int age = state.get(AGE);
			if (age < 15) {
				world.setBlockState(pos, state.with(AGE, age+1));
			} else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 0);
				WorldGenerator worldGenerator;
				if (this == ABlocks.SAPLING) {
					if (random.nextInt(10) == 0) {
						worldGenerator = new WorldGenBigTree();
					} else {
						worldGenerator = new WorldGenTrees();
					}
				} else {
					worldGenerator = new WorldGenNotchTree();
				}
				AlphaWorld aw = new AlphaWorld(world, 0);
				aw.heightLimit = world.getHeight();
				if (!worldGenerator.populate(aw, random, pos.getX(), pos.getY(), pos.getZ())) {
					world.setBlockState(pos, state);
				}
			}
		}
	}

}
