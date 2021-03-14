package com.unascribed.antiquated.port;

import com.unascribed.antiquated.port.adapter.AlphaIBlockAccess;

import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class Pathfinder {
	private AlphaIBlockAccess world;
	private Path path;
	private ManualHashMap table;
	private PathPoint[] planWork;

	public Pathfinder(AlphaIBlockAccess world) {
		super();
		path = new Path();
		table = new ManualHashMap();
		planWork = new PathPoint[32];
		this.world = world;
	}

	public PathPlan createPlanTo(Entity subject, Entity target, float maxDistance) {
		return createPlanTo(subject, target.getPos().x, target.getPos().y, target.getPos().z, maxDistance);
	}

	public PathPlan createPlanTo(Entity subject, int targetX, int targetY, int targetZ, float maxDistance) {
		return createPlanTo(subject, targetX + 0.5f, targetY + 0.5f, targetZ + 0.5f, maxDistance);
	}

	private PathPlan createPlanTo(Entity subject, double targetX, double targetY, double targetZ, float maxDistance) {
		path.func_1038_a();
		table.func_1058_a();
		return func_1129_a(subject,
				openPoint(MathHelper.floor(subject.getBoundingBox().minX),
						MathHelper.floor(subject.getBoundingBox().minY),
						MathHelper.floor(subject.getBoundingBox().minZ)),
				openPoint(MathHelper.floor(targetX - subject.getWidth() / 2.0f),
						MathHelper.floor(targetY), MathHelper.floor(targetZ - subject.getWidth() / 2.0f)),
				new PathPoint(MathHelper.floor(subject.getWidth() + 1.0f),
						MathHelper.floor(subject.getHeight() + 1.0f),
						MathHelper.floor(subject.getWidth() + 1.0f)), maxDistance);
	}

	private PathPlan func_1129_a(final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final PathPoint pathPoint3, final float n) {
		pathPoint.field_1713_f = 0.0f;
		pathPoint.field_1712_g = pathPoint.func_1180_a(pathPoint2);
		pathPoint.field_1711_h = pathPoint.field_1712_g;
		path.func_1038_a();
		path.func_1034_a(pathPoint);
		PathPoint pathPoint4 = pathPoint;
		while (!path.func_1039_c()) {
			final PathPoint func_1036_b = path.func_1036_b();
			if (func_1036_b.field_1715_d == pathPoint2.field_1715_d) {
				return createPlan(pathPoint, pathPoint2);
			}
			if (func_1036_b.func_1180_a(pathPoint2) < pathPoint4.func_1180_a(pathPoint2)) {
				pathPoint4 = func_1036_b;
			}
			func_1036_b.field_1709_j = true;
			for (int func_1133_b = func_1133_b(entity, func_1036_b, pathPoint3, pathPoint2, n), i = 0; i < func_1133_b; ++i) {
				final PathPoint pathPoint5 = planWork[i];
				final float field_1713_f = func_1036_b.field_1713_f + func_1036_b.func_1180_a(pathPoint5);
				if (!pathPoint5.func_1179_a() || field_1713_f < pathPoint5.field_1713_f) {
					pathPoint5.field_1710_i = func_1036_b;
					pathPoint5.field_1713_f = field_1713_f;
					pathPoint5.field_1712_g = pathPoint5.func_1180_a(pathPoint2);
					if (pathPoint5.func_1179_a()) {
						path.func_1035_a(pathPoint5, pathPoint5.field_1713_f + pathPoint5.field_1712_g);
					}
					else {
						pathPoint5.field_1711_h = pathPoint5.field_1713_f + pathPoint5.field_1712_g;
						path.func_1034_a(pathPoint5);
					}
				}
			}
		}
		if (pathPoint4 == pathPoint) {
			return null;
		}
		return createPlan(pathPoint, pathPoint4);
	}

	private int func_1133_b(final Entity entity, final PathPoint pathPoint, final PathPoint pathPoint2, final PathPoint pathPoint3, final float n) {
		int n2 = 0;
		int n3 = 0;
		if (getVerticalOffset(entity, pathPoint.x, pathPoint.y + 1, pathPoint.z, pathPoint2) > 0) {
			n3 = 1;
		}
		final PathPoint func_1135_a = getSafePoint(entity, pathPoint.x, pathPoint.y, pathPoint.z + 1, pathPoint2, n3);
		final PathPoint func_1135_a2 = getSafePoint(entity, pathPoint.x - 1, pathPoint.y, pathPoint.z, pathPoint2, n3);
		final PathPoint func_1135_a3 = getSafePoint(entity, pathPoint.x + 1, pathPoint.y, pathPoint.z, pathPoint2, n3);
		final PathPoint func_1135_a4 = getSafePoint(entity, pathPoint.x, pathPoint.y, pathPoint.z - 1, pathPoint2, n3);
		if (func_1135_a != null && !func_1135_a.field_1709_j && func_1135_a.func_1180_a(pathPoint3) < n) {
			planWork[n2++] = func_1135_a;
		}
		if (func_1135_a2 != null && !func_1135_a2.field_1709_j && func_1135_a2.func_1180_a(pathPoint3) < n) {
			planWork[n2++] = func_1135_a2;
		}
		if (func_1135_a3 != null && !func_1135_a3.field_1709_j && func_1135_a3.func_1180_a(pathPoint3) < n) {
			planWork[n2++] = func_1135_a3;
		}
		if (func_1135_a4 != null && !func_1135_a4.field_1709_j && func_1135_a4.func_1180_a(pathPoint3) < n) {
			planWork[n2++] = func_1135_a4;
		}
		return n2;
	}

	private PathPoint getSafePoint(final Entity entity, final int n, int n2, final int n3, final PathPoint pathPoint, final int n4) {
		PathPoint pathPoint2 = null;
		if (getVerticalOffset(entity, n, n2, n3, pathPoint) > 0) {
			pathPoint2 = openPoint(n, n2, n3);
		}
		if (pathPoint2 == null && getVerticalOffset(entity, n, n2 + n4, n3, pathPoint) > 0) {
			pathPoint2 = openPoint(n, n2 + n4, n3);
			n2 += n4;
		}
		if (pathPoint2 != null) {
			int n5 = 0;
			int func_1132_a;
			while (n2 > 0 && (func_1132_a = getVerticalOffset(entity, n, n2 - 1, n3, pathPoint)) > 0) {
				if (func_1132_a < 0) {
					return null;
				}
				if (++n5 >= 4) {
					return null;
				}
				--n2;
			}
			if (n2 > 0) {
				pathPoint2 = openPoint(n, n2, n3);
			}
		}
		return pathPoint2;
	}

	private final PathPoint openPoint(int x, int y, int z) {
		int packed = x | y << 10 | z << 20;
		PathPoint pathPoint = (PathPoint)table.lookup(packed);
		if (pathPoint == null) {
			pathPoint = new PathPoint(x, y, z);
			table.put(packed, pathPoint);
		}
		return pathPoint;
	}

	private int getVerticalOffset(final Entity entity, final int n, final int n2, final int n3, final PathPoint pathPoint) {
		for (int i = n; i < n + pathPoint.x; ++i) {
			for (int j = n2; j < n2 + pathPoint.y; ++j) {
				for (int k = n3; k < n3 + pathPoint.z; ++k) {
					Material material = world.getBlockMaterial(n, n2, n3);
					if (material.blocksMovement()) {
						return 0;
					}
					if (material.isLiquid()) {
						return -1;
					}
				}
			}
		}
		return 1;
	}

	private PathPlan createPlan(final PathPoint pathPoint, final PathPoint pathPoint2) {
		int n = 1;
		for (PathPoint field_1710_i = pathPoint2; field_1710_i.field_1710_i != null; field_1710_i = field_1710_i.field_1710_i) {
			++n;
		}
		final PathPoint[] array = new PathPoint[n];
		PathPoint field_1710_i2 = pathPoint2;
		array[--n] = field_1710_i2;
		while (field_1710_i2.field_1710_i != null) {
			field_1710_i2 = field_1710_i2.field_1710_i;
			array[--n] = field_1710_i2;
		}
		return new PathPlan(array);
	}
}
