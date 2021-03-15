package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

@Mixin(GameMenuScreen.class)
public abstract class MixinGameMenuScreen extends Screen {

	protected MixinGameMenuScreen(Text title) {
		super(title);
	}

	@Shadow @Final
	private boolean showMenu;
	
	@Unique
	private int savingTicks;
	
	@Inject(at=@At("TAIL"), method="initWidgets")
	private void initWidgets(CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			// Alpha-clone menu - I like it, but it breaks functionality
			/*
			children.remove(buttons.remove(1));
			children.remove(buttons.remove(2));
			children.remove(buttons.remove(1));
			children.remove(buttons.remove(1));
			children.remove(buttons.remove(2));
			
			AbstractButtonWidget resume = buttons.get(0);
			AbstractButtonWidget options = buttons.get(1);
			AbstractButtonWidget quit = buttons.get(2);
			
			quit.setMessage(new TranslatableText(MinecraftClient.getInstance().isInSingleplayer() ? "menu.antiquated.returnToMenu" : "menu.antiquated.disconnect"));
			quit.x = this.width / 2 - 100;
			quit.y = this.height / 4 + 48;
			quit.setWidth(200);
			
			resume.setMessage(new TranslatableText("menu.antiquated.returnToGame"));
			resume.x = this.width / 2 - 100;
			resume.y = this.height / 4 + 24;
			resume.setWidth(200);
			
			options.x = this.width / 2 - 100;
			options.y = this.height / 4 + 96;
			options.setWidth(200);
			*/
			
			// Demicrosofted menu
			children.remove(buttons.remove(3));
			children.remove(buttons.remove(3));
			
			buttons.get(0).setMessage(new TranslatableText("menu.antiquated.returnToGame"));
			buttons.get(5).setMessage(new TranslatableText(MinecraftClient.getInstance().isInSingleplayer() ? "menu.antiquated.returnToMenu" : "menu.antiquated.disconnect"));
		}
	}
	
	@Inject(at=@At("TAIL"), method="render")
	public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		if (showMenu && AntiquatedClient.isInAntiqueBiome()) {
			this.title = new TranslatableText("menu.antiquated.game");
			// can't check if the game is saving easily
			if (savingTicks < 60) {
				int shade = (int)(255.0f * (MathHelper.sin((savingTicks % 10 + partialTicks) / 10.0f * 3.1415927f * 2.0f) * 0.2f + 0.8f));
				textRenderer.drawWithShadow(matrices, "Saving level..", 8, this.height - 16, shade << 16 | shade << 8 | shade);
			}
		}
	}
	
	@Inject(at=@At("TAIL"), method="tick")
	public void tick(CallbackInfo ci) {
		savingTicks++;
	}
	
}
