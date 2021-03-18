package com.unascribed.antiquated.mixin;

import net.minecraft.entity.mob.CreeperEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreeperEntity.class)
public interface AccessorCreeperEntity {

	@Invoker("explode")
	void antiquated$explode();
	
}
