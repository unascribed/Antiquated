package com.unascribed.antiquated.port;
import java.util.*;

import com.unascribed.antiquated.entity.AntiqueSpawnable;
import com.unascribed.antiquated.port.adapter.AlphaWorld;

import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AlphaSpawnerAnimals
{
    private int maxSpawns;
    private Class<?> spawnBaseClass;
    private EntityType<? extends LivingEntity>[] spawnSubclasses;
    private Set<ChunkPos> nearbyChunkSet;
    
    public AlphaSpawnerAnimals(final int maxSpawns, final Class<?> spawnBaseClass, final EntityType<? extends LivingEntity>... spawnSubclasses) {
        super();
        this.nearbyChunkSet = new HashSet<>();
        this.maxSpawns = maxSpawns;
        this.spawnBaseClass = spawnBaseClass;
        this.spawnSubclasses = spawnSubclasses;
    }
    
    public void func_1150_a(final AlphaWorld world) {
//        if (world.countEntities(this.spawnBaseClass) < this.maxSpawns) {
            for (int i = 0; i < 3; ++i) {
                this.func_1149_a(world, 1);
            }
//        }
    }
    
    protected BlockPos func_1151_a(final AlphaWorld world, final int n, final int n2) {
        return new BlockPos(n + world.rand.nextInt(16), world.rand.nextInt(128), n2 + world.rand.nextInt(16));
    }
    
    private int func_1149_a(final AlphaWorld world, final int n) {
        this.nearbyChunkSet.clear();
        for (PlayerEntity entityPlayer : world.getPlayerEntities()) {
            final int func_1108_b = MathHelper.floor(entityPlayer.getPos().x / 16.0);
            final int func_1108_b2 = MathHelper.floor(entityPlayer.getPos().z / 16.0);
            for (int n2 = 4, j = -n2; j <= n2; ++j) {
                for (int k = -n2; k <= n2; ++k) {
                    this.nearbyChunkSet.add(new ChunkPos(j + func_1108_b, k + func_1108_b2));
                }
            }
        }
        int n3 = 0;
        for (final ChunkPos chunkCoordIntPair : this.nearbyChunkSet) {
            if (world.rand.nextInt(10) != 0) {
                continue;
            }
            final int nextInt = world.rand.nextInt(this.spawnSubclasses.length);
            final BlockPos func_1151_a = this.func_1151_a(world, chunkCoordIntPair.x * 16, chunkCoordIntPair.z * 16);
            final int field_1111_a = func_1151_a.getX();
            final int field_1110_b = func_1151_a.getY();
            final int field_1112_c = func_1151_a.getZ();
            if (world.isBlockOpaqueCube(field_1111_a, field_1110_b, field_1112_c)) {
                return 0;
            }
            if (world.getBlockMaterial(field_1111_a, field_1110_b, field_1112_c) != Material.AIR) {
                return 0;
            }
            for (int l = 0; l < 3; ++l) {
                int n4 = field_1111_a;
                int n5 = field_1110_b;
                int n6 = field_1112_c;
                final int n7 = 6;
                for (int n8 = 0; n8 < 2; ++n8) {
                    n4 += world.rand.nextInt(n7) - world.rand.nextInt(n7);
                    n5 += world.rand.nextInt(1) - world.rand.nextInt(1);
                    n6 += world.rand.nextInt(n7) - world.rand.nextInt(n7);
                    if (world.isBlockOpaqueCube(n4, n5 - 1, n6) && !world.isBlockOpaqueCube(n4, n5, n6) && !world.getBlockMaterial(n4, n5, n6).isLiquid() && !world.isBlockOpaqueCube(n4, n5 + 1, n6)) {
                        final float n9 = n4 + 0.5f;
                        final float n10 = n5;
                        final float n11 = n6 + 0.5f;
                        if (world.getClosestPlayer(n9, n10, n11, 24.0) == null) {
//                            final float n12 = n9 - world.spawnX;
//                            final float n13 = n10 - world.spawnY;
//                            final float n14 = n11 - world.spawnZ;
//                            if (n12 * n12 + n13 * n13 + n14 * n14 >= 576.0f) {
                                LivingEntity entityLiving;
                                try {
                                    entityLiving = this.spawnSubclasses[nextInt].create((World) world.delegate);
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                    return n3;
                                }
                                entityLiving.refreshPositionAndAngles(n9, n10, n11, world.rand.nextFloat() * 360.0f, 0);
                                if (!(entityLiving instanceof AntiqueSpawnable) || ((AntiqueSpawnable)entityLiving).canSpawnHere()) {
                                    ++n3;
                                    world.spawnEntity(entityLiving);
//                                    if (entityLiving instanceof AntiqueSpiderEntity && world.rand.nextInt(100) == 0) {
//                                        final AntiqueSkeletonEntity entitySkeleton = new AntiqueSkeletonEntity(world);
//                                        entitySkeleton.func_365_c(n9, n10, n11, entityLiving.rotationYaw, 0.0f);
//                                        world.func_674_a(entitySkeleton);
//                                        entitySkeleton.func_386_g(entityLiving);
//                                    }
                                }
//                            }
                        }
                    }
                }
            }
        }
        return n3;
    }
}
