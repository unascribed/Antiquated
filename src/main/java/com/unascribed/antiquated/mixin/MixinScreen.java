package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(Screen.class)
public class MixinScreen {
	
	@Inject(at=@At("HEAD"), method="renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V", cancellable=true)
	protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo ci) {
		Identifier id = Registry.ITEM.getId(stack.getItem());
		if (AntiquatedClient.isInCursedAntiqueBiome() || (id.getNamespace().equals("antiquated") && !id.getPath().startsWith("studded_"))) {
			ci.cancel();
		}
	}
	
	@ModifyConstant(constant=@Constant(intValue=-1072689136), method="renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V")
	public int modifyTopBgColor(int color) {
		if (AntiquatedClient.isInAntiqueBiome()) return 0x60050500;
		return color;
	}
	
	@ModifyConstant(constant=@Constant(intValue=-804253680), method="renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V")
	public int modifyBottomBgColor(int color) {
		if (AntiquatedClient.isInAntiqueBiome()) return 0xA0303060;
		return color;
	}

}
