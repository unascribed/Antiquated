package com.unascribed.antiquated.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootContext.Builder;

public class IdentitySilkTouchBlock extends Block {

	public IdentitySilkTouchBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, Builder builder) {
		List<ItemStack> identity = handle(state, builder);
		if (identity != null) return identity;
		return super.getDroppedStacks(state, builder);
	}

	public static List<ItemStack> handle(BlockState state, Builder builder) {
		LootContext ctx = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
		if (ctx.hasParameter(LootContextParameters.TOOL)) {
			ItemStack tool = ctx.get(LootContextParameters.TOOL);
			if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) > 0) {
				return Lists.newArrayList(new ItemStack(state.getBlock()));
			}
		}
		return null;
	}

}
