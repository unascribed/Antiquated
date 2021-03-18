package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

public class AntiqueCreeperRenderer extends CreeperEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/creeper.png");

	public AntiqueCreeperRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(CreeperEntity creeperEntity) {
		return TEXTURE;
	}

}
