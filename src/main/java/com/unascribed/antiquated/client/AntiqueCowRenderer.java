package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.CowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;

public class AntiqueCowRenderer extends CowEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/cow.png");

	public AntiqueCowRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(CowEntity cowEntity) {
		return TEXTURE;
	}

}
