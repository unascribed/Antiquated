package com.unascribed.antiquated.init;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import com.unascribed.antiquated.AntiquatedComponents;
import com.unascribed.antiquated.AntiqueChestBlockEntity;
import com.unascribed.antiquated.AntiqueFlowerBlock;
import com.unascribed.antiquated.AntiqueLeavesBlock;
import com.unascribed.antiquated.AntiqueSaplingBlock;
import com.unascribed.antiquated.HighStackScreenHandlerWrapper;

import com.google.common.collect.Maps;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class ABlocks {

	public static final Block STONE = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(1.5f, 10f)
			.requiresTool());
	
	public static final Block COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
	public static final Block DIRT = new Block(FabricBlockSettings.of(Material.SOIL, MaterialColor.DIRT)
			.sounds(ASounds.GRAVEL_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.5f));
	
	public static final Block GRASS = new SnowyBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.6f)) {};
	
	public static final Block WOOD = new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f));
	
	public static final Block LEAVES = new AntiqueLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.strength(0.2f)
			.nonOpaque()
			.suffocates((state, world, pos) -> false)
			.blockVision((state, world, pos) -> false));
	
	public static final Block GRAVEL = new Block(FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.GRAY)
			.sounds(ASounds.GRAVEL_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.6f));
	
	public static final Block SAND = new Block(FabricBlockSettings.of(Material.AGGREGATE, MaterialColor.SAND)
			.sounds(ASounds.SAND_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.5f));
	
	public static final CactusBlock CACTUS = new CactusBlock(FabricBlockSettings.of(Material.CACTUS, MaterialColor.GREEN)
			.sounds(BlockSoundGroup.WOOL)
			.strength(0.4f)) {
		
		@Override
		public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		      Iterator<Direction> iter = Direction.Type.HORIZONTAL.iterator();

		      Direction dir;
		      Material material;
		      do {
		         if (!iter.hasNext()) {
		            BlockState blockState2 = world.getBlockState(pos.down());
		            return (blockState2.isOf(SAND)) && !world.getBlockState(pos.up()).getMaterial().isLiquid();
		         }

		         dir = iter.next();
		         BlockState blockState = world.getBlockState(pos.offset(dir));
		         material = blockState.getMaterial();
		      } while(!material.isSolid() && !world.getFluidState(pos.offset(dir)).isIn(FluidTags.LAVA));

		      return false;
		   }
		
	};
	
	public static final Block FURNACE = new FurnaceBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.requiresTool()
			.strength(3.5f)) {
		
		@Override
		@Environment(EnvType.CLIENT)
		public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
			if (state.get(LIT)) {
				double d = pos.getX() + 0.5D;
				double e = pos.getY();
				double f = pos.getZ() + 0.5D;

				Direction direction = state.get(FACING);
				Direction.Axis axis = direction.getAxis();
				double g = 0.52D;
				double h = random.nextDouble() * 0.6D - 0.3D;
				double i = axis == Direction.Axis.X ? direction.getOffsetX() * 0.52D : h;
				double j = random.nextDouble() * 6.0D / 16.0D;
				double k = axis == Direction.Axis.Z ? direction.getOffsetZ() * 0.52D : h;
				world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
			}
		}
		
	};
	
	public static final Block COBBLESTONE_MOSSY = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
	public static final Block PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f, 5.0f));
	
	public static final Block CHEST = new ChestBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.5f), () -> ABlockEntityTypes.CHEST) {

		private final DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>> NAME_RETRIEVER = new DoubleBlockProperties.PropertyRetriever<ChestBlockEntity, Optional<NamedScreenHandlerFactory>>() {
			@Override
			public Optional<NamedScreenHandlerFactory> getFromBoth(ChestBlockEntity chest1, ChestBlockEntity chest2) {
				Inventory inventory = new DoubleInventory(chest1, chest2);
				return Optional.of(new NamedScreenHandlerFactory() {
					@Override
					@Nullable
					public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
						if (chest1.checkUnlocked(playerEntity) && chest2.checkUnlocked(playerEntity)) {
							chest1.checkLootInteraction(playerInventory.player);
							chest2.checkLootInteraction(playerInventory.player);
							return new HighStackScreenHandlerWrapper(new GenericContainerScreenHandler(AScreenHandlerTypes.ANTIQUE_DOUBLE_CHEST, i, playerInventory, inventory, 6));
						} else {
							return null;
						}
					}

					@Override
					public Text getDisplayName() {
						if (chest1.hasCustomName()) {
							return chest1.getDisplayName();
						} else {
							return chest2.hasCustomName() ? chest2.getDisplayName() : new TranslatableText("container.antiquated.chestDouble");
						}
					}
				});
			}

			@Override
			public Optional<NamedScreenHandlerFactory> getFrom(ChestBlockEntity chest) {
				return Optional.of(new NamedScreenHandlerFactory() {
					@Override
					@Nullable
					public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
						if (chest.checkUnlocked(playerEntity)) {
							chest.checkLootInteraction(playerInventory.player);
							return new HighStackScreenHandlerWrapper(new GenericContainerScreenHandler(AScreenHandlerTypes.ANTIQUE_CHEST, i, playerInventory, chest, 3));
						} else {
							return null;
						}
					}

					@Override
					public Text getDisplayName() {
						return chest.getDisplayName();
					}
				});
			}

			@Override
			public Optional<NamedScreenHandlerFactory> getFallback() {
				return Optional.empty();
			}
		};

		@Override
		public BlockRenderType getRenderType(BlockState state) {
			return BlockRenderType.MODEL;
		}

		@Override
		public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return VoxelShapes.fullCube();
		}

		@Override
		public BlockEntity createBlockEntity(BlockView world) {
			return new AntiqueChestBlockEntity();
		}

		@Override
		public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
			return getBlockEntitySource(state, world, pos, false).apply(NAME_RETRIEVER).orElse(null);
		}

	};
	
	public static final Block PLANKS_OLD = new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f, 5.0f));
	
	public static final Block WOOD_OLD = new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f)) {
		@Override
		public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
			super.onBreak(world, pos, state, player);
			if (!player.abilities.creativeMode) {
				Block.dropStack(world, pos, new ItemStack(PLANKS_OLD));
				Block.dropStack(world, pos, new ItemStack(PLANKS_OLD));
				Block.dropStack(world, pos, new ItemStack(PLANKS_OLD));
				Block.dropStack(world, pos, new ItemStack(PLANKS_OLD));
			}
		}
	};
	
	public static final Block LEAVES_OLD = new AntiqueLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.strength(0.2f)
			.nonOpaque()
			.suffocates((state, world, pos) -> false)
			.blockVision((state, world, pos) -> false));
	
	public static final Block SAPLING = new AntiqueSaplingBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block SAPLING_OLD = new AntiqueSaplingBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block ROSE = new AntiqueFlowerBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.RED)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block DANDELION = new AntiqueFlowerBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.YELLOW)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final FluidBlock WATER = new FluidBlock(AFluids.WATER, FabricBlockSettings.of(Material.WATER)
			.noCollision()
			.strength(100.0F)
			.dropsNothing()) {};
			
	public static final FluidBlock LAVA = new FluidBlock(AFluids.LAVA, FabricBlockSettings.of(Material.LAVA)
			.noCollision()
			.strength(100.0F)
			.luminance(15)
			.dropsNothing()) {};
			
	public static final Block OBSIDIAN = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.PURPLE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 3)
			.strength(50, 1200)
			.requiresTool());
	
	public static final Block ANCH_OBSIDIAN = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.PURPLE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 3)
			.strength(50, 1200)
			.requiresTool()) {
		@Override
		public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
			if (!(world instanceof ServerWorld)) return;
			if (fromPos.equals(pos.up()) && world.getBlockState(fromPos).getBlock() == Blocks.FIRE) {
				ServerWorld sw = (ServerWorld)world;
				for (ServerPlayerEntity pe : sw.getPlayers()) {
					if (pe.squaredDistanceTo(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5) < 8*8) {
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "ignite_anch_obsidian")), "minecraft:impossible");
					}
				}
				String[] patternBottom = {
					"       ",
					"       ",
					"  OzO  ",
					"  zOz  ",
					"  OzO  ",
					"       ",
					"       "
				};
				String[] patternTop = {
					"   #   ",
					"  #P#  ",
					" #A#A# ",
					"#m# #M#",
					" #A#A# ",
					"  #p#  ",
					"   #   "
				};
				String[][] patterns = {
						patternBottom, patternTop
				};
				Map<Character, Block> blocks = Maps.newHashMap();
				blocks.put(' ', null);
				blocks.put('z', null);
				blocks.put('O', ABlocks.ANCH_OBSIDIAN);
				blocks.put('#', ABlocks.ANCH_COBBLESTONE);
				blocks.put('P', Blocks.OAK_PLANKS);
				blocks.put('A', Blocks.AIR);
				blocks.put('m', ABlocks.COBBLESTONE_MOSSY);
				blocks.put('M', Blocks.MOSSY_COBBLESTONE);
				blocks.put('p', ABlocks.PLANKS_OLD);
				int wrong = 0;
				BlockPos.Mutable mut = new BlockPos.Mutable();
				out: for (int y = 0; y < patterns.length; y++) {
					for (int x = 0; x < patternBottom[0].length(); x++) {
						for (int z = 0; z < patternBottom.length; z++) {
							char c = patterns[y][x].charAt(z);
							if (!blocks.containsKey(c)) {
								System.err.println("Unknown rune component "+c);
							}
							Block want = blocks.get(c);
							if (want == null) {
								// null = don't care
								continue;
							}
							mut.set(pos).move(x-3, y, z-3);
							BlockState have = world.getBlockState(mut);
							boolean matches;
							if (want == Blocks.AIR) {
								matches = have.isAir();
							} else {
								matches = have.isOf(want);
							}
							if (!matches) {
								if (want == Blocks.AIR) {
									world.breakBlock(mut, false);
								} else {
									world.syncWorldEvent(null, 2001, mut, Block.getRawIdFromState(want.getDefaultState()));
									world.setBlockState(fromPos, block.getDefaultState());
									wrong++;
									if (wrong > 3) break out;
								}
							}
						}
					}
				}
				if (wrong > 0) {
					world.syncWorldEvent(null, 1009, fromPos, 0);
					return;
				}
				for (int y = 0; y < patterns.length; y++) {
					for (int x = 0; x < patternBottom[0].length(); x++) {
						for (int z = 0; z < patternBottom.length; z++) {
							char c = patterns[y][x].charAt(z);
							if (blocks.get(c) != null || c == 'z') {
								mut.set(pos).move(x-3, y, z-3);
								if (c == 'O' || c == 'z') {
									world.setBlockState(mut, Blocks.CRYING_OBSIDIAN.getDefaultState());
								} else {
									world.breakBlock(mut, false);
								}
							}
						}
					}
				}
				LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
				lightning.setCosmetic(true);
				lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(fromPos));
				world.spawnEntity(lightning);
				ChunkPos cp = new ChunkPos(pos);
				for (int x = -16; x <= 16; x++) {
					for (int z = -16; z <= 16; z++) {
						Chunk c = world.getChunk(cp.x+x, cp.z+z, ChunkStatus.BIOMES, true);
						AntiquatedComponents.UNCURSED.get(c).setStrength(4);
					}
				}
				for (ServerPlayerEntity pe : sw.getPlayers()) {
					if (pe.squaredDistanceTo(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5) < 8*8) {
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "ritual")), "minecraft:impossible");
					}
				}
			}
		}
	};
	
	public static final Block ANCH_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
}
