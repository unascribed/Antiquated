package com.unascribed.antiquated.mixin;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.NVFogDistance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {

	@ModifyConstant(constant=@Constant(floatValue=0.75f), method="applyFog")
	private static float modifyFogStartMultiplier(float orig) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			return 0.25f;
		}
		return orig;
	}
	
	@Inject(at=@At(value="TAIL"), method="applyFog")
	private static void applyFogTail(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome() && GL.getCapabilities().GL_NV_fog_distance) {
			// disable the nicer radial fog
			GlStateManager.fogi(NVFogDistance.GL_FOG_DISTANCE_MODE_NV, NVFogDistance.GL_EYE_PLANE_ABSOLUTE_NV);
		}
	}
	
}
