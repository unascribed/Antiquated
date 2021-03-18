package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.item.AntiqueArmorItem;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.world.World;

@Mixin(RepairItemRecipe.class)
public class MixinRepairItemRecipe {

	@Inject(at=@At("HEAD"), method="matches", cancellable=true)
	public void matches(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> ci) {
		for (int i = 0; i < craftingInventory.size(); ++i) {
			if (craftingInventory.getStack(i).getItem() instanceof AntiqueArmorItem) {
				ci.setReturnValue(false);
			}
		}
	}
	
}
