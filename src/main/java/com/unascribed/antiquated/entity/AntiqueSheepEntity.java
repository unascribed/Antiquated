package com.unascribed.antiquated.entity;

import com.unascribed.antiquated.Antiquated;
import com.unascribed.antiquated.ai.AntiqueAI;
import com.unascribed.antiquated.ai.AntiqueAnimalAI;
import com.unascribed.antiquated.ai.DummyJumpControl;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AntiqueSheepEntity extends SheepEntity implements AntiqueCreature, AntiqueSpawnable {

	private AntiqueAI ai;

	public AntiqueSheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
		super(entityType, world);
		jumpControl = new DummyJumpControl();
	}
	
	@Override
	protected void dropXp() {
	}
	
	@Override
	protected void initGoals() {
		goalSelector.add(0, ai = new AntiqueAnimalAI(this));
	}
	
	@Override
	public boolean handleAttack(Entity attacker) {
		if (world.isClient) return false;
		if (!isSheared() && attacker instanceof LivingEntity) {
			setSheared(true);
			for (int n2 = 1 + world.random.nextInt(3), i = 0; i < n2; ++i) {
				ItemEntity item = dropItem(Blocks.WHITE_WOOL, 1);
				item.setVelocity(item.getVelocity().add(
					(world.random.nextFloat() - world.random.nextFloat()) * 0.1f,
					world.random.nextFloat() * 0.05f,
					(world.random.nextFloat() - world.random.nextFloat()) * 0.1f
				));
			}
		}
		return false;
	}
	
	@Override
	protected void mobTick() {
	}
	
	@Override
	public Identifier getLootTableId() {
		return this.getType().getLootTableId();
	}
	
	@Override
	public boolean isShearable() {
		return false;
	}
	
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		return ActionResult.FAIL;
	}
	
	@Override
	public DyeColor getColor() {
		return DyeColor.WHITE;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		Antiquated.playDefaultStepSound(this, pos, state);
	}
	
	@Override
	public boolean canBreedWith(AnimalEntity other) {
		return false;
	}
	
	@Override
	public boolean isBaby() {
		return false;
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean canSpawnHere() {
		return ai.canSpawnHere();
	}
	
}
