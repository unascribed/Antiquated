package com.unascribed.antiquated.block;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.unascribed.antiquated.init.ABlocks;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RuneBlock extends Block {
	public interface RuneCaster {
		void cast(ServerWorld world, BlockPos pos, Multimap<Character, BlockPos> blocks);
	}
	
	private final List<String[]> patterns = Lists.newArrayList();
	private final Multimap<Character, Supplier<Block>> mappingUnresolved = HashMultimap.create();
	private final Multimap<Character, Block> mapping = HashMultimap.create();
	private final Set<Character> consumed = Sets.newHashSet();
	private final Map<Character, Supplier<Block>> replacements = Maps.newHashMap();
	private RuneCaster caster;
	
	public RuneBlock(Settings settings) {
		super(settings);
		this.mappingUnresolved.put(' ', null);
	}
	
	public RuneBlock addLayer(String... layer) {
		patterns.add(layer);
		return this;
	}
	
	@SafeVarargs
	public final RuneBlock recognize(char c, Supplier<Block>... blocks) {
		for (Supplier<Block> b : blocks) {
			mappingUnresolved.put(c, b);
		}
		return this;
	}
	
	@SafeVarargs
	public final RuneBlock consume(char c, Supplier<Block>... blocks) {
		recognize(c, blocks);
		consumed.add(c);
		return this;
	}
	
	public RuneBlock dontCare(char c) {
		mappingUnresolved.put(c, null);
		return this;
	}
	
	public RuneBlock replace(char c, Supplier<Block> replacement) {
		replacements.put(c, replacement);
		consumed.add(c);
		return this;
	}
	
	public RuneBlock cast(RuneCaster caster) {
		this.caster = caster;
		return this;
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!(world instanceof ServerWorld)) return;
		if (fromPos.equals(pos.up()) && world.getBlockState(fromPos).getBlock() == Blocks.FIRE) {
			if (!mappingUnresolved.isEmpty()) {
				for (Map.Entry<Character, Supplier<Block>> en : mappingUnresolved.entries()) {
					mapping.put(en.getKey(), en.getValue() == null ? null : en.getValue().get());
				}
				mappingUnresolved.clear();
			}
			ServerWorld sw = (ServerWorld)world;
			if (this == ABlocks.ANCH_OBSIDIAN) {
				for (ServerPlayerEntity pe : sw.getPlayers()) {
					if (pe.squaredDistanceTo(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5) < 8*8) {
						pe.getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(new Identifier("antiquated", "ignite_anch_obsidian")), "minecraft:impossible");
					}
				}
			}
			int wrong = 0;
			
			int width = patterns.get(0)[0].length();
			int height = patterns.size();
			int depth = patterns.get(0).length;
			
			Multimap<Character, BlockPos> blocks = HashMultimap.create();
			
			BlockPos.Mutable mut = new BlockPos.Mutable();
			out: for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					for (int z = 0; z < depth; z++) {
						char c = patterns.get(y)[x].charAt(z);
						if (!mapping.containsKey(c)) {
							System.err.println("Unknown rune component "+c);
						}
						mut.set(pos).move(x-(width/2), y, z-(depth/2));
						Collection<Block> want = mapping.get(c);
						if (want.size() == 1 && want.iterator().next() == null) {
							// null = don't care
							blocks.put(c, mut.toImmutable());
							continue;
						}
						boolean wantAir = want.size() == 1 && want.iterator().next() == Blocks.AIR;
						BlockState have = world.getBlockState(mut);
						boolean matches;
						if (wantAir) {
							matches = have.isAir();
						} else {
							matches = want.contains(have.getBlock());
						}
						if (!matches) {
							if (wantAir) {
								world.breakBlock(mut, false);
							} else {
								world.syncWorldEvent(null, 2001, mut, Block.getRawIdFromState(want.iterator().next().getDefaultState()));
								world.setBlockState(fromPos, block.getDefaultState());
								wrong++;
								if (wrong > 3) break out;
							}
						} else {
							blocks.put(c, mut.toImmutable());
						}
					}
				}
			}
			if (wrong > 0) {
				world.syncWorldEvent(null, 1009, fromPos, 0);
				return;
			}
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					for (int z = 0; z < depth; z++) {
						char c = patterns.get(y)[x].charAt(z);
						if (consumed.contains(c)) {
							mut.set(pos).move(x-(width/2), y, z-(depth/2));
							if (replacements.containsKey(c)) {
								world.setBlockState(mut, replacements.get(c).get().getDefaultState());
							} else {
								world.breakBlock(mut, false);
							}
						}
					}
				}
			}
			caster.cast(sw, pos, blocks);
		}
	}
}