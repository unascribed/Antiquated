package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class MixinItemStack {

	@Inject(at=@At("RETURN"), method="getMaxCount", cancellable=true)
	public void getMaxCount(CallbackInfoReturnable<Integer> ci) {
		if (Antiquated.increaseStackSize.get().intValue() > 0) {
			ci.setReturnValue(Math.min(100, (int)(ci.getReturnValueI()*(100/64f))));
		}
	}
	
}
