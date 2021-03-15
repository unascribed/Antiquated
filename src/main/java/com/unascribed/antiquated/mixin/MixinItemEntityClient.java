package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntityClient {
	
	@Shadow
	public ItemStack getStack() { return null; }
	
	@Inject(at=@At("HEAD"), method="method_27314(F)F", cancellable=true)
	public void getRotation(float partialTicks, CallbackInfoReturnable<Float> ci) {
		if (!(getStack().getItem() instanceof BlockItem) && Registry.ITEM.getId(getStack().getItem()).getNamespace().equals("antiquated")) {
			ci.setReturnValue((float)Math.toRadians((-MinecraftClient.getInstance().gameRenderer.getCamera().getYaw())+180));
		}
	}
	
}
