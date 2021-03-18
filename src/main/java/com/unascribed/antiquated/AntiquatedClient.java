package com.unascribed.antiquated;

import com.unascribed.antiquated.client.AntiqueChickenRenderer;
import com.unascribed.antiquated.client.AntiqueCowRenderer;
import com.unascribed.antiquated.client.AntiqueCreeperRenderer;
import com.unascribed.antiquated.client.AntiquePigRenderer;
import com.unascribed.antiquated.client.AntiqueSheepRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonArrowRenderer;
import com.unascribed.antiquated.client.AntiqueSkeletonRenderer;
import com.unascribed.antiquated.client.AntiqueSpiderRenderer;
import com.unascribed.antiquated.client.AntiqueZombieRenderer;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.AFluids;
import com.unascribed.antiquated.init.AItems;
import com.unascribed.antiquated.init.AScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class AntiquatedClient implements ClientModInitializer {

	public static boolean forceSkyLight = false;
	public static float forcedSkyLight;

	public static float lastLightmapSkyLight = -1;
	public static double lastGamma = -1;
	
	private static boolean antiqueWorld = false;
	
	private static final Identifier WATER_FLOW = new Identifier("antiquated", "block/water_flow");
	private static final Identifier WATER_STILL = new Identifier("antiquated", "block/water_still");
	
	private static final Identifier LAVA_FLOW = new Identifier("antiquated", "block/lava_flow");
	private static final Identifier LAVA_STILL = new Identifier("antiquated", "block/lava_still");

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
				ABlocks.LEAVES,
				ABlocks.CACTUS,
				ABlocks.LEAVES_OLD,
				ABlocks.SAPLING,
				ABlocks.SAPLING_OLD,
				ABlocks.ROSE,
				ABlocks.DANDELION,
				ABlocks.RED_MUSHROOM,
				ABlocks.BROWN_MUSHROOM,
				ABlocks.DOOR,
				ABlocks.REEDS,
				ABlocks.GLASS
		);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
				ABlocks.WATER, ABlocks.ICE);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
				AFluids.WATER, AFluids.FLOWING_WATER);
		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			if (isInCursedAntiqueBiome() && !MinecraftClient.getInstance().options.debugEnabled) {
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
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(WATER_STILL);
			registry.register(WATER_FLOW);
			registry.register(LAVA_STILL);
			registry.register(LAVA_FLOW);
		});
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
			out.accept(new ModelIdentifier("antiquated:cobblestone_stairs_gui#inventory"));
			out.accept(new ModelIdentifier("antiquated:wood_stairs_gui#inventory"));
		});
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.PIG, (erd, ctx) -> new AntiquePigRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.COW, (erd, ctx) -> new AntiqueCowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.ARROW, (erd, ctx) -> new ArrowEntityRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON_ARROW, (erd, ctx) -> new AntiqueSkeletonArrowRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SKELETON, (erd, ctx) -> new AntiqueSkeletonRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.ZOMBIE, (erd, ctx) -> new AntiqueZombieRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SPIDER, (erd, ctx) -> new AntiqueSpiderRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.CREEPER, (erd, ctx) -> new AntiqueCreeperRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.SHEEP, (erd, ctx) -> new AntiqueSheepRenderer(erd));
		EntityRendererRegistry.INSTANCE.register(AEntityTypes.CHICKEN, (erd, ctx) -> new AntiqueChickenRenderer(erd));
		
		ArmorRenderingRegistry.registerSimpleTexture(new Identifier("antiquated", "cloth"),
				AItems.LEATHER_HELMET, AItems.LEATHER_CHESTPLATE, AItems.LEATHER_LEGGINGS, AItems.LEATHER_BOOTS);
		ArmorRenderingRegistry.registerSimpleTexture(new Identifier("antiquated", "studded"),
				AItems.STUDDED_HELMET, AItems.STUDDED_CHESTPLATE, AItems.STUDDED_LEGGINGS, AItems.STUDDED_BOOTS);
		
		FluidRenderHandler waterRenderHandler = (view, pos, state) -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			return new Sprite[] {
				mc.getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(WATER_STILL),
				mc.getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(WATER_FLOW)
			};
		};
		FluidRenderHandlerRegistry.INSTANCE.register(AFluids.WATER, waterRenderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(AFluids.FLOWING_WATER, waterRenderHandler);
		
		FluidRenderHandler lavaRenderHandler = (view, pos, state) -> {
			MinecraftClient mc = MinecraftClient.getInstance();
			return new Sprite[] {
				mc.getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(LAVA_STILL),
				mc.getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(LAVA_FLOW)
			};
		};
		FluidRenderHandlerRegistry.INSTANCE.register(AFluids.LAVA, lavaRenderHandler);
		FluidRenderHandlerRegistry.INSTANCE.register(AFluids.FLOWING_LAVA, lavaRenderHandler);
		
		ScreenRegistry.register(AScreenHandlerTypes.ANTIQUE_CHEST, GenericContainerScreen::new);
		ScreenRegistry.register(AScreenHandlerTypes.ANTIQUE_DOUBLE_CHEST, GenericContainerScreen::new);
		
		ClientPlayNetworking.registerGlobalReceiver(new Identifier("antiquated", "is_antique_world"), (client, handler, buf, responseSender) -> {
			antiqueWorld = true;
		});
	}

	public static boolean isInAntiqueBiome() {
		return Antiquated.isInAntiqueBiome(MinecraftClient.getInstance().player);
	}
	
	public static boolean isInCursedAntiqueBiome() {
		return Antiquated.isInCursedAntiqueBiome(MinecraftClient.getInstance().player);
	}

	public static boolean isAntiqueWorld() {
		return MinecraftClient.getInstance().world.getRegistryKey().getValue().toString().equals("minecraft:overworld") && antiqueWorld;
	}
	
}
