package com.unascribed.antiquated.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@Mixin(Explosion.class)
public class MixinExplosion {

	@Shadow @Final
	private World world;
	@Shadow @Final
	private double x;
	@Shadow @Final
	private double y;
	@Shadow @Final
	private double z;
	@Shadow @Final
	private List<BlockPos> affectedBlocks;
	@Shadow @Final
	private float power;

	@ModifyVariable(at=@At("HEAD"), method="affectWorld", argsOnly=true, ordinal=0)
	public boolean modifySpawnCrescents(boolean in) {
		if (Antiquated.isInAntiqueBiome(world, new BlockPos(x, y, z))) {
			return false;
		}
		return in;
	}

	@ModifyArg(at=@At(value="INVOKE", target="net/minecraft/world/World.playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V"), index=3, method="affectWorld")
	public SoundEvent modifySound(SoundEvent in) {
		if (Antiquated.isInAntiqueBiome(world, new BlockPos(x, y, z))) {
			return ASounds.EXPLODE;
		}
		return in;
	}

	@Inject(at=@At("TAIL"), method="affectWorld")
	public void spawnParticles(boolean ignore, CallbackInfo ci) {
		for (BlockPos pos : affectedBlocks) {
			for (int n29 = 0; n29 < 1; ++n29) {
				final double n30 = pos.getX() + world.random.nextFloat();
				final double n31 = pos.getY() + world.random.nextFloat();
				final double n32 = pos.getZ() + world.random.nextFloat();
				final double n33 = n30 - x;
				final double n34 = n31 - y;
				final double n35 = n32 - z;
				final double n36 = MathHelper.sqrt(n33 * n33 + n34 * n34 + n35 * n35);
				final double n37 = n33 / n36;
				final double n38 = n34 / n36;
				final double n39 = n35 / n36;
				final double n40 = 0.5 / (n36 / power + 0.1) * (world.random.nextFloat() * world.random.nextFloat() + 0.3f);
				final double n41 = n37 * n40;
				final double n42 = n38 * n40;
				final double n43 = n39 * n40;
				world.addParticle(ParticleTypes.CLOUD, (n30 + x * 1.0) / 2.0, (n31 + y * 1.0) / 2.0, (n32 + z * 1.0) / 2.0, n41, n42, n43);
				world.addParticle(ParticleTypes.SMOKE, n30, n31, n32, n41, n42, n43);
			}
		}
	}

}
