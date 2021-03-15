package com.unascribed.antiquated.init;

import java.util.Iterator;
import java.util.Random;

import com.unascribed.antiquated.AntiqueChestBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

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
	
	public static final Block LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES, MaterialColor.LIME)
			.sounds(ASounds.GRASS_SOUNDS)
			.strength(0.2f)
			.nonOpaque()
			.suffocates((state, world, pos) -> false)
			.blockVision((state, world, pos) -> false)) {
		
		@Override
		public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
			return VoxelShapes.empty();
		}

		@Override
		public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
			return 1;
		}
		
	};
	
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
		
	};
	
}
