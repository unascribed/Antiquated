package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.AntiqueArmorItem;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

	@Shadow
	public abstract Item getItem();
	
	@Inject(at=@At("RETURN"), method="getMaxCount", cancellable=true)
	public void getMaxCount(CallbackInfoReturnable<Integer> ci) {
		if (Antiquated.increaseStackSize.get().intValue() > 0) {
			ci.setReturnValue(Math.min(100, (int)(ci.getReturnValueI()*(100/64f))));
		}
	}
	
	@Inject(at=@At("TAIL"), method="getAttributeModifiers", cancellable=true)
	public void getAttributeModifiers(EquipmentSlot equipmentSlot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> ci) {
		if (getItem() instanceof AntiqueArmorItem && ci.getReturnValue().isEmpty()) {
			ci.setReturnValue(((AntiqueArmorItem)getItem()).getAttributeModifiers((ItemStack)(Object)this, equipmentSlot));
		}
	}
	
}
