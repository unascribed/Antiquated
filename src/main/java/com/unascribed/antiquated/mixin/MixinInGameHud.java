package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(InGameHud.class)
public class MixinInGameHud {

	@Inject(at=@At("HEAD"), method="renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V", cancellable=true)
	public void renderExperienceBar(MatrixStack matrices, int i, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			ci.cancel();
		}
	}
	
	@Inject(at=@At("HEAD"), method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsHead(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			GlStateManager.pushMatrix();
			GlStateManager.translatef(0, 7, 0);
		}
	}
	
	@Inject(at=@At("RETURN"), method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsRet(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			GlStateManager.popMatrix();
		}
	}
	
	@Inject(at=@At("HEAD"), method="getHeartCount", cancellable=true)
	private void getHeartCount(LivingEntity entity, CallbackInfoReturnable<Integer> ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			ci.setReturnValue(-1);
		}
	}
	
}
