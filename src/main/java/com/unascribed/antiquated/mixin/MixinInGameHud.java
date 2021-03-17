package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;

@Mixin(InGameHud.class)
public class MixinInGameHud {

	@Unique
	private static boolean isLookingAtXpBlock() {
		HitResult tgt = MinecraftClient.getInstance().crosshairTarget;
		if (tgt.getType() == Type.BLOCK) {
			BlockHitResult bhr = (BlockHitResult)tgt;
			if (MinecraftClient.getInstance().world.getBlockState(bhr.getBlockPos()).isIn(Antiquated.WANTS_XP)) {
				return true;
			}
		}
		return false;
	}
	
	@Inject(at=@At("HEAD"), method="renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V", cancellable=true)
	public void renderExperienceBar(MatrixStack matrices, int i, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome() && !isLookingAtXpBlock()) {
			ci.cancel();
		}
	}
	
	@Inject(at=@At("HEAD"), method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsHead(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome() && !isLookingAtXpBlock()) {
			matrices.push();
			matrices.translate(0, 7, 0);
		}
	}
	
	@Inject(at=@At("RETURN"), method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsRet(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome() && !isLookingAtXpBlock()) {
			matrices.pop();
		}
	}
	
	@Inject(at=@At(value="INVOKE_STRING", target="net/minecraft/util/profiler/Profiler.push(Ljava/lang/String;)V", args="ldc=armor", shift=Shift.AFTER),
			method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsBeforeArmor(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			matrices.push();
			matrices.translate(MinecraftClient.getInstance().getWindow().getScaledWidth(), 10, 0);
			matrices.scale(-1, 1, 1);
			GlStateManager.disableCull();
		}
	}
	
	@Inject(at=@At(value="INVOKE_STRING", target="net/minecraft/util/profiler/Profiler.swap(Ljava/lang/String;)V", args="ldc=health", shift=Shift.AFTER),
			method="renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V")
	private void renderStatusBarsAfterArmor(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			matrices.pop();
		}
	}
	
	@Inject(at=@At("HEAD"), method="getHeartCount", cancellable=true)
	private void getHeartCount(LivingEntity entity, CallbackInfoReturnable<Integer> ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			ci.setReturnValue(-1);
		}
	}
	
	@Inject(at=@At("HEAD"), method="renderHeldItemTooltip", cancellable=true)
	public void renderHeldItemTooltip(MatrixStack matrices, CallbackInfo ci) {
		if (AntiquatedClient.isInCursedAntiqueBiome()) {
			ci.cancel();
		}
	}
	
}
