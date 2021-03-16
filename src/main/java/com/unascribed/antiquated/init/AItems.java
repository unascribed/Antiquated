package com.unascribed.antiquated.init;

import com.unascribed.antiquated.AncientFoodItem;
import com.unascribed.antiquated.Antiquated;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;

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
	
	public static final Item RECORD_CALM4 = new MusicDiscItem(4, ASounds.CALM4, new Item.Settings()
			.maxCount(1)
			.group(Antiquated.GROUP)) {};
	
}
