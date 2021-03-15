package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

public class AntiqueSkeletonArrowRenderer extends ArrowEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/skeleton_arrow.png");

	public AntiqueSkeletonArrowRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(ArrowEntity arrowEntity) {
		return TEXTURE;
	}

}
