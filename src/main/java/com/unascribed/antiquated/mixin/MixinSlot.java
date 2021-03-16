package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

@Mixin(Slot.class)
public class MixinSlot {

	@Shadow @Final
	public Inventory inventory;
	
	@Inject(at=@At("HEAD"), method="getMaxItemCount(Lnet/minecraft/item/ItemStack;)I", cancellable=true)
	public void getMaxItemCount(ItemStack stack, CallbackInfoReturnable<Integer> ci) {
		if ((inventory == null || inventory.getMaxCountPerStack() != 100) && Antiquated.increaseStackSize.get().intValue() > 0) {
			int orig = Antiquated.increaseStackSize.get().intValue();
			try {
				Antiquated.increaseStackSize.get().setValue(0);
				ci.setReturnValue(stack.getMaxCount());
			} finally {
				Antiquated.increaseStackSize.get().setValue(orig);
			}
		}
	}
	
}
