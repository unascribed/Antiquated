package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(DoorBlock.class)
public class MixinDoorBlock {

	@Shadow
	private void playOpenCloseSound(World world, BlockPos pos, boolean open) {}
	
	@Inject(at=@At("HEAD"), method="playOpenCloseSound", cancellable=true)
	private void playOpenCloseSound(World world, BlockPos pos, boolean open, CallbackInfo ci) {
		Object self = this;
		if (self == ABlocks.DOOR) {
			world.playSound(null, pos, open ? ASounds.DOOR_OPEN : ASounds.DOOR_CLOSE, SoundCategory.BLOCKS, 1, world.random.nextFloat() * 0.1f + 0.9f);
			ci.cancel();
		}
	}
	
	@Inject(at=@At("HEAD"), method="onUse", cancellable=true)
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> ci) {
		Object self = this;
		if (self == ABlocks.DOOR) {
			state = state.cycle(DoorBlock.OPEN);
			world.setBlockState(pos, state, 10);
			playOpenCloseSound(world, pos, state.get(DoorBlock.OPEN));
			ci.setReturnValue(ActionResult.success(world.isClient));
		}
   }
	
}
