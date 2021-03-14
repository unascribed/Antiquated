package com.unascribed.antiquated.port;
import java.util.*;

public class NoiseGeneratorOctaves extends NoiseGenerator {
	private NoiseGeneratorPerlin[] field_1192_a;
	private int field_1191_b;

	public NoiseGeneratorOctaves(final Random random, final int field_1191_b) {
		super();
		this.field_1191_b = field_1191_b;
		field_1192_a = new NoiseGeneratorPerlin[field_1191_b];
		for (int i = 0; i < field_1191_b; ++i) {
			field_1192_a[i] = new NoiseGeneratorPerlin(random);
		}
	}

	public double func_806_a(final double n, final double n2) {
		double n3 = 0.0;
		double n4 = 1.0;
		for (int i = 0; i < field_1191_b; ++i) {
			n3 += field_1192_a[i].func_801_a(n * n4, n2 * n4) / n4;
			n4 /= 2.0;
		}
		return n3;
	}

	public double[] func_807_a(double[] array, final double n, final double n2, final double n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9) {
		if (array == null) {
			array = new double[n4 * n5 * n6];
		}
		else {
			for (int i = 0; i < array.length; ++i) {
				array[i] = 0.0;
			}
		}
		double n10 = 1.0;
		for (int j = 0; j < field_1191_b; ++j) {
			field_1192_a[j].func_805_a(array, n, n2, n3, n4, n5, n6, n7 * n10, n8 * n10, n9 * n10, n10);
			n10 /= 2.0;
		}
		return array;
	}
}
