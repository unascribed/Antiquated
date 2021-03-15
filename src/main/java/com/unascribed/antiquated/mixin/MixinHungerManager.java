package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.Antiquated;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(HungerManager.class)
public abstract class MixinHungerManager {

	@Inject(at=@At("HEAD"), method="update(Lnet/minecraft/entity/player/PlayerEntity;)V", cancellable=true)
	public void update(PlayerEntity pe, CallbackInfo ci) {
		if (Antiquated.isInAntiqueBiome(pe)) {
			ci.cancel();
		}
	}
	
}
