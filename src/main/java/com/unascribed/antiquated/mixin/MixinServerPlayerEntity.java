package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.unascribed.antiquated.Antiquated;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

	public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	@Shadow
	public ServerPlayNetworkHandler networkHandler;

	@Inject(at=@At("TAIL"), method="onSpawn()V")
	public void onSpawn(CallbackInfo ci) {
		ServerWorld ow = world.getServer().getOverworld();
		if (Antiquated.isAntiqueWorld(ow)) {
			networkHandler.sendPacket(ServerPlayNetworking.createS2CPacket(new Identifier("antiquated", "is_antique_world"), new PacketByteBuf(Unpooled.buffer())));
		}
	}
	
}
