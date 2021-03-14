package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.port.adapter.AlphaBlock;
import com.unascribed.antiquated.port.adapter.BlockIDConverter;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.UpgradeData;

public class Alpha112ChunkGenerator {
	private Random rand;
	private NoiseGeneratorOctaves field_912_k;
	private NoiseGeneratorOctaves field_911_l;
	private NoiseGeneratorOctaves field_910_m;
	private NoiseGeneratorOctaves field_909_n;
	private NoiseGeneratorOctaves field_908_o;
	private NoiseGeneratorOctaves field_922_a;
	private NoiseGeneratorOctaves field_921_b;
	private NoiseGeneratorOctaves field_920_c;
	private AlphaWorld world;
	private double[] field_906_q;
	private double[] field_905_r;
	private double[] field_904_s;
	private double[] field_903_t;
	private MapGenBase caves;
	double[] field_919_d;
	double[] field_918_e;
	double[] field_917_f;
	double[] field_916_g;
	double[] field_915_h;
	int[][] field_914_i;

	public Alpha112ChunkGenerator(final AlphaWorld field_907_p, final long n) {
		super();
		this.field_905_r = new double[256];
		this.field_904_s = new double[256];
		this.field_903_t = new double[256];
		this.caves = new MapGenCaves();
		this.field_914_i = new int[32][32];
		this.world = field_907_p;
		this.rand = new Random(n);
		this.field_912_k = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_911_l = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_910_m = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_909_n = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_908_o = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
		this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_920_c = new NoiseGeneratorOctaves(this.rand, 8);
	}

	private void func_546_a(final int n, final int n2, final byte[] array) {
		final int n3 = 4;
		final int n4 = 64;
		final int n5 = n3 + 1;
		final int n6 = 17;
		final int n7 = n3 + 1;
		this.field_906_q = this.func_544_a(this.field_906_q, n * n3, 0, n2 * n3, n5, n6, n7);
		for (int i = 0; i < n3; ++i) {
			for (int j = 0; j < n3; ++j) {
				for (int k = 0; k < 16; ++k) {
					final double n8 = 0.125;
					double n9 = this.field_906_q[((i + 0) * n7 + (j + 0)) * n6 + (k + 0)];
					double n10 = this.field_906_q[((i + 0) * n7 + (j + 1)) * n6 + (k + 0)];
					double n11 = this.field_906_q[((i + 1) * n7 + (j + 0)) * n6 + (k + 0)];
					double n12 = this.field_906_q[((i + 1) * n7 + (j + 1)) * n6 + (k + 0)];
					final double n13 = (this.field_906_q[((i + 0) * n7 + (j + 0)) * n6 + (k + 1)] - n9) * n8;
					final double n14 = (this.field_906_q[((i + 0) * n7 + (j + 1)) * n6 + (k + 1)] - n10) * n8;
					final double n15 = (this.field_906_q[((i + 1) * n7 + (j + 0)) * n6 + (k + 1)] - n11) * n8;
					final double n16 = (this.field_906_q[((i + 1) * n7 + (j + 1)) * n6 + (k + 1)] - n12) * n8;
					for (int l = 0; l < 8; ++l) {
						final double n17 = 0.25;
						double n18 = n9;
						double n19 = n10;
						final double n20 = (n11 - n9) * n17;
						final double n21 = (n12 - n10) * n17;
						for (int n22 = 0; n22 < 4; ++n22) {
							int n23 = n22 + i * 4 << 11 | 0 + j * 4 << 7 | k * 8 + l;
							final int n24 = 128;
							final double n25 = 0.25;
							double n26 = n18;
							final double n27 = (n19 - n18) * n25;
							for (int n28 = 0; n28 < 4; ++n28) {
								int n29 = 0;
								if (k * 8 + l < n4) {
									if (this.world.winterMode && k * 8 + l >= n4 - 1) {
										n29 = AlphaBlock.ice.blockID;
									}
									else {
										n29 = AlphaBlock.waterMoving.blockID;
									}
								}
								if (n26 > 0.0) {
									n29 = AlphaBlock.stone.blockID;
								}
								array[n23] = (byte)n29;
								n23 += n24;
								n26 += n27;
							}
							n18 += n20;
							n19 += n21;
						}
						n9 += n13;
						n10 += n14;
						n11 += n15;
						n12 += n16;
					}
				}
			}
		}
	}

