package com.unascribed.antiquated.item;

import com.unascribed.antiquated.entity.AntiqueArrowEntity;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.ASounds;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AntiqueBowItem extends Item {

	public AntiqueBowItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		int slot = user.inventory.getSlotWithStack(new ItemStack(Items.ARROW));
		if (slot != -1) {
			user.inventory.getStack(slot).decrement(1);
			user.playSound(ASounds.BOW, 1, 1.0f / (RANDOM.nextFloat() * 0.4f + 0.8f));
			if (!world.isClient) {
				stack.damage(1, user, (e) -> {
					e.sendEquipmentBreakStatus(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
				});
				AntiqueArrowEntity arrow = AEntityTypes.ARROW.create(world);
				arrow.pickupType = PickupPermission.ALLOWED;
				arrow.refreshPositionAndAngles(
						user.getPos().x - MathHelper.cos(user.getYaw(1) / 180.0f * 3.1415927f) * 0.3f,
						user.getPos().y + user.getEyeHeight(user.getPose()) - 0.2,
						user.getPos().z - MathHelper.sin(user.getYaw(1) / 180.0f * 3.1415927f) * 0.3f,
						user.getYaw(1), user.getPitch(1));
				arrow.setVelocity(
						-MathHelper.sin(user.getYaw(1) / 180.0f * 3.1415927f) * MathHelper.cos(user.getPitch(1) / 180.0f * 3.1415927f),
						-MathHelper.sin(user.getPitch(1) / 180.0f * 3.1415927f),
						MathHelper.cos(user.getYaw(1) / 180.0f * 3.1415927f) * MathHelper.cos(user.getPitch(1) / 180.0f * 3.1415927f),
						1.5f, 1.0f);
				world.spawnEntity(arrow);
			}
			return TypedActionResult.success(stack, false);
		}
		return TypedActionResult.pass(stack);
	}

}
