package com.unascribed.antiquated.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SheepEntityRenderer;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;

public class AntiqueSheepRenderer extends SheepEntityRenderer {
	
	private static final Identifier TEXTURE = new Identifier("antiquated", "textures/entity/sheep.png");
	private static final Identifier TEXTURE_WOOL = new Identifier("antiquated", "textures/entity/sheep_fur.png");

	public AntiqueSheepRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
		this.features.clear();
		this.addFeature(new SheepWoolFeatureRenderer(this) {
			private final SheepWoolEntityModel<SheepEntity> model = new SheepWoolEntityModel<>();
			@Override
			public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l) {
				if (!sheepEntity.isSheared() && !sheepEntity.isInvisible()) {
					render(this.getContextModel(), this.model, TEXTURE_WOOL, matrixStack, vertexConsumerProvider, i, sheepEntity, f, g, j, k, l, h, 1, 1, 1);
				}
			}
		});
	}
	
	@Override
	public Identifier getTexture(SheepEntity sheepEntity) {
		return TEXTURE;
	}

}
