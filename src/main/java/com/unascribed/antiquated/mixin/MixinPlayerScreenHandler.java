package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.unascribed.antiquated.Antiquated;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(PlayerScreenHandler.class)
public class MixinPlayerScreenHandler {

	@Shadow @Final
	private CraftingInventory craftingInput;
	
	@Redirect(at=@At(value="FIELD", target="net/minecraft/world/World.isClient"), method="close")
	public boolean preventCraftingDrop(World subject, PlayerEntity player) {
		if (Antiquated.isInAntiqueBiome(player)) {
			if (!craftingInput.isEmpty() && player instanceof ServerPlayerEntity) {
				ServerPlayerEntity spe = (ServerPlayerEntity)player;
				spe.getAdvancementTracker().grantCriterion(spe.world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "grid_storage")), "minecraft:impossible");
			}
			return true;
		}
		return subject.isClient;
	}
	
	
}
