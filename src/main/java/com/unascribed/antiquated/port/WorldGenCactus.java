package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class WorldGenCactus extends WorldGenerator {
	public WorldGenCactus() {
		super();
	}

	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		for (int i = 0; i < 10; ++i) {
			final int n4 = n + random.nextInt(8) - random.nextInt(8);
			final int n5 = n2 + random.nextInt(4) - random.nextInt(4);
			final int n6 = n3 + random.nextInt(8) - random.nextInt(8);
			if (world.getBlockId(n4, n5, n6) == 0) {
				for (int n7 = 1 + random.nextInt(random.nextInt(3) + 1), j = 0; j < n7; ++j) {
					if (AlphaBlock.cactus.canBlockStay(world, n4, n5 + j, n6)) {
						world.setBlockId(n4, n5 + j, n6, AlphaBlock.cactus.blockID);
					}
				}
			}
		}
		return true;
	}
}
