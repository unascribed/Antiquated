package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.render.item.HeldItemRenderer;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

	@ModifyConstant(constant=@Constant(floatValue=0.1f), method="renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V")
	public float modifySwayMultiplier(float orig) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			return 0;
		}
		return orig;
	}
	
}
