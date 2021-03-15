package com.unascribed.antiquated.init;

import com.unascribed.antiquated.AncientFoodItem;
import com.unascribed.antiquated.Antiquated;

import net.minecraft.item.Item;

public class AItems {

	public static final Item PORKCHOP = new AncientFoodItem(3, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item COOKED_PORKCHOP = new AncientFoodItem(8, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item APPLE = new AncientFoodItem(4, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	public static final Item GOLDEN_APPLE = new AncientFoodItem(20, new Item.Settings()
			.group(Antiquated.GROUP)
			.maxCount(1));
	
	public static final Item LEATHER = new Item(new Item.Settings()
			.group(Antiquated.GROUP));
	
}
