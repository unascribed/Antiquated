package com.unascribed.antiquated;

import com.unascribed.antiquated.init.ABlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;

public class AntiquatedClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				ABlocks.LEAVES);
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (isInAntiqueBiome() && !MinecraftClient.getInstance().options.debugEnabled) {
				MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, "Minecraft "+SharedConstants.getGameVersion().getName(), 2, 2, -1);
			}
		});
	}

	public static boolean isInAntiqueBiome() {
		return Antiquated.isInAntiqueBiome(MinecraftClient.getInstance().player);
	}
	
}
