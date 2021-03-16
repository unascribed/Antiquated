package com.unascribed.antiquated.mixin;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;
import com.unascribed.antiquated.AntiquatedClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

	private static final Identifier SUN = new Identifier("antiquated", "textures/misc/sun.png");
	private static final Identifier MOON = new Identifier("antiquated", "textures/misc/moon.png");
	
	@Inject(at=@At("HEAD"), method="renderSky", cancellable=true)
	public void renderSky(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		if (AntiquatedClient.isInAntiqueBiome()) {
			GlStateManager.pushMatrix();
			GlStateManager.multMatrix(matrices.peek().getModel());
			if (!AntiquatedClient.isAntiqueWorld()) {
				// in Alpha, the sun rose in the north; this was corrected later
				// if we're in a world that isn't *just* antique biomes, follow this change
				// for seed parity and reproducing scenes as seen in old worlds we only do this in mixed worlds
				GlStateManager.rotatef(-90, 0, 1, 0);
			}
			renderAlphaSky(tickDelta);
			GlStateManager.popMatrix();
			ci.cancel();
		}
	}
	
	// this.field_1021_D = 8961023L;
    // this.field_1020_E = 12638463L;
    // this.field_1019_F = 16777215L;
	
	@Unique
	private int alphaSkyDisplayLists = -1;
	
	@Unique
	public Vec3d func_626_b(final float n) {
        float n2 = MathHelper.cos(this.func_619_c(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (n2 < 0.0f) {
            n2 = 0.0f;
        }
        if (n2 > 1.0f) {
            n2 = 1.0f;
        }
        return new Vec3d((8961023L >> 16 & 0xFFL) / 255.0f * n2, (8961023L >> 8 & 0xFFL) / 255.0f * n2, (8961023L & 0xFFL) / 255.0f * n2);
    }
    
	@Unique
    public float func_619_c(final float n) {
        float n2 = ((int)(MinecraftClient.getInstance().world.getTimeOfDay() % 24000L) + n) / 24000.0f - 0.25f;
        if (n2 < 0.0f) {
            ++n2;
        }
        if (n2 > 1.0f) {
            --n2;
        }
        final float n3 = n2;
        return n3 + (1.0f - (float)((Math.cos(n2 * 3.141592653589793) + 1.0) / 2.0) - n3) / 3.0f;
    }
	
	@Unique
	public float func_679_f(final float n) {
        float n2 = 1.0f - (MathHelper.cos(func_619_c(n) * 3.1415927f * 2.0f) * 2.0f + 0.75f);
        if (n2 < 0.0f) {
            n2 = 0.0f;
        }
        if (n2 > 1.0f) {
            n2 = 1.0f;
        }
        return n2 * n2 * 0.5f;
    }
	
	private void func_950_f() {
        final Random random = new Random(10842L);
        GL11.glBegin(GL11.GL_QUADS);
        for (int i = 0; i < 1500; ++i) {
            final double n = random.nextFloat() * 2.0f - 1.0f;
            final double n2 = random.nextFloat() * 2.0f - 1.0f;
            final double n3 = random.nextFloat() * 2.0f - 1.0f;
            final double n4 = 0.25f + random.nextFloat() * 0.25f;
            final double n5 = n * n + n2 * n2 + n3 * n3;
            if (n5 < 1.0 && n5 > 0.01) {
                final double n6 = 1.0 / Math.sqrt(n5);
                final double n7 = n * n6;
                final double n8 = n2 * n6;
                final double n9 = n3 * n6;
                final double n10 = n7 * 100.0;
                final double n11 = n8 * 100.0;
                final double n12 = n9 * 100.0;
                final double atan2 = Math.atan2(n7, n9);
                final double sin = Math.sin(atan2);
                final double cos = Math.cos(atan2);
                final double atan3 = Math.atan2(Math.sqrt(n7 * n7 + n9 * n9), n8);
                final double sin2 = Math.sin(atan3);
                final double cos2 = Math.cos(atan3);
                final double n13 = random.nextDouble() * 3.141592653589793 * 2.0;
                final double sin3 = Math.sin(n13);
                final double cos3 = Math.cos(n13);
                for (int j = 0; j < 4; ++j) {
                    final double n14 = 0.0;
                    final double n15 = ((j & 0x2) - 1) * n4;
                    final double n16 = ((j + 1 & 0x2) - 1) * n4;
                    final double n17 = n14;
                    final double n18 = n15 * cos3 - n16 * sin3;
                    final double n19 = n16 * cos3 + n15 * sin3;
                    final double n20 = n18 * sin2 + n17 * cos2;
                    final double n21 = n17 * sin2 - n18 * cos2;
                    GL11.glVertex3d(n10 + (n21 * sin - n19 * cos), n11 + n20, n12 + (n19 * sin + n21 * cos));
                }
            }
        }
        GL11.glEnd();
    }
	
	@Unique
	public void renderAlphaSky(final float n) {
		if (alphaSkyDisplayLists == -1) {
			alphaSkyDisplayLists = GL11.glGenLists(3);
			GL11.glNewList(alphaSkyDisplayLists, GL11.GL_COMPILE);
			final int n2 = 64;
			final int n3 = 256 / n2 + 2;
			final float n4 = 16.0f;
			GL11.glBegin(GL11.GL_QUADS);
			for (int i = -n2 * n3; i <= n2 * n3; i += n2) {
				for (int j = -n2 * n3; j <= n2 * n3; j += n2) {
					GL11.glVertex3f(i + 0, n4, j + 0);
					GL11.glVertex3f(i + n2, n4, j + 0);
					GL11.glVertex3f(i + n2, n4, j + n2);
					GL11.glVertex3f(i + 0, n4, j + n2);
				}
			}
			GL11.glEnd();
			GL11.glEndList();
			GL11.glPushMatrix();
	        GL11.glNewList(alphaSkyDisplayLists+1, GL11.GL_COMPILE);
	        this.func_950_f();
	        GL11.glEndList();
	        GL11.glPopMatrix();
	        GL11.glNewList(alphaSkyDisplayLists+2, GL11.GL_COMPILE);
	        final float n5 = -16.0f;
	        GL11.glBegin(GL11.GL_QUADS);
	        for (int k = -n2 * n3; k <= n2 * n3; k += n2) {
	            for (int l = -n2 * n3; l <= n2 * n3; l += n2) {
	                GL11.glVertex3f(k + n2, n5, l + 0);
	                GL11.glVertex3f(k + 0, n5, l + 0);
	                GL11.glVertex3f(k + 0, n5, l + n2);
	                GL11.glVertex3f(k + n2, n5, l + n2);
	            }
	        }
	        GL11.glEnd();
	        GL11.glEndList();
		}
        GlStateManager.disableTexture();
        final Vec3d func_626_b = func_626_b(n);
        float n2 = (float)func_626_b.x;
        float n3 = (float)func_626_b.y;
        float n4 = (float)func_626_b.z;
//        if (this.field_1439_t.options.anaglyph) {
//            final float n5 = (n2 * 30.0f + n3 * 59.0f + n4 * 11.0f) / 100.0f;
//            final float n6 = (n2 * 30.0f + n3 * 70.0f) / 100.0f;
//            final float n7 = (n2 * 30.0f + n4 * 70.0f) / 100.0f;
//            n2 = n5;
//            n3 = n6;
//            n4 = n7;
//        }
        GlStateManager.color4f(n2, n3, n4, 1);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bb = tess.getBuffer();
        GlStateManager.depthMask(false);
        GlStateManager.enableFog();
        GL11.glCallList(alphaSkyDisplayLists);
        GlStateManager.enableTexture();
        GlStateManager.disableFog();
        GlStateManager.disableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.pushMatrix();
        final float n8 = 0.0f;
        final float n9 = 0.0f;
        final float n10 = 0.0f;
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.translatef(n8, n9, n10);
        GlStateManager.rotatef(0.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotatef(MinecraftClient.getInstance().world.getSkyAngle(n) * 360.0f, 1.0f, 0.0f, 0.0f);
        final float n11 = 30.0f;
        MinecraftClient.getInstance().getTextureManager().bindTexture(SUN);
        bb.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
        bb.vertex(-n11, 100.0, -n11).texture(0.0f, 0.0f).next();
        bb.vertex(n11, 100.0, -n11).texture(1.0f, 0.0f).next();
        bb.vertex(n11, 100.0, n11).texture(1.0f, 1.0f).next();
        bb.vertex(-n11, 100.0, n11).texture(0.0f, 1.0f).next();
        tess.draw();
        final float n12 = 20.0f;
        MinecraftClient.getInstance().getTextureManager().bindTexture(MOON);
        bb.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
        bb.vertex(-n12, -100.0, n12).texture(1.0f, 1.0f).next();
        bb.vertex(n12, -100.0, n12).texture(0.0f, 1.0f).next();
        bb.vertex(n12, -100.0, -n12).texture(0.0f, 0.0f).next();
        bb.vertex(-n12, -100.0, -n12).texture(1.0f, 0.0f).next();
        tess.draw();
        GlStateManager.disableTexture();
        final float func_679_f = func_679_f(n);
        if (func_679_f > 0.0f) {
            GlStateManager.color4f(func_679_f, func_679_f, func_679_f, func_679_f);
            GL11.glCallList(alphaSkyDisplayLists+1);
        }
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.enableFog();
        GlStateManager.popMatrix();
        GlStateManager.color4f(n2 * 0.2f + 0.04f, n3 * 0.2f + 0.04f, n4 * 0.6f + 0.1f, 1);
        GlStateManager.disableTexture();
        GL11.glCallList(alphaSkyDisplayLists+2);
        GlStateManager.enableTexture();
        GlStateManager.depthMask(true);
    }
	
}
