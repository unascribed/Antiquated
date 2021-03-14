package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class WorldGenFlowers extends WorldGenerator {
	private AlphaBlock field_885_a;

	public WorldGenFlowers(AlphaBlock field_885_a) {
		super();
		this.field_885_a = field_885_a;
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		for (int i = 0; i < 64; ++i) {
			final int n4 = n + random.nextInt(8) - random.nextInt(8);
			final int n5 = n2 + random.nextInt(4) - random.nextInt(4);
			final int n6 = n3 + random.nextInt(8) - random.nextInt(8);
			if (world.getBlockId(n4, n5, n6) == 0 && field_885_a.canBlockStay(world, n4, n5, n6)) {
				world.setBlockId(n4, n5, n6, field_885_a.blockID);
			}
		}
		return true;
	}
}
