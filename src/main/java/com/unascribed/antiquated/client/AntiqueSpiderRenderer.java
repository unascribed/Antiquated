package com.unascribed.antiquated.client;

import com.unascribed.antiquated.entity.AntiqueSpiderEntity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.util.Identifier;

public class AntiqueSpiderRenderer extends SpiderEntityRenderer<AntiqueSpiderEntity> {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/spider.png");

	public AntiqueSpiderRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
		features.clear();
		addFeature(new AntiqueSpiderEyesFeatureRenderer(this));
	}
	
	@Override
	public Identifier getTexture(AntiqueSpiderEntity spiderEntity) {
		return TEXTURE;
	}

}
