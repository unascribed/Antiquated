package com.unascribed.antiquated.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.AntiquatedClient;

@Mixin(ClickableWidget.class)
public class MixinClickableWidget {
	@Unique
	private static final Identifier OLD_BUTTONS_TEX = new Identifier("antiquated", "textures/gui/button.png");

	@Inject(at=@At(value="INVOKE", target="net/minecraft/client/texture/TextureManager.bindTexture(Lnet/minecraft/util/Identifier;)V", shift=Shift.AFTER),
			method="renderButton")
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			MinecraftClient.getInstance().getTextureManager().bindTexture(OLD_BUTTONS_TEX);
		}
	}
	
	@ModifyVariable(at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/widget/ClickableWidget;getMessage()Lnet/minecraft/text/Text;"),
			method="renderButton", ordinal=2)
	private int adjustTextColor(int color) {
		Object self = this;
		if (AntiquatedClient.isInAntiqueBiome() && ((ClickableWidget)self).isHovered()) {
			return 0xFFFFFFA0;
		}
		return color;
	}
	
}
