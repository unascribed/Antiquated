package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.model.json.JsonUnbakedModel;

@Mixin(JsonUnbakedModel.class)
public class MixinJsonUnbakedModel {

	@Shadow
	public String id;
	
	@Inject(at=@At("HEAD"), method="useAmbientOcclusion", cancellable=true)
	public void useAmbientOcclusion(CallbackInfoReturnable<Boolean> ci) {
		if (id.startsWith("antiquated:")) {
			// if the model has a parent, the parent's ambientocclusion value will always be used
			// I don't want to copy-paste every vanilla model and turn off AO, so this mixin will do
			ci.setReturnValue(false);
		}
	}
	
}
