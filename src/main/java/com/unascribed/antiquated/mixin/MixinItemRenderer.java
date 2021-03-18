package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.unascribed.antiquated.init.ABlocks;

import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

	@Shadow @Final
	private ItemModels models;
	
	@ModifyVariable(at=@At("HEAD"), method="renderGuiItemModel", argsOnly=true)
	public BakedModel modifyBakedModel(BakedModel orig, ItemStack stack) {
		if (stack.getItem() == ABlocks.COBBLESTONE_STAIRS.asItem()) {
			return models.getModelManager().getModel(new ModelIdentifier("antiquated:cobblestone_stairs_gui#inventory"));
		} else if (stack.getItem() == ABlocks.WOOD_STAIRS.asItem()) {
			return models.getModelManager().getModel(new ModelIdentifier("antiquated:wood_stairs_gui#inventory"));
		}
		return orig;
	}

}
