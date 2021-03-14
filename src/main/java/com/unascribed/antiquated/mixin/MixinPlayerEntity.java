package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

	@Inject(at=@At("HEAD"), method="canConsume(Z)Z", cancellable=true)
	public void canConsume(boolean alwaysEdible, CallbackInfoReturnable<Boolean> ci) {
		if (Antiquated.isInAntiqueBiome((Entity)(Object)this)) {
			ci.setReturnValue(false);
		}
	}
	
}
