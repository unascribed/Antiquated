package com.unascribed.antiquated.init;

import com.unascribed.antiquated.AntiqueFoodItem;
import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.AntiqueArmorItem;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;

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
	
}
