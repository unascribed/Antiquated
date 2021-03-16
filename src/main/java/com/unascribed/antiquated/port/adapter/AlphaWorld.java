package com.unascribed.antiquated.port.adapter;

import java.util.Random;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.init.ABlocks;

import com.google.common.collect.Collections2;

import net.minecraft.block.GrassBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.Heightmap.Type;

public class AlphaWorld implements AntiqueBlockAccess {

	public final long randomSeed;
	
	public final WorldAccess delegate;
	public final BlockPos.Mutable mut = new BlockPos.Mutable();
	
	public final Random rand;

	public boolean winterMode;

	public float spawnX;
	public float spawnY;
	public float spawnZ;
	
	public int heightLimit = 128;

	public boolean isolated;
	
	public AlphaWorld(WorldAccess delegate, long seed) {
		this.delegate = delegate;
		this.randomSeed = seed;
		this.rand = delegate.getRandom();
	}
	
	public Iterable<? extends PlayerEntity> getPlayerEntities() {
		return Collections2.filter(delegate.getPlayers(), Antiquated::isInAntiqueBiome);
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
		if (id == AlphaBlock.snow.blockID && delegate.getBlockState(mut.set(x, y-1, z)).getBlock() == ABlocks.GRASS) {
			delegate.setBlockState(mut, ABlocks.GRASS.getDefaultState().with(GrassBlock.SNOWY, true), 3);
		}
	}

	public int getHeightValue(int x, int z) {
		return delegate.getTopY(Type.WORLD_SURFACE, x, z);
	}

	public boolean isBlockOpaqueCube(int x, int y, int z) {
		return delegate.getBlockState(mut.set(x, y, z)).isOpaqueFullCube(delegate, mut);
	}

	public PlayerEntity getClosestPlayer(double x, double y, double z, double d) {
		return delegate.getClosestPlayer(x, y, z, d, true);
	}

	public void spawnEntity(Entity e) {
		delegate.spawnEntity(e);
	}

	public int getHeightLimit() {
		return heightLimit;
	}
	
}
