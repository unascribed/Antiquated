package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

	@Inject(at=@At("HEAD"), method="clickSlot", cancellable=true)
	public void clickSlot(int syncId, int slotId, int clickData, SlotActionType actionType, PlayerEntity player, CallbackInfoReturnable<ItemStack> ci) {
		if (Antiquated.isInCursedAntiqueBiome(player)) {
			switch (actionType) {
				case PICKUP_ALL:
				case QUICK_CRAFT:
				case QUICK_MOVE:
					ci.setReturnValue(ItemStack.EMPTY);
					break;
			}
		}
	}
	
}
