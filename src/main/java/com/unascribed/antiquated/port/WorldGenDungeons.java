package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.AntiqueChestBlockEntity;
import com.unascribed.antiquated.init.AEnchantments;
import com.unascribed.antiquated.init.AEntityTypes;
import com.unascribed.antiquated.init.AItems;
import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Material;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class WorldGenDungeons extends WorldGenerator {
	public WorldGenDungeons() {
		super();
	}

	@Override
	public boolean populate(final AlphaWorld world, final Random random, final int n, final int n2, final int n3) {
		final int n4 = 3;
		final int n5 = random.nextInt(2) + 2;
		final int n6 = random.nextInt(2) + 2;
		int n7 = 0;
		for (int i = n - n5 - 1; i <= n + n5 + 1; ++i) {
			for (int j = n2 - 1; j <= n2 + n4 + 1; ++j) {
				for (int k = n3 - n6 - 1; k <= n3 + n6 + 1; ++k) {
					final Material func_599_f = world.getBlockMaterial(i, j, k);
					if (j == n2 - 1 && !func_599_f.isSolid()) {
						return false;
					}
					if (j == n2 + n4 + 1 && !func_599_f.isSolid()) {
						return false;
					}
					if ((i == n - n5 - 1 || i == n + n5 + 1 || k == n3 - n6 - 1 || k == n3 + n6 + 1) && j == n2 && world.getBlockId(i, j, k) == 0 && world.getBlockId(i, j + 1, k) == 0) {
						++n7;
					}
				}
			}
		}
		if (n7 < 1 || n7 > 5) {
			return false;
		}
		for (int l = n - n5 - 1; l <= n + n5 + 1; ++l) {
			for (int n8 = n2 + n4; n8 >= n2 - 1; --n8) {
				for (int n9 = n3 - n6 - 1; n9 <= n3 + n6 + 1; ++n9) {
					if (l == n - n5 - 1 || n8 == n2 - 1 || n9 == n3 - n6 - 1 || l == n + n5 + 1 || n8 == n2 + n4 + 1 || n9 == n3 + n6 + 1) {
						if (n8 >= 0 && !world.getBlockMaterial(l, n8 - 1, n9).isSolid()) {
							world.setBlockId(l, n8, n9, 0);
						}
						else if (world.getBlockMaterial(l, n8, n9).isSolid()) {
							if (n8 == n2 - 1 && random.nextInt(4) != 0) {
								world.setBlockId(l, n8, n9, AlphaBlock.cobblestoneMossy.blockID);
							}
							else {
								world.setBlockId(l, n8, n9, AlphaBlock.cobblestone.blockID);
							}
						}
					}
					else {
						world.setBlockId(l, n8, n9, 0);
					}
				}
			}
		}
		for (int n10 = 0; n10 < 2; ++n10) {
			for (int n11 = 0; n11 < 3; ++n11) {
				final int n12 = n + random.nextInt(n5 * 2 + 1) - n5;
				final int n13 = n3 + random.nextInt(n6 * 2 + 1) - n6;
				if (world.getBlockId(n12, n2, n13) == 0) {
					int n14 = 0;
					if (world.getBlockMaterial(n12 - 1, n2, n13).isSolid()) {
						++n14;
					}
					if (world.getBlockMaterial(n12 + 1, n2, n13).isSolid()) {
						++n14;
					}
					if (world.getBlockMaterial(n12, n2, n13 - 1).isSolid()) {
						++n14;
					}
					if (world.getBlockMaterial(n12, n2, n13 + 1).isSolid()) {
						++n14;
					}
					if (n14 == 1) {
						world.setBlockId(n12, n2, n13, AlphaBlock.chest.blockID);
						AntiqueChestBlockEntity tileEntityChest = (AntiqueChestBlockEntity)world.getBlockEntity(n12, n2, n13);
						if (tileEntityChest == null) {
							tileEntityChest = new AntiqueChestBlockEntity();
							world.delegate.getChunk(world.mut.set(n12, n2, n13)).setBlockEntity(world.mut, tileEntityChest);
						}
						for (int n15 = 0; n15 < 8; ++n15) {
							final ItemStack func_530_a = func_530_a(random);
							if (func_530_a != null) {
								tileEntityChest.setStack(random.nextInt(tileEntityChest.size()), func_530_a);
							}
						}
						break;
					}
				}
			}
		}
		world.setBlockId(n, n2, n3, AlphaBlock.mobSpawner.blockID);
		MobSpawnerBlockEntity msbe = ((MobSpawnerBlockEntity)world.getBlockEntity(n, n2, n3));
		if (msbe == null) {
			msbe = new MobSpawnerBlockEntity();
			world.delegate.getChunk(world.mut.set(n, n2, n3)).setBlockEntity(world.mut, msbe);
		}
		msbe.getLogic().setEntityId(func_531_b(random));
		return true;
	}

	private ItemStack func_530_a(final Random random) {
		final int nextInt = random.nextInt(11);
		if (nextInt == 0) {
			return new ItemStack(Items.SADDLE);
		}
		if (nextInt == 1) {
			return new ItemStack(Items.IRON_INGOT, random.nextInt(4) + 1);
		}
		if (nextInt == 2) {
			return new ItemStack(AItems.APPLE);
		}
		if (nextInt == 3) {
			return new ItemStack(Items.WHEAT, random.nextInt(4) + 1);
		}
		if (nextInt == 4) {
			return new ItemStack(Items.GUNPOWDER, random.nextInt(4) + 1);
		}
		if (nextInt == 5) {
			return new ItemStack(Items.STRING, random.nextInt(4) + 1);
		}
		if (nextInt == 6) {
			return new ItemStack(Items.BUCKET);
		}
		if (nextInt == 7 && random.nextInt(100) == 0) {
			return new ItemStack(AItems.GOLDEN_APPLE);
		}
		if (nextInt == 8 && random.nextInt(2) == 0) {
			return new ItemStack(Items.REDSTONE, random.nextInt(4) + 1);
		}
		if (nextInt == 9) {
			int lootSelector = random.nextInt(10);
			// must always be called to advance the RNG properly, otherwise seed desync will occur
			boolean lootSelector2 = random.nextBoolean();
			if (lootSelector == 0) {
				return new ItemStack(lootSelector2 ? Items.MUSIC_DISC_13 : Items.MUSIC_DISC_CAT);
			} else if (lootSelector == 1) {
				return EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(AEnchantments.LEGACY_CURSE, 1));
			} else if (lootSelector == 2) {
				return new ItemStack(AItems.RECORD_CALM4);
			}
		}
		return null;
	}

	private EntityType<?> func_531_b(final Random random) {
		final int nextInt = random.nextInt(4);
		if (nextInt == 0) {
			return AEntityTypes.SKELETON;
		}
		if (nextInt == 1) {
			return AEntityTypes.ZOMBIE;
		}
		if (nextInt == 2) {
			return AEntityTypes.ZOMBIE;
		}
		if (nextInt == 3) {
			return AEntityTypes.SPIDER;
		}
		return AEntityTypes.PIG;
	}
}
