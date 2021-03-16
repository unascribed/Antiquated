package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class WorldGenNotchTree extends WorldGenerator {
	public WorldGenNotchTree() {
		super();
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int x, final int y, final int z) {
		int height = random.nextInt(3) + 4;
		boolean canGenerate = true;
		if (y < 1 || y + height + 1 > world.getHeightLimit()) {
			return false;
		}
		for (int i = y; i <= y + 1 + height; ++i) {
			int radius = 1;
			if (i == y) {
				radius = 0;
			}
			if (i >= y + 1 + height - 2) {
				radius = 2;
			}
			for (int cX = x - radius; cX <= x + radius && canGenerate; ++cX) {
				for (int cZ = z - radius; cZ <= z + radius && canGenerate; ++cZ) {
					if (i >= 0 && i < 128) {
						int blockId = world.getBlockId(cX, i, cZ);
						if (blockId != 0 && blockId != AlphaBlock.oldLeaves.blockID) {
							canGenerate = false;
						}
					} else {
						canGenerate = false;
					}
				}
			}
		}
		if (!canGenerate) {
			return false;
		}
		int soilBlock = world.getBlockId(x, y - 1, z);
		if ((soilBlock != AlphaBlock.grass.blockID && soilBlock != AlphaBlock.dirt.blockID) || y >= world.getHeightLimit() - height - 1) {
			return false;
		}
		world.setBlockId(x, y - 1, z, AlphaBlock.dirt.blockID);
		
		// leaves generator for normal trees, copied here without the leaf setting code to prevent a seed desync
		for (int j = y - 3 + height; j <= y + height; ++j) {
			final int n9 = j - (y + height);
			for (int n10 = 1 - n9 / 2, k = x - n10; k <= x + n10; ++k) {
				final int n11 = k - x;
				for (int l = z - n10; l <= z + n10; ++l) {
					final int n12 = l - z;
					if (Math.abs(n11) == n10 && Math.abs(n12) == n10) {
						if (random.nextInt(2) == 0) {
							continue;
						}
						if (n9 == 0) {
							continue;
						}
					}
				}
			}
		}
		
		for (int cY = y - 2 + height; cY <= y + height; ++cY) {
			for (int cX = x - 1; cX <= x + 1; ++cX) {
				for (int cZ = z - 1; cZ <= z + 1; ++cZ) {
					if (!world.getBlockMaterial(cX, cY, cZ).isSolid()) {
						world.setBlockId(cX, cY, cZ, AlphaBlock.oldLeaves.blockID);
					}
				}
			}
		}
		for (int cY = 0; cY < height; ++cY) {
			int currentBlock = world.getBlockId(x, y + cY, z);
			if (currentBlock == 0 || currentBlock == AlphaBlock.oldLeaves.blockID) {
				world.setBlockId(x, y + cY, z, AlphaBlock.oldWood.blockID);
			}
		}
		return true;
	}
}
