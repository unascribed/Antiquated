package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.init.ABlocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.StonecutterScreenHandler;

@Mixin(StonecutterScreenHandler.class)
public abstract class MixinStonecutterScreenHandler extends ScreenHandler {

	protected MixinStonecutterScreenHandler(ScreenHandlerType<?> type, int syncId) {
		super(type, syncId);
	}

	@Shadow @Final
	private ScreenHandlerContext context;
	
	@Inject(at=@At("HEAD"), method="canUse", cancellable=true)
	public void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> ci) {
		if (canUse(this.context, player, ABlocks.STONECUTTER)) {
			ci.setReturnValue(true);
		}
	}
	
}
