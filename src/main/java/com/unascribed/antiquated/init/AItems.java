package com.unascribed.antiquated.init;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.item.AntiqueArmorItem;
import com.unascribed.antiquated.item.AntiqueBowItem;
import com.unascribed.antiquated.item.AntiqueFoodItem;
import com.unascribed.antiquated.item.AntiqueSwordItem;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class AItems {

	public static final Item PORKCHOP = new AntiqueFoodItem(3, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item COOKED_PORKCHOP = new AntiqueFoodItem(8, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item APPLE = new AntiqueFoodItem(4, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item GOLDEN_APPLE = new AntiqueFoodItem(20, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	
	public static final Item LEATHER = new Item(new Item.Settings()
			.group(Antiquated.GROUP));
	
	public static final Item RECORD_CALM4 = new MusicDiscItem(4, ASounds.CALM4, new Item.Settings()
			.maxCount(1)
			.group(Antiquated.GROUP)) {};

			
	public static final Item LEATHER_HELMET = new AntiqueArmorItem(EquipmentSlot.HEAD, new Item.Settings()
			.maxDamage(33)
			.group(Antiquated.GROUP));
	
	public static final Item LEATHER_CHESTPLATE = new AntiqueArmorItem(EquipmentSlot.CHEST, new Item.Settings()
			.maxDamage(48)
			.group(Antiquated.GROUP));
	
	public static final Item LEATHER_LEGGINGS = new AntiqueArmorItem(EquipmentSlot.LEGS, new Item.Settings()
			.maxDamage(45)
			.group(Antiquated.GROUP));
	
	public static final Item LEATHER_BOOTS = new AntiqueArmorItem(EquipmentSlot.FEET, new Item.Settings()
			.maxDamage(39)
			.group(Antiquated.GROUP));
	
	
	public static final Item STUDDED_HELMET = new AntiqueArmorItem(EquipmentSlot.HEAD, new Item.Settings()
			.maxDamage(132)
			.group(Antiquated.GROUP));
	
	public static final Item STUDDED_CHESTPLATE = new AntiqueArmorItem(EquipmentSlot.CHEST, new Item.Settings()
			.maxDamage(192)
			.group(Antiquated.GROUP));
	
	public static final Item STUDDED_LEGGINGS = new AntiqueArmorItem(EquipmentSlot.LEGS, new Item.Settings()
			.maxDamage(180)
			.group(Antiquated.GROUP));
	
	public static final Item STUDDED_BOOTS = new AntiqueArmorItem(EquipmentSlot.FEET, new Item.Settings()
			.maxDamage(156)
			.group(Antiquated.GROUP));
	
	public static final BucketItem WATER_BUCKET = new BucketItem(AFluids.WATER, new Item.Settings()
			.recipeRemainder(Items.BUCKET)
			.maxCount(1)
			.group(Antiquated.GROUP));
	
	public static final BucketItem LAVA_BUCKET = new BucketItem(AFluids.LAVA, new Item.Settings()
			.recipeRemainder(Items.BUCKET)
			.maxCount(1)
			.group(Antiquated.GROUP));

	private static final ToolMaterial ANTIQUE_IRON = new ToolMaterial() {
		
		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.ofItems();
		}
		
		@Override
		public float getMiningSpeedMultiplier() {
			return 6;
		}
		
		@Override
		public int getMiningLevel() {
			return 2;
		}
		
		@Override
		public int getEnchantability() {
			return 0;
		}
		
		@Override
		public int getDurability() {
			return 128;
		}
		
		@Override
		public float getAttackDamage() {
			return 0;
		}
	};
	
	public static final Item SWORD = new AntiqueSwordItem(7, new Item.Settings()
			.maxDamage(128)
			.group(Antiquated.GROUP));
	
	public static final Item PICKAXE = new PickaxeItem(ANTIQUE_IRON, 3, 900000000, new Item.Settings()
			.maxDamage(128)
			.group(Antiquated.GROUP)) {};
			
	public static final Item AXE = new AxeItem(ANTIQUE_IRON, 4, 900000000, new Item.Settings()
			.maxDamage(128)
			.group(Antiquated.GROUP)) {};
			
	public static final Item SHOVEL = new ShovelItem(ANTIQUE_IRON, 2, 900000000, new Item.Settings()
			.maxDamage(128)
			.group(Antiquated.GROUP)) {};
			
	public static final Item BOW = new AntiqueBowItem(new Item.Settings()
			.maxDamage(256)
			.group(Antiquated.GROUP)) {};
	
}
