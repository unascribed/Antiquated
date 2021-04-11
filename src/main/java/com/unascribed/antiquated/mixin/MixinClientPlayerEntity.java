package com.unascribed.antiquated.mixin;

import com.unascribed.antiquated.AntiquatedClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

	@Inject(at=@At("HEAD"), method="isAutoJumpEnabled", cancellable = true)
	public void isAutoJumpEnabled(CallbackInfoReturnable<Boolean> cir) {
		if (AntiquatedClient.isInCursedAntiqueBiome()) {
			cir.setReturnValue(false);
		}
	}
}
