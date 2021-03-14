package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

@Mixin(CreativeInventoryScreen.class)
public class MixinCreativeInventoryScreen {
	
	@Inject(at=@At("HEAD"), method="renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V", cancellable=true)
	protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo ci) {
		if (Registry.ITEM.getId(stack.getItem()).getNamespace().equals("antiquated")) {
			ci.cancel();
		}
	}
	
}