	private void func_545_b(final int n, final int n2, final byte[] array) {
		final int n3 = 64;
		final double n4 = 0.03125;
		this.field_905_r = this.field_909_n.func_807_a(this.field_905_r, n * 16, n2 * 16, 0.0, 16, 16, 1, n4, n4, 1.0);
		this.field_904_s = this.field_909_n.func_807_a(this.field_904_s, n2 * 16, 109.0134, n * 16, 16, 1, 16, n4, 1.0, n4);
		this.field_903_t = this.field_908_o.func_807_a(this.field_903_t, n * 16, n2 * 16, 0.0, 16, 16, 1, n4 * 2.0, n4 * 2.0, n4 * 2.0);
		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				final boolean b = this.field_905_r[x + z * 16] + this.rand.nextDouble() * 0.2 > 0.0;
				final boolean b2 = this.field_904_s[x + z * 16] + this.rand.nextDouble() * 0.2 > 3.0;
				final int n5 = (int)(this.field_903_t[x + z * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
				int n6 = -1;
				byte b3 = (byte)AlphaBlock.grass.blockID;
				byte b4 = (byte)AlphaBlock.dirt.blockID;
				for (int y = 127; y >= 0; --y) {
					final int n7 = (x * 16 + z) * 128 + y;
					if (y <= 0 + this.rand.nextInt(6) - 1) {
						array[n7] = (byte)AlphaBlock.bedrock.blockID;
					}
					else {
						final byte b5 = array[n7];
						if (b5 == 0) {
							n6 = -1;
						}
						else if (b5 == AlphaBlock.stone.blockID) {
							if (n6 == -1) {
								if (n5 <= 0) {
									b3 = 0;
									b4 = (byte)AlphaBlock.stone.blockID;
								}
								else if (y >= n3 - 4 && y <= n3 + 1) {
									b3 = (byte)AlphaBlock.grass.blockID;
									b4 = (byte)AlphaBlock.dirt.blockID;
									if (b2) {
										b3 = 0;
									}
									if (b2) {
										b4 = (byte)AlphaBlock.gravel.blockID;
									}
									if (b) {
										b3 = (byte)AlphaBlock.sand.blockID;
									}
									if (b) {
										b4 = (byte)AlphaBlock.sand.blockID;
									}
								}
								if (y < n3 && b3 == 0) {
									b3 = (byte)AlphaBlock.waterMoving.blockID;
								}
								n6 = n5;
								if (y >= n3 - 1) {
									array[n7] = b3;
								}
								else {
									array[n7] = b4;
								}
							}
							else if (n6 > 0) {
								--n6;
								array[n7] = b4;
							}
						}
					}
				}
			}
		}
	}

