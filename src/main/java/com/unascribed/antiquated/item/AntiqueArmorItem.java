package com.unascribed.antiquated.item;

import java.util.UUID;

import com.unascribed.antiquated.init.ASounds;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class AntiqueArmorItem extends ArmorItem {

	private static final UUID[] MODIFIERS = new UUID[] {
			UUID.fromString("c5c318f0-b67d-4bd1-8977-be721246fc17"),
			UUID.fromString("fe95f6a4-148b-48b4-bdda-370dea184fd2"),
			UUID.fromString("9c7b1b5e-be12-4080-85a2-dc6ab92647e7"),
			UUID.fromString("34e6de00-3840-48e4-886a-324e0a5c3140")
		};
	
	private static final int[] PROTECTION = {
			3, 6, 8, 3
	};
	
	private final int protection;
	
	public AntiqueArmorItem(EquipmentSlot slot, Settings settings) {
		super(new ArmorMaterial() {
			
			@Override
			public float getToughness() {
				return 0;
			}
			
			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.ofItems();
			}
			
			@Override
			public int getProtectionAmount(EquipmentSlot slot) {
				return 0;
			}
			
			@Override
			public String getName() {
				return "antiquated";
			}
			
			@Override
			public float getKnockbackResistance() {
				return 0;
			}
			
			@Override
			public SoundEvent getEquipSound() {
				return ASounds.SILENCE;
			}
			
			@Override
			public int getEnchantability() {
				return 0;
			}
			
			@Override
			public int getDurability(EquipmentSlot slot) {
				return 0;
			}
		}, slot, settings);
		this.protection = PROTECTION[slot.getEntitySlotId()];
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		// a mixin calls the stack-sensitive version below
		return ImmutableMultimap.of();
	}
	
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
		return slot == this.slot ?
				ImmutableMultimap.of(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(MODIFIERS[slot.getEntitySlotId()],
						"Antique armor conditional modifier", (this.protection*(1-(stack.getDamage()/(double)stack.getMaxDamage()))), EntityAttributeModifier.Operation.ADDITION))
			: ImmutableMultimap.of();
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return false;
	}
	
}
