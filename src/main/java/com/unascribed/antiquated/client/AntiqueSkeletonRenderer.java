package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class AntiqueSkeletonRenderer extends SkeletonEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/skeleton.png");

	public AntiqueSkeletonRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
		model = new SkeletonEntityModel<AbstractSkeletonEntity>() {
			
			@Override
			public void setAngles(AbstractSkeletonEntity mobEntity, float f, float g, float h, float i, float j) {
				CrossbowPosing.method_29352(this.leftArm, this.rightArm, false, this.handSwingProgress, h);
			}
			
		};
	}
	
	@Override
	public Identifier getTexture(AbstractSkeletonEntity skeletonEntity) {
		return TEXTURE;
	}
	
}