	public ProtoChunk generate(int cX, int cZ) {
		this.rand.setSeed(cX * 341873128712L + cZ * 132897987541L);
		byte[] array = new byte[32768];
		this.func_546_a(cX, cZ, array);
		this.func_545_b(cX, cZ, array);
		this.caves.func_867_a(this, this.world, cX, cZ, array);
		
		ProtoChunk result = new ProtoChunk(new ChunkPos(cX, cZ), UpgradeData.NO_UPGRADE_DATA);
		
		BlockPos.Mutable mut = new BlockPos.Mutable();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					int i = (x * 16 + z) * 128 + y;
					result.setBlockState(mut.set(x, y, z), BlockIDConverter.convert(array[i]), false);
				}
			}
		}
		
		return result;
	}

	private double[] func_544_a(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
		if (array == null) {
			array = new double[n4 * n5 * n6];
		}
		final double n7 = 684.412;
		final double n8 = 684.412;
		this.field_916_g = this.field_922_a.func_807_a(this.field_916_g, n, n2, n3, n4, 1, n6, 1.0, 0.0, 1.0);
		this.field_915_h = this.field_921_b.func_807_a(this.field_915_h, n, n2, n3, n4, 1, n6, 100.0, 0.0, 100.0);
		this.field_919_d = this.field_910_m.func_807_a(this.field_919_d, n, n2, n3, n4, n5, n6, n7 / 80.0, n8 / 160.0, n7 / 80.0);
		this.field_918_e = this.field_912_k.func_807_a(this.field_918_e, n, n2, n3, n4, n5, n6, n7, n8, n7);
		this.field_917_f = this.field_911_l.func_807_a(this.field_917_f, n, n2, n3, n4, n5, n6, n7, n8, n7);
		int n9 = 0;
		int n10 = 0;
		for (int i = 0; i < n4; ++i) {
			for (int j = 0; j < n6; ++j) {
				double n11 = (this.field_916_g[n10] + 256.0) / 512.0;
				if (n11 > 1.0) {
					n11 = 1.0;
				}
				final double n12 = 0.0;
				double n13 = this.field_915_h[n10] / 8000.0;
				if (n13 < 0.0) {
					n13 = -n13;
				}
				double n14 = n13 * 3.0 - 3.0;
				double n16;
				if (n14 < 0.0) {
					double n15 = n14 / 2.0;
					if (n15 < -1.0) {
						n15 = -1.0;
					}
					n16 = n15 / 1.4 / 2.0;
					n11 = 0.0;
				}
				else {
					if (n14 > 1.0) {
						n14 = 1.0;
					}
					n16 = n14 / 6.0;
				}
				final double n17 = n11 + 0.5;
				final double n18 = n5 / 2.0 + n16 * n5 / 16.0 * 4.0;
				++n10;
				for (int k = 0; k < n5; ++k) {
					double n19 = (k - n18) * 12.0 / n17;
					if (n19 < 0.0) {
						n19 *= 4.0;
					}
					final double n20 = this.field_918_e[n9] / 512.0;
					final double n21 = this.field_917_f[n9] / 512.0;
					final double n22 = (this.field_919_d[n9] / 10.0 + 1.0) / 2.0;
					double n23;
					if (n22 < 0.0) {
						n23 = n20;
					}
					else if (n22 > 1.0) {
						n23 = n21;
					}
					else {
						n23 = n20 + (n21 - n20) * n22;
					}
					double n24 = n23 - n19;
					if (k > n5 - 4) {
						final double n25 = (k - (n5 - 4)) / 3.0f;
						n24 = n24 * (1.0 - n25) + -10.0 * n25;
					}
					if (k < n12) {
						double n26 = (n12 - k) / 4.0;
						if (n26 < 0.0) {
							n26 = 0.0;
						}
						if (n26 > 1.0) {
							n26 = 1.0;
						}
						n24 = n24 * (1.0 - n26) + -10.0 * n26;
					}
					array[n9] = n24;
					++n9;
				}
			}
		}
		return array;
	}

	public void populate(int cX, int cZ) {
		int startX = cX * 16;
		int startZ = cZ * 16;
		this.rand.setSeed(this.world.randomSeed);
		this.rand.setSeed(cX * (this.rand.nextLong() / 2L * 2L + 1L) + cZ * (this.rand.nextLong() / 2L * 2L + 1L) ^ this.world.randomSeed);
		for (int i = 0; i < 8; ++i) {
			new WorldGenDungeons().populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		for (int j = 0; j < 10; ++j) {
			new WorldGenClay(32).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(128), startZ + this.rand.nextInt(16));
		}
		for (int k = 0; k < 20; ++k) {
			new WorldGenMinable(AlphaBlock.dirt.blockID, 32).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(128), startZ + this.rand.nextInt(16));
		}
		for (int l = 0; l < 10; ++l) {
			new WorldGenMinable(AlphaBlock.gravel.blockID, 32).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(128), startZ + this.rand.nextInt(16));
		}
		for (int n5 = 0; n5 < 20; ++n5) {
			new WorldGenMinable(AlphaBlock.oreCoal.blockID, 16).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(128), startZ + this.rand.nextInt(16));
		}
		for (int n6 = 0; n6 < 20; ++n6) {
			new WorldGenMinable(AlphaBlock.oreIron.blockID, 8).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(64), startZ + this.rand.nextInt(16));
		}
		for (int n7 = 0; n7 < 2; ++n7) {
			new WorldGenMinable(AlphaBlock.oreGold.blockID, 8).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(32), startZ + this.rand.nextInt(16));
		}
		for (int n8 = 0; n8 < 8; ++n8) {
			new WorldGenMinable(AlphaBlock.oreRedstone.blockID, 7).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(16), startZ + this.rand.nextInt(16));
		}
		for (int n9 = 0; n9 < 1; ++n9) {
			new WorldGenMinable(AlphaBlock.oreDiamond.blockID, 7).populate(this.world, this.rand, startX + this.rand.nextInt(16), this.rand.nextInt(16), startZ + this.rand.nextInt(16));
		}
		final double n10 = 0.5;
		int n11 = (int)((this.field_920_c.func_806_a(startX * n10, startZ * n10) / 8.0 + this.rand.nextDouble() * 4.0 + 4.0) / 3.0);
		if (n11 < 0) {
			n11 = 0;
		}
		if (this.rand.nextInt(10) == 0) {
			++n11;
		}
		WorldGenerator worldGenerator = new WorldGenTrees();
		if (this.rand.nextInt(10) == 0) {
			worldGenerator = new WorldGenBigTree();
		}
		for (int n12 = 0; n12 < n11; ++n12) {
			final int n13 = startX + this.rand.nextInt(16) + 8;
			final int n14 = startZ + this.rand.nextInt(16) + 8;
			worldGenerator.func_517_a(1.0, 1.0, 1.0);
			worldGenerator.populate(this.world, this.rand, n13, this.world.getHeightValue(n13, n14), n14);
		}
		for (int n15 = 0; n15 < 2; ++n15) {
			new WorldGenFlowers(AlphaBlock.plantYellow).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		if (this.rand.nextInt(2) == 0) {
			new WorldGenFlowers(AlphaBlock.plantRed).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		if (this.rand.nextInt(4) == 0) {
			new WorldGenFlowers(AlphaBlock.mushroomBrown).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		if (this.rand.nextInt(8) == 0) {
			new WorldGenFlowers(AlphaBlock.mushroomRed).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		for (int n16 = 0; n16 < 10; ++n16) {
			new WorldGenReed().populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		for (int n17 = 0; n17 < 1; ++n17) {
			new WorldGenCactus().populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(128), startZ + this.rand.nextInt(16) + 8);
		}
		for (int n18 = 0; n18 < 50; ++n18) {
			new WorldGenLiquids(AlphaBlock.waterStill.blockID).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(this.rand.nextInt(120) + 8), startZ + this.rand.nextInt(16) + 8);
		}
		for (int n19 = 0; n19 < 20; ++n19) {
			new WorldGenLiquids(AlphaBlock.lavaStill.blockID).populate(this.world, this.rand, startX + this.rand.nextInt(16) + 8, this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8), startZ + this.rand.nextInt(16) + 8);
		}
		for (int n20 = startX + 8 + 0; n20 < startX + 8 + 16; ++n20) {
			for (int n21 = startZ + 8 + 0; n21 < startZ + 8 + 16; ++n21) {
				final int func_685_d = this.world.getHeightValue(n20, n21);
				if (this.world.winterMode && func_685_d > 0 && func_685_d < 128 && this.world.getBlockId(n20, func_685_d, n21) == 0 && this.world.getBlockMaterial(n20, func_685_d - 1, n21).isSolid() && this.world.getBlockMaterial(n20, func_685_d - 1, n21) != Material.ICE) {
					this.world.setBlockId(n20, func_685_d, n21, AlphaBlock.snow.blockID);
				}
			}
		}
	}
}
