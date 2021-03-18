package com.unascribed.antiquated.client;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class AntiqueZombieRenderer extends ZombieEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/zombie.png");

	public AntiqueZombieRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}
	
	@Override
	public Identifier getTexture(ZombieEntity zombieEntity) {
		return TEXTURE;
	}
	
}
