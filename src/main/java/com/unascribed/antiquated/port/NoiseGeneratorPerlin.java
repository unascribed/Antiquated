package com.unascribed.antiquated.port;
import java.util.*;

public class NoiseGeneratorPerlin extends NoiseGenerator {
	private int[] field_1189_d;
	public double field_1188_a;
	public double field_1187_b;
	public double field_1190_c;

	public NoiseGeneratorPerlin() {
		this(new Random());
	}

	public NoiseGeneratorPerlin(final Random random) {
		super();
		field_1189_d = new int[512];
		field_1188_a = random.nextDouble() * 256.0;
		field_1187_b = random.nextDouble() * 256.0;
		field_1190_c = random.nextDouble() * 256.0;
		for (int i = 0; i < 256; ++i) {
			field_1189_d[i] = i;
		}
		for (int j = 0; j < 256; ++j) {
			final int n = random.nextInt(256 - j) + j;
			final int n2 = field_1189_d[j];
			field_1189_d[j] = field_1189_d[n];
			field_1189_d[n] = n2;
			field_1189_d[j + 256] = field_1189_d[j];
		}
	}

	public double func_802_a(final double n, final double n2, final double n3) {
		final double n4 = n + field_1188_a;
		final double n5 = n2 + field_1187_b;
		final double n6 = n3 + field_1190_c;
		int n7 = (int)n4;
		int n8 = (int)n5;
		int n9 = (int)n6;
		if (n4 < n7) {
			--n7;
		}
		if (n5 < n8) {
			--n8;
		}
		if (n6 < n9) {
			--n9;
		}
		final int n10 = n7 & 0xFF;
		final int n11 = n8 & 0xFF;
		final int n12 = n9 & 0xFF;
		final double n13 = n4 - n7;
		final double n14 = n5 - n8;
		final double n15 = n6 - n9;
		final double n16 = n13 * n13 * n13 * (n13 * (n13 * 6.0 - 15.0) + 10.0);
		final double n17 = n14 * n14 * n14 * (n14 * (n14 * 6.0 - 15.0) + 10.0);
		final double n18 = n15 * n15 * n15 * (n15 * (n15 * 6.0 - 15.0) + 10.0);
		final int n19 = field_1189_d[n10] + n11;
		final int n20 = field_1189_d[n19] + n12;
		final int n21 = field_1189_d[n19 + 1] + n12;
		final int n22 = field_1189_d[n10 + 1] + n11;
		final int n23 = field_1189_d[n22] + n12;
		final int n24 = field_1189_d[n22 + 1] + n12;
		return func_804_b(n18, func_804_b(n17, func_804_b(n16, func_803_a(field_1189_d[n20], n13, n14, n15), func_803_a(field_1189_d[n23], n13 - 1.0, n14, n15)), func_804_b(n16, func_803_a(field_1189_d[n21], n13, n14 - 1.0, n15), func_803_a(field_1189_d[n24], n13 - 1.0, n14 - 1.0, n15))), func_804_b(n17, func_804_b(n16, func_803_a(field_1189_d[n20 + 1], n13, n14, n15 - 1.0), func_803_a(field_1189_d[n23 + 1], n13 - 1.0, n14, n15 - 1.0)), func_804_b(n16, func_803_a(field_1189_d[n21 + 1], n13, n14 - 1.0, n15 - 1.0), func_803_a(field_1189_d[n24 + 1], n13 - 1.0, n14 - 1.0, n15 - 1.0))));
	}

	public double func_804_b(final double n, final double n2, final double n3) {
		return n2 + n * (n3 - n2);
	}

	public double func_803_a(final int n, final double n2, final double n3, final double n4) {
		final int n5 = n & 0xF;
		final double n6 = (n5 < 8) ? n2 : n3;
		final double n7 = (n5 < 4) ? n3 : ((n5 == 12 || n5 == 14) ? n2 : n4);
		return (((n5 & 0x1) == 0x0) ? n6 : (-n6)) + (((n5 & 0x2) == 0x0) ? n7 : (-n7));
	}

	public double func_801_a(final double n, final double n2) {
		return func_802_a(n, n2, 0.0);
	}

	public void func_805_a(final double[] array, final double n, final double n2, final double n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9, final double n10) {
		int n11 = 0;
		final double n12 = 1.0 / n10;
		int n13 = -1;
		double func_804_b = 0.0;
		double func_804_b2 = 0.0;
		double func_804_b3 = 0.0;
		double func_804_b4 = 0.0;
		for (int i = 0; i < n4; ++i) {
			final double n14 = (n + i) * n7 + field_1188_a;
			int n15 = (int)n14;
			if (n14 < n15) {
				--n15;
			}
			final int n16 = n15 & 0xFF;
			final double n17 = n14 - n15;
			final double n18 = n17 * n17 * n17 * (n17 * (n17 * 6.0 - 15.0) + 10.0);
			for (int j = 0; j < n6; ++j) {
				final double n19 = (n3 + j) * n9 + field_1190_c;
				int n20 = (int)n19;
				if (n19 < n20) {
					--n20;
				}
				final int n21 = n20 & 0xFF;
				final double n22 = n19 - n20;
				final double n23 = n22 * n22 * n22 * (n22 * (n22 * 6.0 - 15.0) + 10.0);
				for (int k = 0; k < n5; ++k) {
					final double n24 = (n2 + k) * n8 + field_1187_b;
					int n25 = (int)n24;
					if (n24 < n25) {
						--n25;
					}
					final int n26 = n25 & 0xFF;
					final double n27 = n24 - n25;
					final double n28 = n27 * n27 * n27 * (n27 * (n27 * 6.0 - 15.0) + 10.0);
					if (k == 0 || n26 != n13) {
						n13 = n26;
						final int n29 = field_1189_d[n16] + n26;
						final int n30 = field_1189_d[n29] + n21;
						final int n31 = field_1189_d[n29 + 1] + n21;
						final int n32 = field_1189_d[n16 + 1] + n26;
						final int n33 = field_1189_d[n32] + n21;
						final int n34 = field_1189_d[n32 + 1] + n21;
						func_804_b = func_804_b(n18, func_803_a(field_1189_d[n30], n17, n27, n22), func_803_a(field_1189_d[n33], n17 - 1.0, n27, n22));
						func_804_b2 = func_804_b(n18, func_803_a(field_1189_d[n31], n17, n27 - 1.0, n22), func_803_a(field_1189_d[n34], n17 - 1.0, n27 - 1.0, n22));
						func_804_b3 = func_804_b(n18, func_803_a(field_1189_d[n30 + 1], n17, n27, n22 - 1.0), func_803_a(field_1189_d[n33 + 1], n17 - 1.0, n27, n22 - 1.0));
						func_804_b4 = func_804_b(n18, func_803_a(field_1189_d[n31 + 1], n17, n27 - 1.0, n22 - 1.0), func_803_a(field_1189_d[n34 + 1], n17 - 1.0, n27 - 1.0, n22 - 1.0));
					}
					final double func_804_b5 = func_804_b(n23, func_804_b(n28, func_804_b, func_804_b2), func_804_b(n28, func_804_b3, func_804_b4));
					final int n35 = n11++;
					array[n35] += func_804_b5 * n12;
				}
			}
		}
	}
}
