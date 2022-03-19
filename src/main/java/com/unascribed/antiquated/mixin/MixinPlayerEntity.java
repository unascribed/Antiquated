package com.unascribed.antiquated.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.entity.AntiqueCreature;
import com.unascribed.antiquated.entity.AntiqueMonster;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
abstract public class MixinPlayerEntity extends LivingEntity {

	protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at=@At("HEAD"), method="canConsume(Z)Z", cancellable=true)
	public void canConsume(boolean alwaysEdible, CallbackInfoReturnable<Boolean> ci) {
		if (Antiquated.isInAntiqueBiome((Entity)(Object)this)) {
			ci.setReturnValue(false);
		}
	}
	
	@Redirect(at=@At(value="INVOKE", target="net/minecraft/world/World.playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"), method="attack")
	public void playAttackSound(World subject, @Nullable PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, Entity target) {
		if (target instanceof AntiqueCreature || target instanceof AntiqueMonster) {
			return;
		}
		subject.playSound(player, x, y, z, sound, category, volume, pitch);
	}
	
	@Redirect(at=@At(value="INVOKE", target="net/minecraft/server/world/ServerWorld.spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"), method="attack")
	public <T extends ParticleEffect> int spawnAttackParticles(ServerWorld subject, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed, Entity target) {
		if (target instanceof AntiqueCreature || target instanceof AntiqueMonster) {
			return 0;
		}
		return subject.spawnParticles(particle, x, y, z, count, deltaX, deltaY, deltaZ, speed);
	}
	
	@Inject(at=@At("HEAD"), method="getHurtSound(Lnet/minecraft/entity/damage/DamageSource;)Lnet/minecraft/sound/SoundEvent;",
			cancellable=true)
	public void getHurtSound(DamageSource src, CallbackInfoReturnable<SoundEvent> ci) {
		if (Antiquated.isInAntiqueBiome((Entity)(Object)this)) {
			ci.setReturnValue(ASounds.HIT);
		}
	}
	
	@Inject(at=@At("HEAD"), method="addCritParticles",
			cancellable=true)
	public void addCritParticles(Entity target, CallbackInfo ci) {
		if (target instanceof AntiqueCreature || target instanceof AntiqueMonster) {
			ci.cancel();
		}
	}
	@Inject(at=@At("HEAD"), method="shouldDismount()Z",
			cancellable=true)
	public void shouldDismount(CallbackInfoReturnable<Boolean> ci) {
		if (Antiquated.isInCursedAntiqueBiome(this) && ((PlayerEntity)(Object)this).hasVehicle()) {
			ci.setReturnValue(false);
		}
	}

	@Inject(at=@At("HEAD"), method="interact(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", cancellable=true)
	public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (Antiquated.isInAntiqueBiome(this)) {
			if (entity != null && entity == getVehicle()) {
				stopRiding();
				cir.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}

}
