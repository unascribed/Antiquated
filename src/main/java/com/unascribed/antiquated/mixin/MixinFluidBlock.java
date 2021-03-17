package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.init.ABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@Mixin(FluidBlock.class)
public class MixinFluidBlock {

	@Shadow @Final
	protected FlowableFluid fluid;
	
	@Shadow
	private void playExtinguishSound(WorldAccess world, BlockPos pos) {}
	
	@Inject(at=@At("HEAD"), method="receiveNeighborFluids", cancellable=true)
	private void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> ci) {
		if (this.fluid.isIn(FluidTags.LAVA)) {
			for (Direction dir : Direction.values()) {
				if (dir != Direction.DOWN) {
					BlockPos checkPos = pos.offset(dir);
					FluidState them = world.getFluidState(checkPos);
					if (them.isIn(FluidTags.WATER)) {
						FluidState us = world.getFluidState(pos);
						boolean still = us.isStill();
						boolean anch = us.isIn(Antiquated.ANCIENT_FLUIDS) != them.isIn(Antiquated.ANCIENT_FLUIDS);
						boolean ancient = us.isIn(Antiquated.ANCIENT_FLUIDS);
						Block block;
						if (anch) {
							block = still ? ABlocks.ANCH_OBSIDIAN : ABlocks.ANCH_COBBLESTONE;
						} else if (ancient) {
							block = still ? ABlocks.OBSIDIAN : ABlocks.COBBLESTONE;
						} else {
							// don't touch modern-on-modern interactions
							continue;
						}
						world.setBlockState(pos, block.getDefaultState());
						this.playExtinguishSound(world, pos);
						ci.setReturnValue(false);
						return;
					}
				}
			}
		}
	}
	
}
