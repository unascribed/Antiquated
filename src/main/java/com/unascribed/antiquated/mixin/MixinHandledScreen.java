package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Pair;
import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen extends Screen {

	protected MixinHandledScreen(Text title) {
		super(title);
	}

	@Shadow
	protected boolean cursorDragging;
	
	@ModifyVariable(at=@At(value="INVOKE_ASSIGN", target="net/minecraft/screen/slot/Slot.getBackgroundSprite()Lcom/mojang/datafixers/util/Pair;"), method="drawSlot")
	protected Pair<Identifier, Identifier> getBackgroundForSlot(Pair<Identifier, Identifier> orig) {
		Object self = this;
		if (self instanceof InventoryScreen && AntiquatedClient.isInAntiqueBiome()) {
			return null;
		}
		return orig;
	}
	
	@Inject(at=@At("TAIL"), method="mouseClicked")
	public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> ci) {
		if (AntiquatedClient.isInCursedAntiqueBiome()) {
			cursorDragging = false;
		}
	}
	
	@Inject(at=@At("HEAD"), method="handleHotbarKeyPressed", cancellable=true)
	protected void handleHotbarKeyPressed(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> ci) {
		if (AntiquatedClient.isInCursedAntiqueBiome()) {
			ci.setReturnValue(false);
		}
	}
	
}
