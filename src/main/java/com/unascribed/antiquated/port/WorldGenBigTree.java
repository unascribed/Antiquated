package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.util.math.MathHelper;

public class WorldGenBigTree extends WorldGenerator {
	static final byte[] field_882_a;
	Random field_881_b;
	AlphaWorld field_880_c;
	int[] field_879_d;
	int field_878_e;
	int field_877_f;
	double field_876_g;
	double field_875_h;
	double field_874_i;
	double field_873_j;
	double field_872_k;
	int field_871_l;
	int field_870_m;
	int field_869_n;
	int[][] field_868_o;

	public WorldGenBigTree() {
		super();
		field_881_b = new Random();
		field_879_d = new int[] { 0, 0, 0 };
		field_878_e = 0;
		field_876_g = 0.618;
		field_875_h = 1.0;
		field_874_i = 0.381;
		field_873_j = 1.0;
		field_872_k = 1.0;
		field_871_l = 1;
		field_870_m = 12;
		field_869_n = 4;
	}

	void func_521_a() {
		field_877_f = (int)(field_878_e * field_876_g);
		if (field_877_f >= field_878_e) {
			field_877_f = field_878_e - 1;
		}
		int n = (int)(1.382 + Math.pow(field_872_k * field_878_e / 13.0, 2.0));
		if (n < 1) {
			n = 1;
		}
		final int[][] array = new int[n * field_878_e][4];
		int n2 = field_879_d[1] + field_878_e - field_869_n;
		int n3 = 1;
		final int n4 = field_879_d[1] + field_877_f;
		int i = n2 - field_879_d[1];
		array[0][0] = field_879_d[0];
		array[0][1] = n2;
		array[0][2] = field_879_d[2];
		array[0][3] = n4;
		--n2;
		while (i >= 0) {
			int j = 0;
			final float func_528_a = func_528_a(i);
			if (func_528_a < 0.0f) {
				--n2;
				--i;
			}
			else {
				final double n5 = 0.5;
				while (j < n) {
					final double n6 = field_873_j * (func_528_a * (field_881_b.nextFloat() + 0.328));
					final double n7 = field_881_b.nextFloat() * 2.0 * 3.14159;
					final int n8 = (int)(n6 * Math.sin(n7) + field_879_d[0] + n5);
					final int n9 = (int)(n6 * Math.cos(n7) + field_879_d[2] + n5);
					final int[] array2 = { n8, n2, n9 };
					if (func_524_a(array2, new int[] { n8, n2 + field_869_n, n9 }) == -1) {
						final int[] array3 = { field_879_d[0], field_879_d[1], field_879_d[2] };
						final double n10 = Math.sqrt(Math.pow(Math.abs(field_879_d[0] - array2[0]), 2.0) + Math.pow(Math.abs(field_879_d[2] - array2[2]), 2.0)) * field_874_i;
						if (array2[1] - n10 > n4) {
							array3[1] = n4;
						}
						else {
							array3[1] = (int)(array2[1] - n10);
						}
						if (func_524_a(array3, array2) == -1) {
							array[n3][0] = n8;
							array[n3][1] = n2;
							array[n3][2] = n9;
							array[n3][3] = array3[1];
							++n3;
						}
					}
					++j;
				}
				--n2;
				--i;
			}
		}
		System.arraycopy(array, 0, field_868_o = new int[n3][4], 0, n3);
	}

	void func_523_a(final int n, final int n2, final int n3, final float n4, final byte b, final int n5) {
		final int n6 = (int)(n4 + 0.618);
		final byte b2 = WorldGenBigTree.field_882_a[b];
		final byte b3 = WorldGenBigTree.field_882_a[b + 3];
		final int[] array = { n, n2, n3 };
		final int[] array2 = { 0, 0, 0 };
		int i = -n6;
		array2[b] = array[b];
		while (i <= n6) {
			array2[b2] = array[b2] + i;
			for (int j = -n6; j <= n6; ++j) {
				if (Math.sqrt(Math.pow(Math.abs(i) + 0.5, 2.0) + Math.pow(Math.abs(j) + 0.5, 2.0)) <= n4) {
					array2[b3] = array[b3] + j;
					final int func_600_a = field_880_c.getBlockId(array2[0], array2[1], array2[2]);
					if (func_600_a == 0 || func_600_a == 18) {
						field_880_c.setBlockId(array2[0], array2[1], array2[2], n5);
					}
				}
			}
			++i;
		}
	}

	float func_528_a(final int n) {
		if (n < field_878_e * 0.3) {
			return -1.618f;
		}
		final float n2 = field_878_e / 2.0f;
		final float n3 = field_878_e / 2.0f - n;
		float n4;
		if (n3 == 0.0f) {
			n4 = n2;
		}
		else if (Math.abs(n3) >= n2) {
			n4 = 0.0f;
		}
		else {
			n4 = (float)Math.sqrt(Math.pow(Math.abs(n2), 2.0) - Math.pow(Math.abs(n3), 2.0));
		}
		return n4 * 0.5f;
	}

	float func_526_b(final int n) {
		if (n < 0 || n >= field_869_n) {
			return -1.0f;
		}
		if (n == 0 || n == field_869_n - 1) {
			return 2.0f;
		}
		return 3.0f;
	}

	void func_520_a(final int n, final int n2, final int n3) {
		for (int i = n2; i < n2 + field_869_n; ++i) {
			func_523_a(n, i, n3, func_526_b(i - n2), (byte)1, 18);
		}
	}

