package com.unascribed.antiquated.port;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PathEntity
{

    public PathEntity(PathPoint apathpoint[])
    {
        points = apathpoint;
        pathLength = apathpoint.length;
    }

    public void incrementPathIndex()
    {
        pathIndex++;
    }

    public boolean isFinished()
    {
        return pathIndex >= points.length;
    }

    public Vec3d getPosition(Entity entity)
    {
        double d = points[pathIndex].xCoord + (int)(entity.getWidth() + 1.0F) * 0.5D;
        double d1 = points[pathIndex].yCoord;
        double d2 = points[pathIndex].zCoord + (int)(entity.getWidth() + 1.0F) * 0.5D;
        return new Vec3d(d, d1, d2);
    }

    private final PathPoint points[];
    public final int pathLength;
    private int pathIndex;
}
