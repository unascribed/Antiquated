package com.unascribed.antiquated.init;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.jetbrains.annotations.Nullable;

import com.unascribed.antiquated.AntiquatedComponents;
import com.unascribed.antiquated.HighStackScreenHandlerWrapper;
import com.unascribed.antiquated.Technical;
import com.unascribed.antiquated.TimeEquivalents;
import com.unascribed.antiquated.block.AntiqueChestBlockEntity;
import com.unascribed.antiquated.block.AntiqueFlowerBlock;
import com.unascribed.antiquated.block.AntiqueLeavesBlock;
import com.unascribed.antiquated.block.AntiqueMushroomBlock;
import com.unascribed.antiquated.block.AntiqueSaplingBlock;
import com.unascribed.antiquated.block.IdentitySilkTouchBlock;
import com.unascribed.antiquated.block.RuneBlock;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.StairShape;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext.Builder;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
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
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class ABlocks {

	public static final Block STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(1.5f, 10f)
			.requiresTool());
	
	public static final Block COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
	public static final Block DIRT = new Block(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN)
			.sounds(ASounds.GRAVEL_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.5f));
	
	public static final Block GRASS = new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.ticksRandomly()
			.strength(0.6f)) {
		{
			setDefaultState(getDefaultState().with(SnowyBlock.SNOWY, false));
		}
		
		@Override
		public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
			return direction != Direction.UP ?
					super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom)
				: (BlockState) state.with(SnowyBlock.SNOWY, newState.isOf(ABlocks.SNOW_BLOCK) || newState.isOf(ABlocks.SNOW));
		}

		@Override
		public BlockState getPlacementState(ItemPlacementContext ctx) {
			BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
			return this.getDefaultState().with(SnowyBlock.SNOWY, blockState.isOf(ABlocks.SNOW_BLOCK) || blockState.isOf(ABlocks.SNOW));
		}

		@Override
		protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
			builder.add(SnowyBlock.SNOWY);
		}
		
		private boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
			BlockPos blockPos = pos.up();
			BlockState blockState = worldView.getBlockState(blockPos);
			if (blockState.isOf(ABlocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
				return true;
			} else if (blockState.getFluidState().getLevel() == 8) {
				return false;
			} else {
				int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(worldView, blockPos));
				return i < worldView.getMaxLightLevel();
			}
		}

		private boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
			BlockPos blockPos = pos.up();
			return canSurvive(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);
		}

		@Override
		public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
			if (!canSurvive(state, world, pos)) {
				world.setBlockState(pos, ABlocks.DIRT.getDefaultState());
			} else {
				if (world.getLightLevel(pos.up()) >= 9) {
					BlockState blockState = this.getDefaultState();
					for (int i = 0; i < 4; ++i) {
						BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (world.getBlockState(blockPos).isOf(ABlocks.DIRT) && canSpread(blockState, world, blockPos)) {
							world.setBlockState(blockPos, blockState.with(SnowyBlock.SNOWY, world.getBlockState(blockPos.up()).isOf(ABlocks.SNOW)));
						}
					}
				}

			}
		}
	};
	
	public static final Block WOOD = new Block(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f));
	
	public static final Block LEAVES = new AntiqueLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.strength(0.2f)
			.nonOpaque()
			.suffocates((state, world, pos) -> false)
			.blockVision((state, world, pos) -> false));
	
	public static final Block GRAVEL = new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.GRAY)
			.sounds(ASounds.GRAVEL_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.6f));
	
	public static final Block SAND = new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW)
			.sounds(ASounds.SAND_SOUNDS)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.5f));
	
	public static final CactusBlock CACTUS = new CactusBlock(FabricBlockSettings.of(Material.CACTUS, MapColor.GREEN)
			.sounds(BlockSoundGroup.WOOL)
			.ticksRandomly()
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
	
	public static final Block FURNACE = new FurnaceBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
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
	
	public static final Block COBBLESTONE_MOSSY = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
	public static final Block PLANKS = new Block(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f, 5.0f));
	
	public static final Block CHEST = new ChestBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
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
	
	public static final Block PLANKS_OLD = new RuneBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(2.0f, 5.0f))
		.addLayer(
				"       ",
				"       ",
				"       ",
				"   P   ",
				"       ",
				"       ",
				"       "
			)
		.addLayer(
				" ##### ",
				"##OXO##",
				"#LGOGW#",
				"#LD DW#",
				"#LGOGW#",
				"##OXO##",
				" ##### "
			)
		.addLayer(
				" +++++ ",
				"++   ++",
				"+ +++ +",
				"+ + + +",
				"+ +++ +",
				"++   ++",
				" +++++ "
			)
		.recognize('P', () -> ABlocks.PLANKS_OLD)
		.recognize('#', () -> Blocks.IRON_BLOCK, () -> ABlocks.IRON_BLOCK)
		.recognize('O', () -> ABlocks.OBSIDIAN, () -> ABlocks.ANCH_OBSIDIAN, () -> Blocks.OBSIDIAN)
		.recognize('L', () -> ABlocks.LAVA, () -> Blocks.LAVA)
		.recognize('G', () -> Blocks.GOLD_BLOCK, () -> ABlocks.GOLD_BLOCK)
		.recognize('W', () -> ABlocks.WATER, () -> Blocks.WATER)
		.recognize('D', () -> Blocks.DIAMOND_BLOCK, () -> ABlocks.DIAMOND_BLOCK)
		.recognize('+', () -> Blocks.REDSTONE_WIRE)
		.dontCare('X')
		.replace('O', () -> ABlocks.ANCH_OBSIDIAN)
		.cast((world, pos, blocks) -> {
			world.breakBlock(pos.up(), false);
			int fuelNeeded = 30;
			if (pos.getY() < 128) {
				fuelNeeded *= 2;
			}
			boolean allWaterModern = true;
			for (BlockPos bp : blocks.get('W')) {
				if (world.getBlockState(bp).isOf(ABlocks.WATER)) {
					allWaterModern = false;
					break;
				}
			}
			boolean allLavaModern = true;
			for (BlockPos bp : blocks.get('W')) {
				if (world.getBlockState(bp).isOf(ABlocks.LAVA)) {
					allLavaModern = false;
					break;
				}
			}
			if (allLavaModern != allWaterModern) fuelNeeded /= 2;
			Map<BlockPos, BlockState> newFuelStates = Maps.newHashMap();
			List<BlockPos> fuelBlocks = Lists.newArrayList();
			addShuffled(fuelBlocks, blocks.get('#'));
			addShuffled(fuelBlocks, blocks.get('+'));
			addShuffled(fuelBlocks, blocks.get('G'));
			addShuffled(fuelBlocks, blocks.get('D'));
			int antiqueFuelNeeded = 0;
			int modernFuelNeeded = 0;
			Map<BlockPos, BlockState> transformations = Maps.newHashMap();
			for (BlockPos bp : blocks.get('X')) {
				BlockState bs = world.getBlockState(bp);
				Block result = null;
				if (TimeEquivalents.EQUIVS.containsKey(bs.getBlock())) {
					modernFuelNeeded += fuelNeeded;
					result = TimeEquivalents.EQUIVS.get(bs.getBlock());
				} else if (TimeEquivalents.EQUIVS.inverse().containsKey(bs.getBlock())) {
					antiqueFuelNeeded += fuelNeeded;
					result = TimeEquivalents.EQUIVS.inverse().get(bs.getBlock());
				} else {
					continue;
				}
				BlockState newState = result.getDefaultState();
				for (Property prop : bs.getProperties()) {
					if (newState.contains(prop)) {
						newState = newState.with(prop, bs.get(prop));
					}
				}
				transformations.put(bp, newState);
			}
			if (transformations.isEmpty()) {
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 0.5f);
				for (BlockPos bp : blocks.get('X')) {
					world.spawnParticles(ParticleTypes.SMOKE, bp.getX()+0.5, bp.getY(), bp.getZ()+0.5, 12, 0.2, 0, 0.2, 0);
				}
				return;
			}
			int ultimateFuelNeeded = 0;
			Map<Block, Integer> fuelMap = null;
			BiMap<Block, Block> fuelByproductMap = null;
			if (antiqueFuelNeeded > modernFuelNeeded) {
				fuelMap = TimeEquivalents.FUEL_VALUES_ANTIQUIZE;
				fuelByproductMap = TimeEquivalents.EQUIVS;
				ultimateFuelNeeded = antiqueFuelNeeded-modernFuelNeeded;
			} else if (modernFuelNeeded > antiqueFuelNeeded) {
				fuelMap = TimeEquivalents.FUEL_VALUES_MODERNIZE;
				fuelByproductMap = TimeEquivalents.EQUIVS.inverse();
				ultimateFuelNeeded = modernFuelNeeded-antiqueFuelNeeded;
			}
			boolean paidInFull = true;
			if (fuelMap != null && fuelByproductMap != null) {
				paidInFull = false;
				int fuelFound = 0;
				for (BlockPos fuel : fuelBlocks) {
					BlockState fuelState = world.getBlockState(fuel);
					Block fuelBlock = fuelState.getBlock();
					if (fuelMap.containsKey(fuelBlock)) {
						fuelFound += fuelMap.get(fuelBlock);
						if (fuelByproductMap.containsKey(fuelBlock)) {
							newFuelStates.put(fuel, fuelByproductMap.get(fuelBlock).getDefaultState());
						} else {
							newFuelStates.put(fuel, Blocks.AIR.getDefaultState());
						}
						if (fuelFound >= ultimateFuelNeeded) break;
					}
				}
				if (fuelFound >= ultimateFuelNeeded) {
					for (Map.Entry<BlockPos, BlockState> en : newFuelStates.entrySet()) {
						world.syncWorldEvent(null, 2001, en.getKey(), Block.getRawIdFromState(world.getBlockState(en.getKey())));
						world.setBlockState(en.getKey(), en.getValue());
					}
					paidInFull = true;
				}
			}
			if (paidInFull) {
				world.playSound(null, pos, ASounds.EXPLODE, SoundCategory.BLOCKS, 1, 0.5f);
				for (Map.Entry<BlockPos, BlockState> trans : transformations.entrySet()) {
					world.syncWorldEvent(null, 2001, trans.getKey(), Block.getRawIdFromState(world.getBlockState(trans.getKey())));
					world.setBlockState(trans.getKey(), trans.getValue());
				}
				world.syncWorldEvent(null, 2001, pos, Block.getRawIdFromState(ABlocks.PLANKS.getDefaultState()));
				world.setBlockState(pos, ABlocks.PLANKS.getDefaultState());
			} else {
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 0.5f);
				for (BlockPos bp : blocks.get('+')) {
					world.spawnParticles(ParticleTypes.SMOKE, bp.getX()+0.5, bp.getY(), bp.getZ()+0.5, 12, 0.2, 0, 0.2, 0);
				}
			}
		});
	
	private static <T> void addShuffled(List<T> to, Iterable<T> from) {
		List<T> copy = Lists.newArrayList(from);
		Collections.shuffle(copy);
		to.addAll(copy);
	}
	
	public static final Block WOOD_OLD = new Block(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
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
	
	public static final Block LEAVES_OLD = new AntiqueLeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.strength(0.2f)
			.nonOpaque()
			.suffocates((state, world, pos) -> false)
			.blockVision((state, world, pos) -> false));
	
	public static final Block SAPLING = new AntiqueSaplingBlock(FabricBlockSettings.of(Material.PLANT, MapColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block SAPLING_OLD = new AntiqueSaplingBlock(FabricBlockSettings.of(Material.PLANT, MapColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block ROSE = new AntiqueFlowerBlock(FabricBlockSettings.of(Material.PLANT, MapColor.RED)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block DANDELION = new AntiqueFlowerBlock(FabricBlockSettings.of(Material.PLANT, MapColor.YELLOW)
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
			
	public static final Block OBSIDIAN = new Block(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 3)
			.strength(50, 1200)
			.requiresTool());
	
	public static final Block ANCH_OBSIDIAN = new RuneBlock(FabricBlockSettings.of(Material.STONE, MapColor.PURPLE)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 3)
			.strength(50, 1200)
			.requiresTool())
		.addLayer(
				"       ",
				"       ",
				"  OzO  ",
				"  zOz  ",
				"  OzO  ",
				"       ",
				"       "
			)
		.addLayer(
				"   #   ",
				"  #P#  ",
				" #A#A# ",
				"#m# #M#",
				" #A#A# ",
				"  #p#  ",
				"   #   "
			)
		.consume('O', () -> ABlocks.ANCH_OBSIDIAN)
		.consume('#', () -> ABlocks.ANCH_COBBLESTONE)
		.consume('P', () -> Blocks.OAK_PLANKS)
		.consume('A', () -> Blocks.AIR)
		.consume('m', () -> ABlocks.COBBLESTONE_MOSSY)
		.consume('M', () -> Blocks.MOSSY_COBBLESTONE)
		.consume('p', () -> ABlocks.PLANKS_OLD)
		.dontCare('z')
		.replace('O', () -> Blocks.CRYING_OBSIDIAN)
		.replace('z', () -> Blocks.CRYING_OBSIDIAN)
		.cast((world, pos, blocks) -> {
			LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
			lightning.setCosmetic(true);
			lightning.refreshPositionAfterTeleport(Vec3d.ofCenter(pos, 1));
			world.spawnEntity(lightning);
			ChunkPos cp = new ChunkPos(pos);
			for (int x = -16; x <= 16; x++) {
				for (int z = -16; z <= 16; z++) {
					Chunk c = world.getChunk(cp.x+x, cp.z+z, ChunkStatus.BIOMES, true);
					AntiquatedComponents.UNCURSED.get(c).setStrength(4);
				}
			}
			for (ServerPlayerEntity pe : world.getPlayers()) {
				if (pe.squaredDistanceTo(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5) < 8*8) {
					pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "ritual")), "minecraft:impossible");
				}
			}
		});
	
	public static final Block ANCH_COBBLESTONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool());
	
	public static final Block BROWN_MUSHROOM = new AntiqueMushroomBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN)
			.sounds(ASounds.GRASS_SOUNDS)
			.luminance(1)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final Block RED_MUSHROOM = new AntiqueMushroomBlock(FabricBlockSettings.of(Material.PLANT, MapColor.RED)
			.sounds(ASounds.GRASS_SOUNDS)
			.noCollision()
			.ticksRandomly()
			.breakInstantly());
	
	public static final SugarCaneBlock REEDS = new SugarCaneBlock(FabricBlockSettings.of(Material.PLANT, MapColor.GREEN)
			.noCollision()
			.sounds(BlockSoundGroup.GRASS)
			.ticksRandomly()
			.breakInstantly()) {
		
		@Override
		public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
			BlockState blockState = world.getBlockState(pos.down());
			if (blockState.getBlock() == this) {
				return true;
			} else {
				if (blockState.isOf(GRASS) || blockState.isOf(DIRT) || blockState.isOf(SAND)) {
					BlockPos blockPos = pos.down();
					for (Direction direction : Direction.Type.HORIZONTAL) {
						BlockState blockState2 = world.getBlockState(blockPos.offset(direction));
						FluidState fluidState = world.getFluidState(blockPos.offset(direction));
						if (fluidState.isIn(FluidTags.WATER) || blockState2.isOf(Blocks.FROSTED_ICE)) {
							return true;
						}
					}
				}

				return false;
			}
		}
		
	};
	
	public static final Block SNOW = new SnowBlock(FabricBlockSettings.of(Material.SNOW_LAYER, MapColor.WHITE)
			.sounds(BlockSoundGroup.WOOL)
			.breakByTool(FabricToolTags.SHOVELS)
			.strength(0.1F)
			.ticksRandomly()
			.requiresTool()) {};
			
	public static final Block SNOW_BLOCK = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK, MapColor.WHITE)
			.sounds(BlockSoundGroup.WOOL)
			.breakByTool(FabricToolTags.SHOVELS)
			.requiresTool()
			.strength(0.2F));
	
	public static final Block ICE = new TransparentBlock(FabricBlockSettings.of(Material.ICE, MapColor.PALE_PURPLE)
			.sounds(BlockSoundGroup.GLASS)
			.breakByTool(FabricToolTags.PICKAXES)
			.slipperiness(0.98F)
			.ticksRandomly()
			.strength(0.5F)
			.sounds(BlockSoundGroup.GLASS)
			.nonOpaque()) {
		@Override
		public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
			super.afterBreak(world, player, pos, state, blockEntity, stack);
			if (world.getDimension().isUltrawarm()) {
				world.removeBlock(pos, false);
				return;
			}

			Material material = world.getBlockState(pos.down()).getMaterial();
			if (material.blocksMovement() || material.isLiquid()) {
				world.setBlockState(pos, WATER.getDefaultState());
			}
		}

		@Override
		public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
			if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
				if (world.getDimension().isUltrawarm()) {
					world.removeBlock(pos, false);
				} else {
					world.setBlockState(pos, WATER.getDefaultState());
					world.updateNeighbor(pos, WATER, pos);
				}
			}
		}

		@Override
		public PistonBehavior getPistonBehavior(BlockState state) {
			return PistonBehavior.NORMAL;
		}
	};
	
	public static final Block DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.AXES)
			.strength(3)) {
		@Override
		public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
			onUse(state, world, pos, player, null, null);
		}
		
		@Override
		public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
			return VoxelShapes.empty();
		}
	};
			
	public static final Block GLASS = new TransparentBlock(FabricBlockSettings.of(Material.ICE, MapColor.PALE_PURPLE)
			.sounds(BlockSoundGroup.GLASS)
			.ticksRandomly()
			.strength(0.3F)
			.nonOpaque()) {};
			
	public static final Block IRON_BLOCK = new Block(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY)
			.sounds(BlockSoundGroup.METAL)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(5.0f, 10f)
			.requiresTool());
	
	public static final Block GOLD_BLOCK = new Block(FabricBlockSettings.of(Material.METAL, MapColor.GOLD)
			.sounds(BlockSoundGroup.METAL)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(3.0f, 10f)
			.requiresTool());
	
	public static final Block DIAMOND_BLOCK = new Block(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE)
			.sounds(BlockSoundGroup.METAL)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(5.0f, 10f)
			.requiresTool());
	
	public static final Block SPONGE = new Block(FabricBlockSettings.of(Material.WOOL, MapColor.YELLOW)
			.sounds(ASounds.GRASS_SOUNDS)
			.ticksRandomly()
			.strength(0.6f)) {
		
		@Override
		public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
			BlockPos.Mutable mut = new BlockPos.Mutable();
			for (int x = -2; x <= 2; x++) {
				for (int y = -2; y <= 2; y++) {
					for (int z = -2; z <= 2; z++) {
						mut.set(pos).move(x, y, z);
						check(world, mut);
					}
				}
			}
		}
		
		@Override
		public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
			BlockPos.Mutable mut = new BlockPos.Mutable();
			for (int x = -2; x <= 2; x++) {
				for (int y = -2; y <= 2; y++) {
					for (int z = -2; z <= 2; z++) {
						mut.set(pos).move(x, y, z);
						if (world.getBlockState(mut).isOf(ABlocks.SPONGE_AIR)) {
							world.setBlockState(mut, Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
		
		@Override
		public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
			for (int i = 0; i < 4; i++) {
				check(world, pos.add(random.nextInt(5)-2, random.nextInt(5)-2, random.nextInt(5)-2));
			}
		}

		private void check(World world, BlockPos pos) {
			if (world.isAir(pos)) {
				world.setBlockState(pos, ABlocks.SPONGE_AIR.getDefaultState());
			} else {
				BlockState bs = world.getBlockState(pos);
				FluidState fs = world.getFluidState(pos);
				if (fs.isIn(FluidTags.WATER)) {
					if (bs.getBlock() instanceof FluidBlock) {
						// tryDrainFluid won't succeed on a non-source block
						world.setBlockState(pos, ABlocks.SPONGE_AIR.getDefaultState());
					} else if (bs.getBlock() instanceof FluidDrainable) {
						((FluidDrainable)bs.getBlock()).tryDrainFluid(world, pos, bs);
						if (world.isAir(pos)) {
							world.setBlockState(pos, ABlocks.SPONGE_AIR.getDefaultState());
						}
					}
				}
			}
		}
		
	};
	
	@Technical
	public static final Block SPONGE_AIR = new AirBlock(FabricBlockSettings.of(Material.AIR)
			.air()
			.dropsNothing()
			.noCollision()
			.nonOpaque()) {
		@Override
		public FluidState getFluidState(BlockState state) {
			return AFluids.SPONGE_AIR.getDefaultState();
		}
		
		@Override
		public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
			if (newState.getBlock() instanceof FluidBlock && newState.getFluidState().isIn(FluidTags.WATER)) {
				world.setBlockState(pos, getDefaultState());
			}
		}
	};
	
	public static final Block COAL_ORE = new IdentitySilkTouchBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(3)
			.requiresTool()
			.dropsLike(Blocks.COAL_ORE));
	
	public static final Block IRON_ORE = new IdentitySilkTouchBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 1)
			.strength(3)
			.requiresTool()
			.dropsLike(Blocks.IRON_ORE));
	
	public static final Block GOLD_ORE = new IdentitySilkTouchBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 2)
			.strength(3)
			.requiresTool()
			.dropsLike(Blocks.GOLD_ORE));
	
	public static final Block DIAMOND_ORE = new IdentitySilkTouchBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 2)
			.strength(3)
			.requiresTool()
			.dropsLike(Blocks.DIAMOND_ORE));
	
	public static final Block REDSTONE_ORE = new RedstoneOreBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES, 2)
			.strength(3)
			.requiresTool()
			.dropsLike(Blocks.REDSTONE_ORE)) {
		@Override
		public List<ItemStack> getDroppedStacks(BlockState state, Builder builder) {
			List<ItemStack> identity = IdentitySilkTouchBlock.handle(state, builder);
			if (identity != null) return identity;
			return super.getDroppedStacks(state, builder);
		}
		
		@Override
		public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
			// never give an effective tool bonus
			// reimplements an Alpha bug
			return player.getBlockBreakingSpeed(state) / state.getHardness(world, pos) / 100f;
		}
	};
	
	public static final Block CLOTH_RED = makeCloth(MapColor.RED);
	public static final Block CLOTH_ORANGE = makeCloth(MapColor.ORANGE);
	public static final Block CLOTH_YELLOW = makeCloth(MapColor.YELLOW);
	public static final Block CLOTH_LIME = makeCloth(MapColor.LIME);
	public static final Block CLOTH_GREEN = makeCloth(MapColor.LIME);
	public static final Block CLOTH_MINT = makeCloth(MapColor.EMERALD_GREEN);
	public static final Block CLOTH_CYAN = makeCloth(MapColor.PALE_PURPLE);
	public static final Block CLOTH_PERIWINKLE = makeCloth(MapColor.PALE_PURPLE);
	public static final Block CLOTH_LILAC = makeCloth(MapColor.PURPLE);
	public static final Block CLOTH_PURPLE = makeCloth(MapColor.PURPLE);
	public static final Block CLOTH_LAVENDER = makeCloth(MapColor.PURPLE);
	public static final Block CLOTH_MAGENTA = makeCloth(MapColor.MAGENTA);
	public static final Block CLOTH_PINK = makeCloth(MapColor.MAGENTA);
	public static final Block CLOTH_GRAY = makeCloth(MapColor.GRAY);
	public static final Block CLOTH_SILVER = makeCloth(MapColor.LIGHT_GRAY);
	public static final Block CLOTH_WHITE = makeCloth(MapColor.WHITE);
	
	private static Block makeCloth(MapColor color) {
		return new Block(FabricBlockSettings.of(Material.WOOL, color)
			.sounds(BlockSoundGroup.WOOL)
			.strength(0.8f)
			.breakByTool(FabricToolTags.SHEARS));
	}
	
	public static final Block DISPENSER = new DispenserBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.requiresTool()
			.strength(3.5F)) {
		@Override
		public BlockState getPlacementState(ItemPlacementContext ctx) {
			return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
		}
	};
			
	public static final Block STONECUTTER = new StonecutterBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.requiresTool()
			.strength(3.5F)) {
		@Override
		public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return VoxelShapes.fullCube();
		}
	};
	
	public static final Block COBBLESTONE_STAIRS = new StairsBlock(COBBLESTONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY)
			.sounds(ASounds.STONE_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool()) {
		@Override
		public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return VoxelShapes.fullCube();
		}
		
		@Override
		public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return super.getOutlineShape(state.with(StairsBlock.SHAPE, StairShape.STRAIGHT), world, pos, context);
		}
		
		@Override
		public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
			return state.getCollisionShape(world, pos);
		}
		
		@Override
		public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
			return false;
		}
	};
			
	public static final Block WOOD_STAIRS = new StairsBlock(COBBLESTONE.getDefaultState(), FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN)
			.sounds(ASounds.WOOD_SOUNDS)
			.breakByTool(FabricToolTags.PICKAXES)
			.strength(2.0f, 10f)
			.requiresTool()) {
		@Override
		public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return VoxelShapes.fullCube();
		}
		
		@Override
		public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
			return super.getOutlineShape(state.with(StairsBlock.SHAPE, StairShape.STRAIGHT), world, pos, context);
		}
		
		@Override
		public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
			return state.getCollisionShape(world, pos);
		}
		
		@Override
		public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
			return false;
		}
	};
	
}