	void func_522_a(final int[] array, final int[] array2, final int n) {
		final int[] array3 = { 0, 0, 0 };
		int i = 0;
		int n2 = 0;
		while (i < 3) {
			array3[i] = array2[i] - array[i];
			if (Math.abs(array3[i]) > Math.abs(array3[n2])) {
				n2 = i;
			}
			i = (byte)(i + 1);
		}
		if (array3[n2] == 0) {
			return;
		}
		final byte b = WorldGenBigTree.field_882_a[n2];
		final byte b2 = WorldGenBigTree.field_882_a[n2 + 3];
		int n3;
		if (array3[n2] > 0) {
			n3 = 1;
		}
		else {
			n3 = -1;
		}
		final double n4 = array3[b] / array3[n2];
		final double n5 = array3[b2] / array3[n2];
		final int[] array4 = { 0, 0, 0 };
		for (int j = 0; j != array3[n2] + n3; j += n3) {
			array4[n2] = MathHelper.floor(array[n2] + j + 0.5);
			array4[b] = MathHelper.floor(array[b] + j * n4 + 0.5);
			array4[b2] = MathHelper.floor(array[b2] + j * n5 + 0.5);
			field_880_c.setBlockId(array4[0], array4[1], array4[2], n);
		}
	}

	void func_518_b() {
		for (int i = 0; i < field_868_o.length; ++i) {
			func_520_a(field_868_o[i][0], field_868_o[i][1], field_868_o[i][2]);
		}
	}

	boolean func_527_c(final int n) {
		return n >= field_878_e * 0.2;
	}

	void func_529_c() {
		final int n = field_879_d[0];
		final int n2 = field_879_d[1];
		final int n3 = field_879_d[1] + field_877_f;
		final int n4 = field_879_d[2];
		final int[] array = { n, n2, n4 };
		final int[] array2 = { n, n3, n4 };
		func_522_a(array, array2, 17);
		if (field_871_l == 2) {
			final int[] array3 = array;
			final int n5 = 0;
			++array3[n5];
			final int[] array4 = array2;
			final int n6 = 0;
			++array4[n6];
			func_522_a(array, array2, 17);
			final int[] array5 = array;
			final int n7 = 2;
			++array5[n7];
			final int[] array6 = array2;
			final int n8 = 2;
			++array6[n8];
			func_522_a(array, array2, 17);
			final int[] array7 = array;
			final int n9 = 0;
			--array7[n9];
			final int[] array8 = array2;
			final int n10 = 0;
			--array8[n10];
			func_522_a(array, array2, 17);
		}
	}

	void func_525_d() {
		int i = 0;
		final int length = field_868_o.length;
		final int[] array = { field_879_d[0], field_879_d[1], field_879_d[2] };
		while (i < length) {
			final int[] array2 = field_868_o[i];
			final int[] array3 = { array2[0], array2[1], array2[2] };
			array[1] = array2[3];
			if (func_527_c(array[1] - field_879_d[1])) {
				func_522_a(array, array3, 17);
			}
			++i;
		}
	}

	int func_524_a(final int[] array, final int[] array2) {
		final int[] array3 = { 0, 0, 0 };
		int i = 0;
		int n = 0;
		while (i < 3) {
			array3[i] = array2[i] - array[i];
			if (Math.abs(array3[i]) > Math.abs(array3[n])) {
				n = i;
			}
			i = (byte)(i + 1);
		}
		if (array3[n] == 0) {
			return -1;
		}
		final byte b = WorldGenBigTree.field_882_a[n];
		final byte b2 = WorldGenBigTree.field_882_a[n + 3];
		int n2;
		if (array3[n] > 0) {
			n2 = 1;
		}
		else {
			n2 = -1;
		}
		final double n3 = array3[b] / array3[n];
		final double n4 = array3[b2] / array3[n];
		final int[] array4 = { 0, 0, 0 };
		int j;
		int n5;
		for (j = 0, n5 = array3[n] + n2; j != n5; j += n2) {
			array4[n] = array[n] + j;
			array4[b] = (int)(array[b] + j * n3);
			array4[b2] = (int)(array[b2] + j * n4);
			final int func_600_a = field_880_c.getBlockId(array4[0], array4[1], array4[2]);
			if (func_600_a != 0 && func_600_a != 18) {
				break;
			}
		}
		if (j == n5) {
			return -1;
		}
		return Math.abs(j);
	}

	boolean func_519_e() {
		final int[] array = { field_879_d[0], field_879_d[1], field_879_d[2] };
		final int[] array2 = { field_879_d[0], field_879_d[1] + field_878_e - 1, field_879_d[2] };
		final int func_600_a = field_880_c.getBlockId(field_879_d[0], field_879_d[1] - 1, field_879_d[2]);
		if (func_600_a != 2 && func_600_a != 3) {
			return false;
		}
		final int func_524_a = func_524_a(array, array2);
		if (func_524_a == -1) {
			return true;
		}
		if (func_524_a < 6) {
			return false;
		}
		field_878_e = func_524_a;
		return true;
	}

	@Override
	public void func_517_a(final double n, final double field_873_j, final double field_872_k) {
		field_870_m = (int)(n * 12.0);
		if (n > 0.5) {
			field_869_n = 5;
		}
		this.field_873_j = field_873_j;
		this.field_872_k = field_872_k;
	}

	public boolean populate(final AlphaWorld field_880_c, final Random random, final int n, final int n2, final int n3) {
		this.field_880_c = field_880_c;
		field_881_b.setSeed(random.nextLong());
		field_879_d[0] = n;
		field_879_d[1] = n2;
		field_879_d[2] = n3;
		if (field_878_e == 0) {
			field_878_e = 5 + field_881_b.nextInt(field_870_m);
		}
		if (!func_519_e()) {
			return false;
		}
		func_521_a();
		func_518_b();
		func_529_c();
		func_525_d();
		return true;
	}

	static {
		field_882_a = new byte[] { 2, 0, 0, 1, 2, 1 };
	}
}
