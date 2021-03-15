package com.unascribed.antiquated;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AncientFoodItem extends Item {

	private final int health;
	
	public AncientFoodItem(int health, Settings settings) {
		super(settings);
		this.health = health;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		stack.decrement(1);
		user.heal(health);
		return TypedActionResult.success(stack, false);
	}

}
