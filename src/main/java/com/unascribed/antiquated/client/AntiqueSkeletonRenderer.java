package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class AntiqueSkeletonRenderer extends SkeletonEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/skeleton.png");

	public AntiqueSkeletonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(AbstractSkeletonEntity skeletonEntity) {
		return TEXTURE;
	}

}
