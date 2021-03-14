package com.unascribed.antiquated;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import com.unascribed.antiquated.init.ABiomes;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.ASounds;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class Antiquated implements ModInitializer {

	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier("antiquated", "main"))
			.icon(() -> new ItemStack(ABlocks.GRASS))
			.build();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {
		register(ASounds.class, ABlocks.class, ABiomes.class);
		
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "valley")), OverworldClimate.TEMPERATE, 0.2);
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "tundra")), OverworldClimate.SNOWY, 0.1);
		
		ServerTickEvents.START_WORLD_TICK.register(world -> {
			for (ServerPlayerEntity pe : world.getPlayers()) {
				if (isInAntiqueBiome(pe) && !pe.getOffHandStack().isEmpty()) {
					pe.dropItem(pe.getOffHandStack(), true);
					pe.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
				}
			}
		});
	}
	
	public static boolean isInAntiqueBiome(Entity e) {
		Identifier id = e.getEntityWorld().getRegistryManager().get(Registry.BIOME_KEY).getId(e.getEntityWorld().getBiome(e.getBlockPos()));
		return id != null && id.getNamespace().equals("antiquated");
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
							Registry.register(Registry.ITEM, "antiquated:"+name, new BlockItem(b, new Item.Settings().group(GROUP)));
						}
					} else if (Item.class.isAssignableFrom(f.getType())) {
						Registry.register(Registry.ITEM, "antiquated:"+name, (Item)f.get(null));
					} else if (f.getType() == SoundEvent.class) {
						Registry.register(Registry.SOUND_EVENT, "antiquated:"+name, (SoundEvent)f.get(null));
					} else if (f.getType() == Biome.class) {
						Registry.register(BuiltinRegistries.BIOME, "antiquated:"+name, (Biome)f.get(null));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
