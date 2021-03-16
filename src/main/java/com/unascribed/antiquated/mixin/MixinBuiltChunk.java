package com.unascribed.antiquated.mixin;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.AntiquatedClient;

import com.google.common.hash.Hashing;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.chunk.ChunkBuilder.BuiltChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Mixin(BuiltChunk.class)
public class MixinBuiltChunk {
	
	@Shadow @Final
	private BlockPos.Mutable origin;

	@Unique
	private float skyLightLag;
	@Unique
	private long skyLightUpdate;
	@Unique
	private int skyLightRandom = -1;
	
	@Inject(at=@At("HEAD"), method="getBuffer")
	public void getBuffer(RenderLayer layer, CallbackInfoReturnable<VertexBuffer> ci) {
		if (!RenderSystem.isOnRenderThread()) return;
		double targetGamma = MinecraftClient.getInstance().options.gamma;
		float targetSkyLight = MinecraftClient.getInstance().world.method_23783(0);
		// this is called before rendering, so it's a good time for us to hack things
		if (Antiquated.isAntiqueBiome(MinecraftClient.getInstance().world, MinecraftClient.getInstance().world.getBiome(origin.add(4, 4, 4)))) {
			// max out gamma at +30%
			targetGamma = Math.min(targetGamma, 0.3);
			targetSkyLight = ((int)(targetSkyLight*15)/15f);
			if (skyLightRandom == -1) {
				skyLightRandom = (Hashing.murmur3_32().newHasher().putInt(origin.getX()).putInt(origin.getZ()).hash().asInt()&Integer.MAX_VALUE)%100;
				skyLightLag = targetSkyLight;
				skyLightUpdate = System.currentTimeMillis();
			} else {
				double distance = MathHelper.sqrt(MinecraftClient.getInstance().player.squaredDistanceTo(origin.getX(), MinecraftClient.getInstance().player.getPos().y, origin.getZ()))/16;
				long delay = (long) ((distance*80)+(skyLightRandom));
				if (System.currentTimeMillis()-skyLightUpdate > delay) {
					skyLightLag = targetSkyLight;
					skyLightUpdate = System.currentTimeMillis();
				}
			}
			if (targetSkyLight == skyLightLag) {
				skyLightUpdate = System.currentTimeMillis();
			}
			targetSkyLight = skyLightLag;
			// disable mipmapping on the world for that true alpha crunch
			GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} else {
			GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
			GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}
		double origGamma = MinecraftClient.getInstance().options.gamma;
		AntiquatedClient.forceSkyLight = true;
		try {
			MinecraftClient.getInstance().options.gamma = targetGamma;
			AntiquatedClient.forcedSkyLight = targetSkyLight;
			if (targetSkyLight != AntiquatedClient.lastLightmapSkyLight || targetGamma != AntiquatedClient.lastGamma) {
				int boundTexture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
				LightmapTextureManager ltm = MinecraftClient.getInstance().gameRenderer.getLightmapTextureManager();
				ltm.tick();
				((AccessorLightmapTextureManager)ltm).antiquated$setTorchFlicker(0);
				ltm.update(0);
				// no idea why this is being messed up! if i don't do this, the lighting gets banding artifacts
				GlStateManager.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GlStateManager.bindTexture(boundTexture);
				AntiquatedClient.lastLightmapSkyLight = targetSkyLight;
				AntiquatedClient.lastGamma = MinecraftClient.getInstance().options.gamma;
			}
		} finally {
			AntiquatedClient.forceSkyLight = false;
			MinecraftClient.getInstance().options.gamma = origGamma;
		}
	}

}
