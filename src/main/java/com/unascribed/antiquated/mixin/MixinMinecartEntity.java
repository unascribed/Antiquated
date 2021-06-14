package com.unascribed.antiquated.mixin;

import com.unascribed.antiquated.Antiquated;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecartEntity.class)
abstract public class MixinMinecartEntity extends AbstractMinecartEntity {

	protected MixinMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}
	@Inject(at=@At("HEAD"), method="interact(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", cancellable=true)
	public void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (Antiquated.isInAntiqueBiome(player) && player.isSneaky() ) {
			player.stopRiding();
			cir.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
