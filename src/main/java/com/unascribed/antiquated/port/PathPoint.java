package com.unascribed.antiquated.port;

import net.minecraft.util.math.MathHelper;

public class PathPoint {
	public final int x;
	public final int y;
	public final int z;
	public final int field_1715_d;
	int field_1714_e;
	float field_1713_f;
	float field_1712_g;
	float field_1711_h;
	PathPoint field_1710_i;
	public boolean field_1709_j;

	public PathPoint(final int field_1718_a, final int field_1717_b, final int field_1716_c) {
		super();
		field_1714_e = -1;
		field_1709_j = false;
		this.x = field_1718_a;
		this.y = field_1717_b;
		this.z = field_1716_c;
		field_1715_d = (field_1718_a | field_1717_b << 10 | field_1716_c << 20);
	}

	public float func_1180_a(final PathPoint pathPoint) {
		final float n = pathPoint.x - x;
		final float n2 = pathPoint.y - y;
		final float n3 = pathPoint.z - z;
		return MathHelper.sqrt(n * n + n2 * n2 + n3 * n3);
	}

	@Override
	public boolean equals(final Object o) {
		return ((PathPoint)o).field_1715_d == field_1715_d;
	}

	@Override
	public int hashCode() {
		return field_1715_d;
	}

	public boolean func_1179_a() {
		return field_1714_e >= 0;
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + z;
	}
}
