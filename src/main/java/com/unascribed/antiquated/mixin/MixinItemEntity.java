package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.init.ABlocks;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {

	@Shadow
	public abstract ItemStack getStack();
	
	@Inject(at=@At("HEAD"), method="canMerge()Z", cancellable=true)
	public void canMerge(CallbackInfoReturnable<Boolean> ci) {
		if (getStack().getItem() == ABlocks.PLANKS_OLD.asItem()) {
			ci.setReturnValue(false);
		}
	}
	
}
