package com.unascribed.antiquated.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public class MixinGameRenderer<T extends Entity> {

	@Shadow @Final private MinecraftClient client;

	@ModifyArg(method = "updateTargetedEntity(F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"))
	private Predicate<T> isRiding(Predicate<T> old){
		return (e) -> old.test(e) || e.hasPlayerRider();
	}
	@Inject(method = "updateTargetedEntity(F)V", at=@At(value = "com.unascribed.antiquated.mixin.BeforeFieldAccessReversed",
			opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER, target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",ordinal = 0))
	private void updateTargetedEntity(CallbackInfo ci){
		if (client.crosshairTarget instanceof EntityHitResult && ((EntityHitResult)client.crosshairTarget).getEntity().hasPlayerRider()) {
			this.client.targetedEntity = ((EntityHitResult)client.crosshairTarget).getEntity();
			System.out.println("E"+((EntityHitResult) client.crosshairTarget).getEntity());
		}
	}
}
