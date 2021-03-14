package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaWorld;

public class MapGenBase {
	protected int field_1306_a;
	protected Random field_1305_b;

	public MapGenBase() {
		super();
		field_1306_a = 8;
		field_1305_b = new Random();
	}

	public void func_867_a(final Alpha112ChunkGenerator chunkProviderGenerate, final AlphaWorld world, final int n, final int n2, final byte[] array) {
		final int field_1306_a = this.field_1306_a;
		field_1305_b.setSeed(world.randomSeed);
		final long n3 = field_1305_b.nextLong() / 2L * 2L + 1L;
		final long n4 = field_1305_b.nextLong() / 2L * 2L + 1L;
		for (int i = n - field_1306_a; i <= n + field_1306_a; ++i) {
			for (int j = n2 - field_1306_a; j <= n2 + field_1306_a; ++j) {
				field_1305_b.setSeed(i * n3 + j * n4 ^ world.randomSeed);
				func_868_a(world, i, j, n, n2, array);
			}
		}
	}

	protected void func_868_a(final AlphaWorld world, final int n, final int n2, final int n3, final int n4, final byte[] array) {
	}
}
