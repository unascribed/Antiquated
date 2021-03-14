package com.unascribed.antiquated.port.adapter;

import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;

public interface AlphaIBlockAccess {

    int getBlockId(int x, int y, int z);
    
    BlockEntity getBlockEntity(int x, int y, int z);
    
    Material getBlockMaterial(int x, int y, int z);
    
    boolean isSolid(int x, int y, int z);
	
}
