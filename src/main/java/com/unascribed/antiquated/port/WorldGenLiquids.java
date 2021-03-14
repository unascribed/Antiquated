package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class WorldGenLiquids extends WorldGenerator {
	private int field_865_a;

	public WorldGenLiquids(final int field_865_a) {
		super();
		this.field_865_a = field_865_a;
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		if (world.getBlockId(n, n2 + 1, n3) != AlphaBlock.stone.blockID) {
			return false;
		}
		if (world.getBlockId(n, n2 - 1, n3) != AlphaBlock.stone.blockID) {
			return false;
		}
		if (world.getBlockId(n, n2, n3) != 0 && world.getBlockId(n, n2, n3) != AlphaBlock.stone.blockID) {
			return false;
		}
		int n4 = 0;
		if (world.getBlockId(n - 1, n2, n3) == AlphaBlock.stone.blockID) {
			++n4;
		}
		if (world.getBlockId(n + 1, n2, n3) == AlphaBlock.stone.blockID) {
			++n4;
		}
		if (world.getBlockId(n, n2, n3 - 1) == AlphaBlock.stone.blockID) {
			++n4;
		}
		if (world.getBlockId(n, n2, n3 + 1) == AlphaBlock.stone.blockID) {
			++n4;
		}
		int n5 = 0;
		if (world.getBlockId(n - 1, n2, n3) == 0) {
			++n5;
		}
		if (world.getBlockId(n + 1, n2, n3) == 0) {
			++n5;
		}
		if (world.getBlockId(n, n2, n3 - 1) == 0) {
			++n5;
		}
		if (world.getBlockId(n, n2, n3 + 1) == 0) {
			++n5;
		}
		if (n4 == 3 && n5 == 1) {
			world.setBlockId(n, n2, n3, field_865_a);
		}
		return true;
	}
}
