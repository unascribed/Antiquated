package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;

public class AntiqueChickenRenderer extends ChickenEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/chicken.png");

	public AntiqueChickenRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(ChickenEntity chickenEntity) {
		return TEXTURE;
	}

}
