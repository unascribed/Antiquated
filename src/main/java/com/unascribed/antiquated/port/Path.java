package com.unascribed.antiquated.port;
public class Path {
	private PathPoint[] field_1556_a;
	private int field_1555_b;

	public Path() {
		super();
		field_1556_a = new PathPoint[1024];
		field_1555_b = 0;
	}

	public PathPoint func_1034_a(final PathPoint pathPoint) {
		if (pathPoint.field_1714_e >= 0) {
			throw new IllegalStateException("OW KNOWS!");
		}
		if (field_1555_b == field_1556_a.length) {
			final PathPoint[] field_1556_a = new PathPoint[field_1555_b << 1];
			System.arraycopy(this.field_1556_a, 0, field_1556_a, 0, field_1555_b);
			this.field_1556_a = field_1556_a;
		}
		field_1556_a[field_1555_b] = pathPoint;
		pathPoint.field_1714_e = field_1555_b;
		func_1033_a(field_1555_b++);
		return pathPoint;
	}

	public void func_1038_a() {
		field_1555_b = 0;
	}

	public PathPoint func_1036_b() {
		final PathPoint pathPoint = field_1556_a[0];
		final PathPoint[] field_1556_a = this.field_1556_a;
		final int n = 0;
		final PathPoint[] field_1556_a2 = this.field_1556_a;
		final int field_1555_b = this.field_1555_b - 1;
		this.field_1555_b = field_1555_b;
		field_1556_a[n] = field_1556_a2[field_1555_b];
		this.field_1556_a[this.field_1555_b] = null;
		if (this.field_1555_b > 0) {
			func_1037_b(0);
		}
		pathPoint.field_1714_e = -1;
		return pathPoint;
	}

	public void func_1035_a(final PathPoint pathPoint, final float field_1711_h) {
		final float field_1711_h2 = pathPoint.field_1711_h;
		pathPoint.field_1711_h = field_1711_h;
		if (field_1711_h < field_1711_h2) {
			func_1033_a(pathPoint.field_1714_e);
		}
		else {
			func_1037_b(pathPoint.field_1714_e);
		}
	}

	private void func_1033_a(int i) {
		final PathPoint pathPoint = field_1556_a[i];
		final float field_1711_h = pathPoint.field_1711_h;
		while (i > 0) {
			final int n = i - 1 >> 1;
			final PathPoint pathPoint2 = field_1556_a[n];
			if (field_1711_h >= pathPoint2.field_1711_h) {
				break;
			}
			field_1556_a[i] = pathPoint2;
			pathPoint2.field_1714_e = i;
			i = n;
		}
		field_1556_a[i] = pathPoint;
		pathPoint.field_1714_e = i;
	}

	private void func_1037_b(int field_1714_e) {
		final PathPoint pathPoint = field_1556_a[field_1714_e];
		final float field_1711_h = pathPoint.field_1711_h;
		while (true) {
			final int n = 1 + (field_1714_e << 1);
			final int n2 = n + 1;
			if (n >= field_1555_b) {
				break;
			}
			final PathPoint pathPoint2 = field_1556_a[n];
			final float field_1711_h2 = pathPoint2.field_1711_h;
			PathPoint pathPoint3;
			float field_1711_h3;
			if (n2 >= field_1555_b) {
				pathPoint3 = null;
				field_1711_h3 = Float.POSITIVE_INFINITY;
			}
			else {
				pathPoint3 = field_1556_a[n2];
				field_1711_h3 = pathPoint3.field_1711_h;
			}
			if (field_1711_h2 < field_1711_h3) {
				if (field_1711_h2 >= field_1711_h) {
					break;
				}
				field_1556_a[field_1714_e] = pathPoint2;
				pathPoint2.field_1714_e = field_1714_e;
				field_1714_e = n;
			}
			else {
				if (field_1711_h3 >= field_1711_h) {
					break;
				}
				field_1556_a[field_1714_e] = pathPoint3;
				pathPoint3.field_1714_e = field_1714_e;
				field_1714_e = n2;
			}
		}
		field_1556_a[field_1714_e] = pathPoint;
		pathPoint.field_1714_e = field_1714_e;
	}

	public boolean func_1039_c() {
		return field_1555_b == 0;
	}
}
