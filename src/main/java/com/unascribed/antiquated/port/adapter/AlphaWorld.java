package com.unascribed.antiquated.port.adapter;

import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.Heightmap.Type;

public class AlphaWorld implements AlphaIBlockAccess {

	public final long randomSeed;
	
	public final WorldAccess delegate;
	public final BlockPos.Mutable mut = new BlockPos.Mutable();

	public boolean winterMode;
	
	public AlphaWorld(WorldAccess delegate, long seed) {
		this.delegate = delegate;
		this.randomSeed = seed;
	}

	@Override
	public int getBlockId(int x, int y, int z) {
		return BlockIDConverter.convert(delegate.getBlockState(mut.set(x, y, z)));
	}

	@Override
	public BlockEntity getBlockEntity(int x, int y, int z) {
		return delegate.getBlockEntity(mut.set(x, y, z));
	}

	@Override
	public Material getBlockMaterial(int x, int y, int z) {
		return delegate.getBlockState(mut.set(x, y, z)).getMaterial();
	}

	@Override
	public boolean isSolid(int x, int y, int z) {
		return getBlockMaterial(x, y, z).isSolid();
	}

	public void setBlockId(int x, int y, int z, int id) {
		delegate.setBlockState(mut.set(x, y, z), BlockIDConverter.convert(id), 3);
	}

	public int getHeightValue(int x, int z) {
		return delegate.getTopY(Type.WORLD_SURFACE_WG, x, z);
	}
	
}
