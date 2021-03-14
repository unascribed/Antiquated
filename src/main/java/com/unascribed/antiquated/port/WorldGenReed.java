package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Material;

public class WorldGenReed extends WorldGenerator {
	public WorldGenReed() {
		super();
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		for (int i = 0; i < 20; ++i) {
			final int n4 = n + random.nextInt(4) - random.nextInt(4);
			final int n5 = n3 + random.nextInt(4) - random.nextInt(4);
			if (world.getBlockId(n4, n2, n5) == 0 && (world.getBlockMaterial(n4 - 1, n2 - 1, n5) == Material.WATER || world.getBlockMaterial(n4 + 1, n2 - 1, n5) == Material.WATER || world.getBlockMaterial(n4, n2 - 1, n5 - 1) == Material.WATER || world.getBlockMaterial(n4, n2 - 1, n5 + 1) == Material.WATER)) {
				for (int n6 = 2 + random.nextInt(random.nextInt(3) + 1), j = 0; j < n6; ++j) {
					if (AlphaBlock.reed.canBlockStay(world, n4, n2 + j, n5)) {
						world.setBlockId(n4, n2 + j, n5, AlphaBlock.reed.blockID);
					}
				}
			}
		}
		return true;
	}
}
