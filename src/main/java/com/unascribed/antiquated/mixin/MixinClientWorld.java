package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.world.ClientWorld;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

	@Inject(at=@At("HEAD"), method="method_23783", cancellable=true)
	public void getSkyLightBrightness(float tickDelta, CallbackInfoReturnable<Float> ci) {
		if (AntiquatedClient.forceSkyLight) {
			ci.setReturnValue(AntiquatedClient.forcedSkyLight);
		}
	}
	
}
