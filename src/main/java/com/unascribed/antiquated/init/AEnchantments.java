package com.unascribed.antiquated.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.entity.EquipmentSlot;

public class AEnchantments {

	public static final Enchantment LEGACY_CURSE = new Enchantment(Rarity.RARE, EnchantmentTarget.WEARABLE, new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
		
		@Override
		public boolean isCursed() {
			return true;
		}
		
	};
	
}
