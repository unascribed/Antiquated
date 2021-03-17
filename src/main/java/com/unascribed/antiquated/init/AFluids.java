package com.unascribed.antiquated.init;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;

public class AFluids {

	public static final WaterFluid FLOWING_WATER = new WaterFluid.Flowing() {
		@Override
		public BlockState toBlockState(FluidState state) {
			return ABlocks.WATER.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
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
			return ABlocks.WATER.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
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
			return ABlocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
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
			return ABlocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
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
	
}
