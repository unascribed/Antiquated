package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.util.math.MathHelper;

public class MapGenCaves extends MapGenBase {
	public MapGenCaves() {
		super();
	}

	protected void func_870_a(final int n, final int n2, final byte[] array, final double n3, final double n4, final double n5) {
		func_869_a(n, n2, array, n3, n4, n5, 1.0f + field_1305_b.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
	}

	protected void func_869_a(final int n, final int n2, final byte[] array, double n3, double n4, double n5, final float n6, float n7, float n8, int i, int n9, final double n10) {
		final double n11 = n * 16 + 8;
		final double n12 = n2 * 16 + 8;
		float n13 = 0.0f;
		float n14 = 0.0f;
		final Random random = new Random(field_1305_b.nextLong());
		if (n9 <= 0) {
			final int n15 = field_1306_a * 16 - 16;
			n9 = n15 - random.nextInt(n15 / 4);
		}
		boolean b = false;
		if (i == -1) {
			i = n9 / 2;
			b = true;
		}
		final int n16 = random.nextInt(n9 / 2) + n9 / 4;
		final boolean b2 = random.nextInt(6) == 0;
		while (i < n9) {
			final double n17 = 1.5 + MathHelper.sin(i * 3.1415927f / n9) * n6 * 1.0f;
			final double n18 = n17 * n10;
			final float func_1114_b = MathHelper.cos(n8);
			final float func_1106_a = MathHelper.sin(n8);
			n3 += MathHelper.cos(n7) * func_1114_b;
			n4 += func_1106_a;
			n5 += MathHelper.sin(n7) * func_1114_b;
			if (b2) {
				n8 *= 0.92f;
			}
			else {
				n8 *= 0.7f;
			}
			n8 += n14 * 0.1f;
			n7 += n13 * 0.1f;
			final float n19 = n14 * 0.9f;
			final float n20 = n13 * 0.75f;
			n14 = n19 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
			n13 = n20 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
			if (!b && i == n16 && n6 > 1.0f) {
				func_869_a(n, n2, array, n3, n4, n5, random.nextFloat() * 0.5f + 0.5f, n7 - 1.5707964f, n8 / 3.0f, i, n9, 1.0);
				func_869_a(n, n2, array, n3, n4, n5, random.nextFloat() * 0.5f + 0.5f, n7 + 1.5707964f, n8 / 3.0f, i, n9, 1.0);
				return;
			}
			if (b || random.nextInt(4) != 0) {
				final double n21 = n3 - n11;
				final double n22 = n5 - n12;
				final double n23 = n9 - i;
				final double n24 = n6 + 2.0f + 16.0f;
				if (n21 * n21 + n22 * n22 - n23 * n23 > n24 * n24) {
					return;
				}
				if (n3 >= n11 - 16.0 - n17 * 2.0 && n5 >= n12 - 16.0 - n17 * 2.0 && n3 <= n11 + 16.0 + n17 * 2.0) {
					if (n5 <= n12 + 16.0 + n17 * 2.0) {
						int n25 = MathHelper.floor(n3 - n17) - n * 16 - 1;
						int n26 = MathHelper.floor(n3 + n17) - n * 16 + 1;
						int n27 = MathHelper.floor(n4 - n18) - 1;
						int n28 = MathHelper.floor(n4 + n18) + 1;
						int n29 = MathHelper.floor(n5 - n17) - n2 * 16 - 1;
						int n30 = MathHelper.floor(n5 + n17) - n2 * 16 + 1;
						if (n25 < 0) {
							n25 = 0;
						}
						if (n26 > 16) {
							n26 = 16;
						}
						if (n27 < 1) {
							n27 = 1;
						}
						if (n28 > 120) {
							n28 = 120;
						}
						if (n29 < 0) {
							n29 = 0;
						}
						if (n30 > 16) {
							n30 = 16;
						}
						int n31 = 0;
						for (int n32 = n25; n31 == 0 && n32 < n26; ++n32) {
							for (int n33 = n29; n31 == 0 && n33 < n30; ++n33) {
								for (int n34 = n28 + 1; n31 == 0 && n34 >= n27 - 1; --n34) {
									final int n35 = (n32 * 16 + n33) * 128 + n34;
									if (n34 >= 0) {
										if (n34 < 128) {
											if (array[n35] == AlphaBlock.waterStill.blockID || array[n35] == AlphaBlock.waterMoving.blockID) {
												n31 = 1;
											}
											if (n34 != n27 - 1 && n32 != n25 && n32 != n26 - 1 && n33 != n29 && n33 != n30 - 1) {
												n34 = n27;
											}
										}
									}
								}
							}
						}
						if (n31 == 0) {
							for (int j = n25; j < n26; ++j) {
								final double n36 = (j + n * 16 + 0.5 - n3) / n17;
								for (int k = n29; k < n30; ++k) {
									final double n37 = (k + n2 * 16 + 0.5 - n5) / n17;
									int n38 = (j * 16 + k) * 128 + n28;
									boolean b3 = false;
									for (int l = n28 - 1; l >= n27; --l) {
										final double n39 = (l + 0.5 - n4) / n18;
										if (n39 > -0.7 && n36 * n36 + n39 * n39 + n37 * n37 < 1.0) {
											final byte b4 = array[n38];
											if (b4 == AlphaBlock.grass.blockID) {
												b3 = true;
											}
											if (b4 == AlphaBlock.stone.blockID || b4 == AlphaBlock.dirt.blockID || b4 == AlphaBlock.grass.blockID) {
												if (l < 10) {
													array[n38] = (byte)AlphaBlock.lavaStill.blockID;
												}
												else {
													array[n38] = 0;
													if (b3 && array[n38 - 1] == AlphaBlock.dirt.blockID) {
														array[n38 - 1] = (byte)AlphaBlock.grass.blockID;
													}
												}
											}
										}
										--n38;
									}
								}
							}
							if (b) {
								break;
							}
						}
					}
				}
			}
			++i;
		}
	}

	protected void func_868_a(final AlphaWorld world, final int n, final int n2, final int n3, final int n4, final byte[] array) {
		int nextInt = field_1305_b.nextInt(field_1305_b.nextInt(field_1305_b.nextInt(40) + 1) + 1);
		if (field_1305_b.nextInt(15) != 0) {
			nextInt = 0;
		}
		for (int i = 0; i < nextInt; ++i) {
			final double n5 = n * 16 + field_1305_b.nextInt(16);
			final double n6 = field_1305_b.nextInt(field_1305_b.nextInt(120) + 8);
			final double n7 = n2 * 16 + field_1305_b.nextInt(16);
			int n8 = 1;
			if (field_1305_b.nextInt(4) == 0) {
				func_870_a(n3, n4, array, n5, n6, n7);
				n8 += field_1305_b.nextInt(4);
			}
			for (int j = 0; j < n8; ++j) {
				func_869_a(n3, n4, array, n5, n6, n7, field_1305_b.nextFloat() * 2.0f + field_1305_b.nextFloat(), field_1305_b.nextFloat() * 3.1415927f * 2.0f, (field_1305_b.nextFloat() - 0.5f) * 2.0f / 8.0f, 0, 0, 1.0);
			}
		}
	}
}
