package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Material;
import net.minecraft.util.math.MathHelper;

public class WorldGenClay extends WorldGenerator {
	private int field_867_a;
	private int field_866_b;

	public WorldGenClay(final int field_866_b) {
		super();
		field_867_a = AlphaBlock.clay.blockID;
		this.field_866_b = field_866_b;
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		if (world.getBlockMaterial(n, n2, n3) != Material.WATER) {
			return false;
		}
		final float n4 = random.nextFloat() * 3.1415927f;
		final double n5 = n + 8 + MathHelper.sin(n4) * field_866_b / 8.0f;
		final double n6 = n + 8 - MathHelper.sin(n4) * field_866_b / 8.0f;
		final double n7 = n3 + 8 + MathHelper.cos(n4) * field_866_b / 8.0f;
		final double n8 = n3 + 8 - MathHelper.cos(n4) * field_866_b / 8.0f;
		final double n9 = n2 + random.nextInt(3) + 2;
		final double n10 = n2 + random.nextInt(3) + 2;
		for (int i = 0; i <= field_866_b; ++i) {
			final double n11 = n5 + (n6 - n5) * i / field_866_b;
			final double n12 = n9 + (n10 - n9) * i / field_866_b;
			final double n13 = n7 + (n8 - n7) * i / field_866_b;
			final double n14 = random.nextDouble() * field_866_b / 16.0;
			final double n15 = (MathHelper.sin(i * 3.1415927f / field_866_b) + 1.0f) * n14 + 1.0;
			final double n16 = (MathHelper.sin(i * 3.1415927f / field_866_b) + 1.0f) * n14 + 1.0;
			for (int j = (int)(n11 - n15 / 2.0); j <= (int)(n11 + n15 / 2.0); ++j) {
				for (int k = (int)(n12 - n16 / 2.0); k <= (int)(n12 + n16 / 2.0); ++k) {
					for (int l = (int)(n13 - n15 / 2.0); l <= (int)(n13 + n15 / 2.0); ++l) {
						final double n17 = (j + 0.5 - n11) / (n15 / 2.0);
						final double n18 = (k + 0.5 - n12) / (n16 / 2.0);
						final double n19 = (l + 0.5 - n13) / (n15 / 2.0);
						if (n17 * n17 + n18 * n18 + n19 * n19 < 1.0 && world.getBlockId(j, k, l) == AlphaBlock.sand.blockID) {
							world.setBlockId(j, k, l, field_867_a);
						}
					}
				}
			}
		}
		return true;
	}
}
