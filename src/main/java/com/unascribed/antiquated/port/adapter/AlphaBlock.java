package com.unascribed.antiquated.port.adapter;

public class AlphaBlock {
	
	public static final AlphaBlock stone = new AlphaBlock(1);
	public static final AlphaBlock grass = new AlphaBlock(2);
	public static final AlphaBlock dirt = new AlphaBlock(3);
	public static final AlphaBlock cobblestone = new AlphaBlock(4);
	
	public static final AlphaBlock bedrock = new AlphaBlock(7);
	
	public static final AlphaBlock waterMoving = new AlphaBlock(8);
	public static final AlphaBlock waterStill = new AlphaBlock(9);
	public static final AlphaBlock lavaMoving = new AlphaBlock(10);
	public static final AlphaBlock lavaStill = new AlphaBlock(11);
	
	public static final AlphaBlock sand = new AlphaBlock(12);
	public static final AlphaBlock gravel = new AlphaBlock(13);
	
	public static final AlphaBlock oreGold = new AlphaBlock(14);
	public static final AlphaBlock oreIron = new AlphaBlock(15);
	public static final AlphaBlock oreCoal = new AlphaBlock(16);
	
	public static final AlphaBlock wood = new AlphaBlock(17);
	public static final AlphaBlock leaves = new AlphaBlock(18);
	
	public static final AlphaBlock plantYellow = new AlphaBlock(37);
	public static final AlphaBlock plantRed = new AlphaBlock(38);
	public static final AlphaBlock mushroomBrown = new AlphaBlock(39);
	public static final AlphaBlock mushroomRed = new AlphaBlock(40);
	
	public static final AlphaBlock cobblestoneMossy = new AlphaBlock(48);
	
	public static final AlphaBlock mobSpawner = new AlphaBlock(52);
	
	public static final AlphaBlock chest = new AlphaBlock(54);
	
	public static final AlphaBlock oreDiamond = new AlphaBlock(56);
	
	public static final AlphaBlock oreRedstone = new AlphaBlock(73);
	
	public static final AlphaBlock snow = new AlphaBlock(78);
	public static final AlphaBlock ice = new AlphaBlock(79);
	
	public static final AlphaBlock cactus = new AlphaBlock(81);
	public static final AlphaBlock clay = new AlphaBlock(82);
	public static final AlphaBlock reed = new AlphaBlock(83);
	
	public static final AlphaBlock oldWood = new AlphaBlock(90);
	public static final AlphaBlock oldLeaves = new AlphaBlock(91);
	
	public final int blockID;
	
	public AlphaBlock(int id) {
		this.blockID = id;
	}

	public boolean canBlockStay(AlphaWorld world, int x, int y, int z) {
		return BlockIDConverter.convert(blockID).canPlaceAt(world.delegate, world.mut.set(x, y, z));
	}

}
