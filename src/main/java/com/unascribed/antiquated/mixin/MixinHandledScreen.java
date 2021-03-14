package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen extends Screen {

	protected MixinHandledScreen(Text title) {
		super(title);
	}

	@ModifyVariable(at=@At(value="INVOKE_ASSIGN", target="net/minecraft/screen/slot/Slot.getBackgroundSprite"), method="drawSlot")
	protected Pair<Identifier, Identifier> getBackgroundForSlot(Pair<Identifier, Identifier> orig, MatrixStack matrices, Slot slot) {
		Object self = this;
		if (self instanceof InventoryScreen) {
			return null;
		}
		return orig;
	}
	
}
