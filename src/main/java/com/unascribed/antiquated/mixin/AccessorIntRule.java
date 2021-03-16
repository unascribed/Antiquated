package com.unascribed.antiquated.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.GameRules;

@Mixin(GameRules.IntRule.class)
public interface AccessorIntRule {

	@Accessor("value")
	void antiquated$setValue(int value);
	
}
