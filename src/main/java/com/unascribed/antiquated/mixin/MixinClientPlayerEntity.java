package com.unascribed.antiquated.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ProjectileUtil.class)
public class MixinClientPlayerEntity {

	@Redirect(at=@At(value = "INVOKE", target="Lnet/minecraft/entity/Entity;getRootVehicle()Lnet/minecraft/entity/Entity;"), method = "raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;")
	private static Entity isRiding(Entity entity) {
		return MinecraftClient.getInstance().cameraEntity;
	}
}
