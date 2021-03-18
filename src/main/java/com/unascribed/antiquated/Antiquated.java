package com.unascribed.antiquated;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.mutable.MutableInt;
import com.unascribed.antiquated.entity.AntiqueCreature;
import com.unascribed.antiquated.entity.AntiqueMonster;
import com.unascribed.antiquated.init.ABiomes;
import com.unascribed.antiquated.init.ABlockEntityTypes;
import com.unascribed.antiquated.init.ABlocks;
import com.unascribed.antiquated.init.AEnchantments;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.AFluids;
import com.unascribed.antiquated.init.AItems;
import com.unascribed.antiquated.init.AScreenHandlerTypes;
import com.unascribed.antiquated.init.ASounds;
import com.unascribed.antiquated.mixin.AccessorBlockEntityType;
import com.unascribed.antiquated.port.AlphaSpawnerAnimals;
import com.unascribed.antiquated.port.AlphaSpawnerMonsters;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class Antiquated implements ModInitializer {

	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier("antiquated", "main"))
			.icon(() -> new ItemStack(ABlocks.GRASS))
			.build();
	
	public static final Tag<Block> WANTS_XP = TagRegistry.block(new Identifier("antiquated", "wants_xp"));
	public static final Tag<Fluid> ANCIENT_FLUIDS = TagRegistry.fluid(new Identifier("antiquated", "ancient_fluids"));
	
	public static WeakReference<MinecraftServer> serverForHouseAdvancement;

	public static ThreadLocal<MutableInt> increaseStackSize = ThreadLocal.withInitial(() -> new MutableInt(0));
	
	private static final AlphaSpawnerMonsters ALPHA_MONSTERS = new AlphaSpawnerMonsters(60, AntiqueMonster.class,
			AEntityTypes.ZOMBIE, AEntityTypes.SKELETON, AEntityTypes.CREEPER, AEntityTypes.SPIDER);
	private static final AlphaSpawnerAnimals ALPHA_ANIMALS = new AlphaSpawnerAnimals(5, AntiqueCreature.class,
			AEntityTypes.SHEEP, AEntityTypes.PIG, AEntityTypes.COW, AEntityTypes.CHICKEN);
	
	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {
		register(ASounds.class, AFluids.class, ABlocks.class, ABiomes.class, AEntityTypes.class, AItems.class, ABlockEntityTypes.class, AScreenHandlerTypes.class, AEnchantments.class);
		
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "valley")), OverworldClimate.TEMPERATE, 0.2);
		OverworldBiomes.addContinentalBiome(RegistryKey.of(Registry.BIOME_KEY, new Identifier("antiquated", "tundra")), OverworldClimate.SNOWY, 0.1);
		
		ServerTickEvents.START_WORLD_TICK.register(world -> {
			for (ServerPlayerEntity pe : world.getPlayers()) {
				if (isInAntiqueBiome(pe)) {
					if (serverForHouseAdvancement != null && serverForHouseAdvancement.get() == pe.getServer()) {
						serverForHouseAdvancement = null;
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "house")), "minecraft:impossible");
					}
					pe.clearStatusEffects();
				}
				if (isInCursedAntiqueBiome(pe)) {
					if (!pe.getOffHandStack().isEmpty()) {
						ItemStack offhand = pe.getOffHandStack();
						pe.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
						if (pe.getMainHandStack().isEmpty()) {
							pe.setStackInHand(Hand.MAIN_HAND, offhand);
						} else {
							pe.inventory.offerOrDrop(world, offhand);
						}
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "lose_offhand")), "minecraft:impossible");
						// debug code to regenerate your current chunk when you hit the swap hands key with an item held
