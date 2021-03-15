package com.unascribed.antiquated;

import com.unascribed.antiquated.client.AntiqueCowRenderer;
import com.unascribed.antiquated.client.AntiquePigRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonArrowRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonRenderer;
import com.unascribed.antiquated.client.AntiqueSpiderRenderer;
import com.unascribed.antiquated.client.AntiqueZombieRenderer;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.AEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;

public class AntiquatedClient implements ClientModInitializer {

	public static boolean forceSkyLight = false;
	public static float forcedSkyLight;

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				ABlocks.LEAVES,
				ABlocks.CACTUS);
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (isInAntiqueBiome() && !MinecraftClient.getInstance().options.debugEnabled) {
				MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, "Minecraft v"+SharedConstants.getGameVersion().getName(), 2, 2, -1);
			}
		});
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.PIG, (erd, ctx) -> new AntiquePigRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.COW, (erd, ctx) -> new AntiqueCowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON_ARROW, (erd, ctx) -> new AntiqueSkeletonArrowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON, (erd, ctx) -> new AntiqueSkeletonRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.ZOMBIE, (erd, ctx) -> new AntiqueZombieRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SPIDER, (erd, ctx) -> new AntiqueSpiderRenderer(erd));
	}

	public static boolean isInAntiqueBiome() {
		return Antiquated.isInAntiqueBiome(MinecraftClient.getInstance().player);
	}
	
}
