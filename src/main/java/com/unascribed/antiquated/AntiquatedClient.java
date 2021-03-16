package com.unascribed.antiquated;

import com.unascribed.antiquated.client.AntiqueCowRenderer;
import com.unascribed.antiquated.client.AntiquePigRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonArrowRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonRenderer;
import com.unascribed.antiquated.client.AntiqueSpiderRenderer;
import com.unascribed.antiquated.client.AntiqueZombieRenderer;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.AScreenHandlerTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class AntiquatedClient implements ClientModInitializer {

	public static boolean forceSkyLight = false;
	public static float forcedSkyLight;

	public static float lastLightmapSkyLight = -1;
	public static double lastGamma = -1;
	
	private static boolean antiqueWorld = false;

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				ABlocks.LEAVES,
				ABlocks.CACTUS,
				ABlocks.LEAVES_OLD,
				ABlocks.SAPLING,
				ABlocks.SAPLING_OLD,
				ABlocks.ROSE,
				ABlocks.DANDELION);
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (isInAntiqueBiome() && !MinecraftClient.getInstance().options.debugEnabled) {
				MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, "Minecraft v"+SharedConstants.getGameVersion().getName(), 2, 2, -1);
			}
		});
		ClientTickEvents.START_CLIENT_TICK.register((mc) -> {
			lastLightmapSkyLight = -1;
			lastGamma = -1;
			if (mc.world == null) {
				antiqueWorld = false;
			}
		});
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.PIG, (erd, ctx) -> new AntiquePigRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.COW, (erd, ctx) -> new AntiqueCowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON_ARROW, (erd, ctx) -> new AntiqueSkeletonArrowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON, (erd, ctx) -> new AntiqueSkeletonRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.ZOMBIE, (erd, ctx) -> new AntiqueZombieRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SPIDER, (erd, ctx) -> new AntiqueSpiderRenderer(erd));
		
		ScreenRegistry.register(AScreenHandlerTypes.ANTIQUE_CHEST, GenericContainerScreen::new);
		ScreenRegistry.register(AScreenHandlerTypes.ANTIQUE_DOUBLE_CHEST, GenericContainerScreen::new);
		
		ClientPlayNetworking.registerGlobalReceiver(new Identifier("antiquated", "is_antique_world"), (client, handler, buf, responseSender) -> {
			antiqueWorld = true;
		});
	}

	public static boolean isInAntiqueBiome() {
		return Antiquated.isInAntiqueBiome(MinecraftClient.getInstance().player);
	}

	public static boolean isAntiqueWorld() {
		return MinecraftClient.getInstance().world.getRegistryKey().getValue().toString().equals("minecraft:overworld") && antiqueWorld;
	}
	
}
