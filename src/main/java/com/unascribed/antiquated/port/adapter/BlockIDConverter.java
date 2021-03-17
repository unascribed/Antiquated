package com.unascribed.antiquated.port.adapter;

import com.unascribed.antiquated.init.ABlocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class BlockIDConverter {
	
	public static BlockState convert(int id) {
		if (id == 11) id = 10;
		if (id == 9) id = 8;
		if (id == 74) id = 73;
		if (id == 76) id = 75;
		if (id == 62) id = 61;
		Block b = QUICK_MAPPING[id];
		if (b == null) b = Blocks.MAGENTA_GLAZED_TERRACOTTA; // Jasmine said so
		return b.getDefaultState();
	}
	
	public static int convert(BlockState state) {
		if (state.isAir()) return 0;
		return MAPPING.inverse().getOrDefault(state.getBlock(), 99);
	}
	
	private static final Block[] QUICK_MAPPING = new Block[92];
	
	// TODO use Antique block variants
	private static final BiMap<Integer, Block> MAPPING = ImmutableBiMap.<Integer, Block>builder()
			.put(0, Blocks.AIR)
			.put(1, ABlocks.STONE)
			.put(2, ABlocks.GRASS)
			.put(3, ABlocks.DIRT)
			.put(4, ABlocks.COBBLESTONE)
			.put(5, ABlocks.PLANKS)
			.put(6, ABlocks.SAPLING)
			.put(7, Blocks.BEDROCK)
			.put(8, ABlocks.WATER)
			.put(10, ABlocks.LAVA)
			.put(12, ABlocks.SAND)
			.put(13, ABlocks.GRAVEL)
			.put(14, ABlocks.GOLD_ORE)
			.put(15, ABlocks.IRON_ORE)
			.put(16, ABlocks.COAL_ORE)
			.put(17, ABlocks.WOOD)
			.put(18, ABlocks.LEAVES)
			.put(19, Blocks.SPONGE)
			.put(20, ABlocks.GLASS)
			.put(35, Blocks.WHITE_WOOL)
			.put(37, ABlocks.DANDELION)
			.put(38, ABlocks.ROSE)
			.put(39, ABlocks.BROWN_MUSHROOM)
			.put(40, ABlocks.RED_MUSHROOM)
			.put(41, ABlocks.GOLD_BLOCK)
			.put(42, ABlocks.IRON_BLOCK)
			.put(43, Blocks.SMOOTH_STONE)
			.put(44, Blocks.SMOOTH_STONE_SLAB)
			.put(45, Blocks.BRICKS)
			.put(46, Blocks.TNT)
			.put(47, Blocks.BOOKSHELF)
			.put(48, ABlocks.COBBLESTONE_MOSSY)
			.put(49, ABlocks.OBSIDIAN)
			.put(50, Blocks.TORCH)
			.put(51, Blocks.FIRE)
			.put(52, Blocks.SPAWNER)
			.put(53, Blocks.OAK_STAIRS)
			.put(54, ABlocks.CHEST)
			.put(55, Blocks.REDSTONE_WIRE)
			.put(56, ABlocks.DIAMOND_ORE)
			.put(57, ABlocks.DIAMOND_BLOCK)
			.put(58, Blocks.CRAFTING_TABLE)
			.put(59, Blocks.WHEAT)
			.put(60, Blocks.FARMLAND)
			.put(61, ABlocks.FURNACE)
			.put(63, Blocks.OAK_SIGN)
			.put(64, ABlocks.DOOR)
			.put(65, Blocks.LADDER)
			.put(66, Blocks.RAIL)
			.put(67, Blocks.COBBLESTONE_STAIRS)
			.put(68, Blocks.OAK_WALL_SIGN)
			.put(69, Blocks.LEVER)
			.put(70, Blocks.STONE_PRESSURE_PLATE)
			.put(71, Blocks.IRON_DOOR)
			.put(72, Blocks.OAK_PRESSURE_PLATE)
			.put(73, ABlocks.REDSTONE_ORE)
			.put(75, Blocks.REDSTONE_TORCH)
			.put(77, Blocks.STONE_BUTTON)
			.put(78, ABlocks.SNOW)
			.put(79, ABlocks.ICE)
			.put(80, ABlocks.SNOW_BLOCK)
			.put(81, ABlocks.CACTUS)
			.put(82, Blocks.CLAY)
			.put(83, ABlocks.REEDS)
			.put(84, Blocks.JUKEBOX)
			.put(85, Blocks.OAK_FENCE)
			.put(90, ABlocks.WOOD_OLD)
			.put(91, ABlocks.LEAVES_OLD)
			.build();
	
	static {
		for (int i = 0; i < QUICK_MAPPING.length; i++) {
			QUICK_MAPPING[i] = MAPPING.get(i);
		}
	}

}
