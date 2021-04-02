package com.unascribed.antiquated;

import com.unascribed.antiquated.init.ABlocks;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class TimeEquivalents {

	public static final ImmutableBiMap<Block, Block> EQUIVS = ImmutableBiMap.<Block, Block>builder()
			.put(ABlocks.STONE, Blocks.STONE)
			.put(ABlocks.COBBLESTONE, Blocks.COBBLESTONE)
			.put(ABlocks.DIRT, Blocks.DIRT)
			.put(ABlocks.GRASS, Blocks.GRASS)
			.put(ABlocks.WOOD, Blocks.OAK_LOG)
			.put(ABlocks.LEAVES, Blocks.OAK_LEAVES)
			.put(ABlocks.GRAVEL, Blocks.GRAVEL)
			.put(ABlocks.SAND, Blocks.SAND)
			.put(ABlocks.CACTUS, Blocks.CACTUS)
			.put(ABlocks.FURNACE, Blocks.FURNACE)
			.put(ABlocks.COBBLESTONE_MOSSY, Blocks.MOSSY_COBBLESTONE)
			.put(ABlocks.PLANKS, Blocks.OAK_PLANKS)
			.put(ABlocks.SAPLING, Blocks.OAK_SAPLING)
			.put(ABlocks.ROSE, Blocks.POPPY)
			.put(ABlocks.DANDELION, Blocks.DANDELION)
			.put(ABlocks.WATER, Blocks.WATER)
			.put(ABlocks.LAVA, Blocks.LAVA)
			.put(ABlocks.OBSIDIAN, Blocks.OBSIDIAN)
			.put(ABlocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM)
			.put(ABlocks.RED_MUSHROOM, Blocks.RED_MUSHROOM)
			.put(ABlocks.REEDS, Blocks.SUGAR_CANE)
			.put(ABlocks.SNOW, Blocks.SNOW)
			.put(ABlocks.SNOW_BLOCK, Blocks.SNOW_BLOCK)
			.put(ABlocks.ICE, Blocks.ICE)
			.put(ABlocks.GLASS, Blocks.GLASS)
			.put(ABlocks.IRON_BLOCK, Blocks.IRON_BLOCK)
			.put(ABlocks.GOLD_BLOCK, Blocks.GOLD_BLOCK)
			.put(ABlocks.DIAMOND_BLOCK, Blocks.DIAMOND_BLOCK)
			.put(ABlocks.SPONGE, Blocks.SPONGE)
			.put(ABlocks.COAL_ORE, Blocks.COAL_ORE)
			.put(ABlocks.IRON_ORE, Blocks.IRON_ORE)
			.put(ABlocks.GOLD_ORE, Blocks.GOLD_ORE)
			.put(ABlocks.DIAMOND_ORE, Blocks.DIAMOND_ORE)
			.put(ABlocks.REDSTONE_ORE, Blocks.REDSTONE_ORE)
			.put(ABlocks.DISPENSER, Blocks.DISPENSER)
			.put(ABlocks.STONECUTTER, Blocks.STONECUTTER)
			.put(ABlocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS)
			.put(ABlocks.WOOD_STAIRS, Blocks.OAK_STAIRS)
			.build();
	
	public static final ImmutableMap<Block, Integer> FUEL_VALUES_MODERNIZE = ImmutableMap.<Block, Integer>builder()
			.put(Blocks.IRON_BLOCK, 5)
			.put(Blocks.GOLD_BLOCK, 15)
			.put(Blocks.DIAMOND_BLOCK, 30)
			.put(Blocks.REDSTONE_WIRE, 1)
			.build();
	
	public static final ImmutableMap<Block, Integer> FUEL_VALUES_ANTIQUIZE = ImmutableMap.<Block, Integer>builder()
			.put(ABlocks.IRON_BLOCK, 5)
			.put(ABlocks.GOLD_BLOCK, 15)
			.put(ABlocks.DIAMOND_BLOCK, 30)
			.put(Blocks.REDSTONE_WIRE, 1)
			.build();
	
}
