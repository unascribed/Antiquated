package com.unascribed.antiquated.port;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PathPlan {
	private final PathPoint[] points;
	public final int pathLength;
	private int pathIndex;

	public PathPlan(PathPoint... points) {
		this.points = points;
		pathLength = points.length;
	}

	public void next() {
		++pathIndex;
	}

	public boolean isFinished() {
		return pathIndex >= points.length;
	}

	public Vec3d getPosition(Entity entity) {
		return new Vec3d(points[pathIndex].x + (int)(entity.getWidth() + 1.0f) * 0.5, points[pathIndex].y, points[pathIndex].z + (int)(entity.getWidth() + 1.0f) * 0.5);
	}
}
