package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class WorldGenTrees extends WorldGenerator {
	public WorldGenTrees() {
		super();
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		final int n4 = random.nextInt(3) + 4;
		int n5 = 1;
		if (n2 < 1 || n2 + n4 + 1 > world.getHeightLimit()) {
			return false;
		}
		for (int i = n2; i <= n2 + 1 + n4; ++i) {
			int n6 = 1;
			if (i == n2) {
				n6 = 0;
			}
			if (i >= n2 + 1 + n4 - 2) {
				n6 = 2;
			}
			for (int n7 = n - n6; n7 <= n + n6 && n5 != 0; ++n7) {
				for (int n8 = n3 - n6; n8 <= n3 + n6 && n5 != 0; ++n8) {
					if (i >= 0 && i < world.getHeightLimit()) {
						final int func_600_a = world.getBlockId(n7, i, n8);
						if (func_600_a != 0 && func_600_a != AlphaBlock.leaves.blockID) {
							n5 = 0;
						}
					}
					else {
						n5 = 0;
					}
				}
			}
		}
		if (n5 == 0) {
			return false;
		}
		final int func_600_a2 = world.getBlockId(n, n2 - 1, n3);
		if ((func_600_a2 != AlphaBlock.grass.blockID && func_600_a2 != AlphaBlock.dirt.blockID) || n2 >= world.getHeightLimit() - n4 - 1) {
			return false;
		}
		world.setBlockId(n, n2 - 1, n3, AlphaBlock.dirt.blockID);
		for (int j = n2 - 3 + n4; j <= n2 + n4; ++j) {
			final int n9 = j - (n2 + n4);
			for (int n10 = 1 - n9 / 2, k = n - n10; k <= n + n10; ++k) {
				final int n11 = k - n;
				for (int l = n3 - n10; l <= n3 + n10; ++l) {
					final int n12 = l - n3;
					if (Math.abs(n11) == n10 && Math.abs(n12) == n10) {
						if (random.nextInt(2) == 0) {
							continue;
						}
						if (n9 == 0) {
							continue;
						}
					}
					if (!world.getBlockMaterial(k, j, l).isSolid()) {
						world.setBlockId(k, j, l, AlphaBlock.leaves.blockID);
					}
				}
			}
		}
		for (int n13 = 0; n13 < n4; ++n13) {
			final int func_600_a3 = world.getBlockId(n, n2 + n13, n3);
			if (func_600_a3 == 0 || func_600_a3 == AlphaBlock.leaves.blockID) {
				world.setBlockId(n, n2 + n13, n3, AlphaBlock.wood.blockID);
			}
		}
		return true;
	}
}
