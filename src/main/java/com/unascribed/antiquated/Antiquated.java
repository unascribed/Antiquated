package com.unascribed.antiquated;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Set;

import com.unascribed.antiquated.init.ABiomes;
import com.unascribed.antiquated.init.ABlockEntityTypes;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.AItems;
import com.unascribed.antiquated.init.ASounds;
import com.unascribed.antiquated.mixin.AccessorBlockEntityType;

import com.google.common.collect.Sets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Antiquated implements ModInitializer {

	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier("antiquated", "main"))
			.icon(() -> new ItemStack(ABlocks.GRASS))
			.build();
	
	public static WeakReference<MinecraftServer> serverForHouseAdvancement;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {
		register(ASounds.class, ABlocks.class, ABiomes.class, AEntityTypes.class, AItems.class, ABlockEntityTypes.class);
		
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "valley")), OverworldClimate.TEMPERATE, 0.2);
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "tundra")), OverworldClimate.SNOWY, 0.1);
		
		ServerTickEvents.START_WORLD_TICK.register(world -> {
			for (ServerPlayerEntity pe : world.getPlayers()) {
				if (isInAntiqueBiome(pe)) {
					if (serverForHouseAdvancement != null && serverForHouseAdvancement.get() == pe.getServer()) {
						serverForHouseAdvancement = null;
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "house")), "minecraft:impossible");
					}
					if (!pe.getOffHandStack().isEmpty()) {
						pe.dropItem(pe.getOffHandStack(), true);
						pe.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "lose_offhand")), "minecraft:impossible");
					}
					if (pe.isSprinting()) {
						pe.setSprinting(false);
					}
					pe.clearStatusEffects();
				}
			}
		});
		
		FabricDefaultAttributeRegistry.register(AEntityTypes.PIG, PigEntity.createPigAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.COW, CowEntity.createCowAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.ZOMBIE, ZombieEntity.createZombieAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.SPIDER, SpiderEntity.createSpiderAttributes());
		
		Set<Block> newFurnaceBlocks = Sets.newHashSet(((AccessorBlockEntityType)BlockEntityType.FURNACE).antiquated$getBlocks());
		newFurnaceBlocks.add(ABlocks.FURNACE);
		((AccessorBlockEntityType)BlockEntityType.FURNACE).antiquated$setBlocks(newFurnaceBlocks);
	}
	
	public static boolean isAntiqueBiome(World world, Biome biome) {
		Identifier id = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
		return id != null && id.getNamespace().equals("antiquated");
	}
	
	public static boolean isInAntiqueBiome(World world, BlockPos pos) {
		return isAntiqueBiome(world, world.getBiome(pos));
	}
	
	public static boolean isInAntiqueBiome(Entity e) {
		if (e == null) return false;
		return isInAntiqueBiome(e.world, e.getBlockPos());
	}

	private void register(Class<?>... clazzes) {
		for (Class<?> clazz : clazzes) {
			for (Field f : clazz.getFields()) {
				if (!Modifier.isStatic(f.getModifiers()) || !Modifier.isFinal(f.getModifiers())) continue;
				String name = f.getName().toLowerCase(Locale.ROOT);
				try {
					if (Block.class.isAssignableFrom(f.getType())) {
						Block b = (Block)f.get(null);
						Registry.register(Registry.BLOCK, "antiquated:"+name, b);
						if (f.getAnnotation(Technical.class) == null) {
							Registry.register(Registry.ITEM, "antiquated:"+name, new BlockItem(b, new Item.Settings()
									.group(GROUP)));
						}
					} else if (Item.class.isAssignableFrom(f.getType())) {
						Registry.register(Registry.ITEM, "antiquated:"+name, (Item)f.get(null));
					} else if (f.getType() == SoundEvent.class) {
						Registry.register(Registry.SOUND_EVENT, "antiquated:"+name, (SoundEvent)f.get(null));
					} else if (f.getType() == Biome.class) {
						Registry.register(BuiltinRegistries.BIOME, "antiquated:"+name, (Biome)f.get(null));
					} else if (f.getType() == EntityType.class) {
						Registry.register(Registry.ENTITY_TYPE, "antiquated:"+name, (EntityType<?>)f.get(null));
					} else if (f.getType() == BlockEntityType.class) {
						Registry.register(Registry.BLOCK_ENTITY_TYPE, "antiquated:"+name, (BlockEntityType<?>)f.get(null));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
