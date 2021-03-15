package com.unascribed.antiquated.client;

import com.unascribed.antiquated.entity.AntiqueSpiderEntity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.util.Identifier;

public class AntiqueSpiderEyesFeatureRenderer extends EyesFeatureRenderer<AntiqueSpiderEntity, SpiderEntityModel<AntiqueSpiderEntity>> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier("antiquated", "textures/entity/spider_eyes.png"));
	
	public AntiqueSpiderEyesFeatureRenderer(FeatureRendererContext<AntiqueSpiderEntity, SpiderEntityModel<AntiqueSpiderEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
	
	@Override
	public RenderLayer getEyesTexture() {
		return SKIN;
	}

}
