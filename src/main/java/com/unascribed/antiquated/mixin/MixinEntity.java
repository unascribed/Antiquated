package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class MixinEntity {

	@Inject(at=@At("HEAD"), method="isSprinting()Z", cancellable=true)
	public void isSprinting(CallbackInfoReturnable<Boolean> ci) {
		if (Antiquated.isInAntiqueBiome((Entity)(Object)this)) {
			ci.setReturnValue(false);
		}
	}
	
	@Inject(at=@At("HEAD"), method="setSprinting(Z)V", cancellable=true)
	public void setSprinting(boolean sprinting, CallbackInfo ci) {
		if (Antiquated.isInAntiqueBiome((Entity)(Object)this) && sprinting) {
			ci.cancel();
		}
	}
	
}
