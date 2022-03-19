package com.unascribed.antiquated.init;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class AFluids {

	public static final WaterFluid FLOWING_WATER = new WaterFluid.Flowing() {
		@Override
		public BlockState toBlockState(FluidState state) {
			return ABlocks.WATER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
		}
		
		@Override
		public boolean matchesType(Fluid fluid) {
			return fluid == AFluids.FLOWING_WATER || fluid == AFluids.WATER;
		}
		
		@Override
		public ParticleEffect getParticle() {
			return null;
		}
		
		@Override
		public Item getBucketItem() {
			return AItems.WATER_BUCKET;
		}
		
		@Override
		public Fluid getFlowing() {
			return AFluids.FLOWING_WATER;
		}
		
		@Override
		public Fluid getStill() {
			return AFluids.WATER;
		}
	};
	
	public static final WaterFluid WATER = new WaterFluid.Still() {
		@Override
		public BlockState toBlockState(FluidState state) {
			return ABlocks.WATER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
		}
		
		@Override
		public boolean matchesType(Fluid fluid) {
			return fluid == AFluids.FLOWING_WATER || fluid == AFluids.WATER;
		}
		
		@Override
		public ParticleEffect getParticle() {
			return null;
		}
		
		@Override
		public Item getBucketItem() {
			return AItems.WATER_BUCKET;
		}
		
		@Override
		public Fluid getFlowing() {
			return AFluids.FLOWING_WATER;
		}
		
		@Override
		public Fluid getStill() {
			return AFluids.WATER;
		}
	};
	
	public static final LavaFluid FLOWING_LAVA = new LavaFluid.Flowing() {
		@Override
		public BlockState toBlockState(FluidState state) {
			return ABlocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
		}
		
		@Override
		public boolean matchesType(Fluid fluid) {
			return fluid == AFluids.FLOWING_LAVA || fluid == AFluids.LAVA;
		}
		
		@Override
		public ParticleEffect getParticle() {
			return null;
		}
		
		@Override
		public Item getBucketItem() {
			return AItems.LAVA_BUCKET;
		}
		
		@Override
		public Fluid getFlowing() {
			return AFluids.FLOWING_LAVA;
		}
		
		@Override
		public Fluid getStill() {
			return AFluids.LAVA;
		}
	};
	
	public static final LavaFluid LAVA = new LavaFluid.Still() {
		@Override
		public BlockState toBlockState(FluidState state) {
			return ABlocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
		}
		
		@Override
		public boolean matchesType(Fluid fluid) {
			return fluid == AFluids.FLOWING_LAVA || fluid == AFluids.LAVA;
		}
		
		@Override
		public ParticleEffect getParticle() {
			return null;
		}
		
		@Override
		public Item getBucketItem() {
			return AItems.LAVA_BUCKET;
		}
		
		@Override
		public Fluid getFlowing() {
			return AFluids.FLOWING_LAVA;
		}
		
		@Override
		public Fluid getStill() {
			return AFluids.LAVA;
		}
	};
	
	public static final Fluid SPONGE_AIR = new Fluid() {
		
		@Override
		protected BlockState toBlockState(FluidState state) {
			return ABlocks.SPONGE_AIR.getDefaultState();
		}
		
		@Override
		public boolean isStill(FluidState state) {
			return true;
		}
		
		@Override
		protected Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state) {
			return Vec3d.ZERO;
		}
		
		@Override
		public int getTickRate(WorldView world) {
			return 0;
		}
		
		@Override
		public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
			return VoxelShapes.empty();
		}
		
		@Override
		public int getLevel(FluidState state) {
			return 0;
		}
		
		@Override
		public float getHeight(FluidState state, BlockView world, BlockPos pos) {
			return 0;
		}
		
		@Override
		public float getHeight(FluidState state) {
			return 0;
		}
		
		@Override
		public Item getBucketItem() {
			return ABlocks.SPONGE.asItem();
		}
		
		@Override
		protected float getBlastResistance() {
			return 0;
		}
		
		@Override
		protected boolean isEmpty() {
			return true;
		}
		
		@Override
		protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
			return !fluid.isIn(FluidTags.WATER);
		}
	};
	
}
