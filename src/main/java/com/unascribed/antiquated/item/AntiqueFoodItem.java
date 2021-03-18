package com.unascribed.antiquated.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AntiqueFoodItem extends Item {

	private final int health;
	
	public AntiqueFoodItem(int health, Settings settings) {
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