//						Chunk chunk = world.getChunk(pe.getBlockPos());
//						{
//							AlphaWorld w = new AlphaWorld(world, world.getSeed());
//							w.isolated = Antiquated.isAntiqueWorld(world.getServer().getOverworld());
//							ProtoChunk alphaChunk = new Alpha112ChunkGenerator(w, world.getSeed()).generate(chunk.getPos().x, chunk.getPos().z);
//							for (int x = 0; x < 16; x++) {
//								for (int z = 0; z < 16; z++) {
//									for (int y = 0; y < 128; y++) {
//										chunk.setBlockState(w.mut.set(x, y, z), alphaChunk.getBlockState(w.mut), false);
//									}
//								}
//							}
//							for (Heightmap.Type hm : Heightmap.Type.values()) {
//								chunk.getHeightmap(hm); // ensure it's populated
//							}
//							Heightmap.populateHeightmaps(chunk, EnumSet.allOf(Heightmap.Type.class));
//						}
//						{
//							AlphaWorld w = new AlphaWorld(world, world.getSeed());
//							w.isolated = Antiquated.isAntiqueWorld(world.getServer().getOverworld());
//							new Alpha112ChunkGenerator(w, world.getSeed()).populate(chunk.getPos().x, chunk.getPos().z);
//						}
					}
					if (pe.isSprinting()) {
						pe.setSprinting(false);
					}
				}
			}
			AlphaWorld aw = new AlphaWorld(world, world.getSeed());
			ALPHA_MONSTERS.spawn(aw);
			ALPHA_ANIMALS.spawn(aw);
		});
		
		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof PhantomEntity && isInAntiqueBiome(entity)) {
				entity.remove();
			}
		});
		
		FabricDefaultAttributeRegistry.register(AEntityTypes.PIG, PigEntity.createPigAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.COW, CowEntity.createCowAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.SKELETON, SkeletonEntity.createAbstractSkeletonAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.ZOMBIE, ZombieEntity.createZombieAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.SPIDER, SpiderEntity.createSpiderAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.CREEPER, CreeperEntity.createCreeperAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.SHEEP, SheepEntity.createSheepAttributes());
		FabricDefaultAttributeRegistry.register(AEntityTypes.CHICKEN, ChickenEntity.createChickenAttributes());
		
		Set<Block> newFurnaceBlocks = Sets.newHashSet(((AccessorBlockEntityType)BlockEntityType.FURNACE).antiquated$getBlocks());
		newFurnaceBlocks.add(ABlocks.FURNACE);
		((AccessorBlockEntityType)BlockEntityType.FURNACE).antiquated$setBlocks(newFurnaceBlocks);
	}
	
	public static boolean isAntiqueWorld(World world, BiomeSource src) {
		return Iterables.all(src.getBiomes(), (b) -> isAntiqueBiome(world, b));
	}
	
	public static boolean isAntiqueWorld(ServerWorld world) {
		return isAntiqueWorld(world, world.getChunkManager().getChunkGenerator().getBiomeSource());
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
		if (isInAntiqueBiome(e.world, e.getBlockPos())) return true;
		for (ItemStack is : e.getArmorItems()) {
			if (EnchantmentHelper.getLevel(AEnchantments.LEGACY_CURSE, is) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInCursedAntiqueBiome(World world, BlockPos pos) {
		return isAntiqueBiome(world, world.getBiome(pos)) && isCursed(world, pos);
	}
	
	public static boolean isInCursedAntiqueBiome(Entity e) {
		if (e == null) return false;
		for (ItemStack is : e.getArmorItems()) {
			if (EnchantmentHelper.getLevel(AEnchantments.LEGACY_CURSE, is) > 0) {
				return true;
			}
		}
		return isInAntiqueBiome(e) && isCursed(e.world, e.getBlockPos());
	}
	
	public static boolean isCursed(World world, BlockPos blockPos) {
		ChunkPos cp = new ChunkPos(blockPos);
		Chunk c = world.getChunk(cp.x, cp.z, ChunkStatus.FULL, false);
		return AntiquatedComponents.UNCURSED.get(c).getStrength() <= 0;
	}

	private static final ImmutableMap<Class<?>, Registry> NORMAL_REGISTRIES = ImmutableMap.<Class<?>, Registry>builder()
			.put(Item.class, Registry.ITEM)
			.put(SoundEvent.class, Registry.SOUND_EVENT)
			.put(Biome.class, BuiltinRegistries.BIOME)
			.put(EntityType.class, Registry.ENTITY_TYPE)
			.put(BlockEntityType.class, Registry.BLOCK_ENTITY_TYPE)
			.put(ScreenHandlerType.class, Registry.SCREEN_HANDLER)
			.put(Enchantment.class, Registry.ENCHANTMENT)
			.put(RecipeType.class, Registry.RECIPE_TYPE)
			.put(Fluid.class, Registry.FLUID)
		.build();

	private void register(Class<?>... clazzes) {
		for (Class<?> clazz : clazzes) {
			outer: for (Field f : clazz.getFields()) {
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
					} else if (BlockSoundGroup.class.equals(f.getType())) {
						// doesn't need registration, is held in ASounds for convenience
					} else {
						for (Map.Entry<Class<?>, Registry> en : NORMAL_REGISTRIES.entrySet()) {
							if (en.getKey().isAssignableFrom(f.getType())) {
								Registry.register(en.getValue(), "antiquated:"+name, f.get(null));
								continue outer;
							}
						}
						throw new RuntimeException("Don't know how to register "+clazz.getName()+"."+f.getName());
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void playDefaultStepSound(Entity entity, BlockPos pos, BlockState state) {
		if (!state.getMaterial().isLiquid()) {
			BlockState blockState = entity.world.getBlockState(pos.up());
			BlockSoundGroup blockSoundGroup = blockState.isOf(Blocks.SNOW)
					? blockState.getSoundGroup()
					: state.getSoundGroup();
			entity.playSound(blockSoundGroup.getStepSound(),
					blockSoundGroup.getVolume() * 0.15F,
					blockSoundGroup.getPitch());
		}
	}

}
