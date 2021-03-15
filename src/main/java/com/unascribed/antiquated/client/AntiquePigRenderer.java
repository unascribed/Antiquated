package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;

public class AntiquePigRenderer extends PigEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/pig.png");

	public AntiquePigRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(PigEntity pigEntity) {
		return TEXTURE;
	}

}
