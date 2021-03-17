package com.unascribed.antiquated.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.AntiqueArmorItem;

import com.google.common.collect.Maps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SmithingRecipe;

@Mixin(SmithingRecipe.class)
public abstract class MixinSmithingRecipe {

	@Shadow
	public abstract ItemStack getOutput();
	
	@Inject(at=@At("HEAD"), method="craft", cancellable=true)
	public void craft(Inventory inv, CallbackInfoReturnable<ItemStack> ci) {
		if (getOutput().getItem() instanceof AntiqueArmorItem) {
			double duraA = inv.getStack(0).getDamage()/(double)inv.getStack(0).getMaxDamage();
			double duraB = inv.getStack(1).getDamage()/(double)inv.getStack(1).getMaxDamage();
			ItemStack stack = getOutput().copy();
			stack.setDamage((int)(stack.getMaxDamage()*(duraA*duraB)));
			Map<Enchantment, Integer> enchantments = Maps.newHashMap();
			for (Map.Entry<Enchantment, Integer> en : EnchantmentHelper.get(inv.getStack(1)).entrySet()) {
				if (en.getKey().isTreasure()) continue;
				if (en.getKey().isCursed() || en.getValue() > 1) {
					enchantments.put(en.getKey(), en.getKey().isCursed() ? en.getValue() : en.getValue()/2);
				}
			}
			EnchantmentHelper.set(enchantments, stack);
			ci.setReturnValue(stack);
		}
	}
	
}
