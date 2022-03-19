package com.unascribed.antiquated.mixin;

import com.unascribed.antiquated.Antiquated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(GameRenderer.class)
public class MixinGameRenderer<T extends Entity> {

	@Shadow @Final private MinecraftClient client;

	@Inject(method="updateTargetedEntity(F)V", at=@At("HEAD"), cancellable=true)
	private void updateTargetedEntity(float tickDelta, CallbackInfo ci){
		Entity cam = this.client.getCameraEntity();
		if (this.client.world != null && Antiquated.isInAntiqueBiome(cam)) {
			Entity cart = cam.getVehicle();
			if (cart != null) {
				Box box2 = cart.getBoundingBox().expand(cart.getTargetingMargin());
				Vec3d camPos = cam.getCameraPosVec(tickDelta);
				Vec3d rot = cam.getRotationVec(1.0F);
				double d = this.client.interactionManager.getReachDistance();
				Vec3d vec3d3 = camPos.add(rot.x * d, rot.y * d, rot.z * d);
				Optional<Vec3d> optional = box2.raycast(camPos, vec3d3);
				if (optional.isPresent()) {
					this.client.crosshairTarget = new EntityHitResult(cart, optional.get());
					this.client.targetedEntity = cart;
					ci.cancel();
				}
			}
		}
	}
}
