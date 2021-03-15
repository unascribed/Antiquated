package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.render.LightmapTextureManager;

@Mixin(LightmapTextureManager.class)
public interface AccessorLightmapTextureManager {

	@Accessor("field_21528")
	void antiquated$setTorchFlicker(float f);
	
}
