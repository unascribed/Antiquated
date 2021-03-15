package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.Antiquated;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.ConfirmScreenActionS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class MixinServerPlayNetworkHandler {

	@Shadow
	public ServerPlayerEntity player;
	
	@Inject(at=@At("HEAD"), method="onClickSlot", cancellable=true)
	public void onClickSlot(ClickSlotC2SPacket packet, CallbackInfo ci) {
		if (Antiquated.isInAntiqueBiome(player)) {
			switch (packet.getActionType()) {
				case PICKUP_ALL:
				case QUICK_CRAFT:
				case QUICK_MOVE:
					this.player.networkHandler.sendPacket(new ConfirmScreenActionS2CPacket(packet.getSyncId(), packet.getActionId(), false));
					ci.cancel();
					break;
			}
		}
	}
	
}
