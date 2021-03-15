package com.unascribed.antiquated.init;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ASounds {

	public static final SoundEvent SILENCE = new SoundEvent(new Identifier("antiquated", "silence"));
	
	public static final SoundEvent GRASS_STEP = new SoundEvent(new Identifier("antiquated", "grass.step"));
	public static final SoundEvent GRASS_HIT = new SoundEvent(new Identifier("antiquated", "grass.hit"));
	
	public static final SoundEvent GRAVEL_STEP = new SoundEvent(new Identifier("antiquated", "gravel.step"));
	public static final SoundEvent GRAVEL_HIT = new SoundEvent(new Identifier("antiquated", "gravel.hit"));
	
	public static final SoundEvent SAND_STEP = new SoundEvent(new Identifier("antiquated", "sand.step"));
	public static final SoundEvent SAND_HIT = new SoundEvent(new Identifier("antiquated", "sand.hit"));
	
	public static final SoundEvent STONE_STEP = new SoundEvent(new Identifier("antiquated", "stone.step"));
	public static final SoundEvent STONE_HIT = new SoundEvent(new Identifier("antiquated", "stone.hit"));
	
	public static final SoundEvent WOOD_STEP = new SoundEvent(new Identifier("antiquated", "wood.step"));
	public static final SoundEvent WOOD_HIT = new SoundEvent(new Identifier("antiquated", "wood.hit"));
	
	public static final SoundEvent COW_AMBIENT = new SoundEvent(new Identifier("antiquated", "cow.ambient"));
	public static final SoundEvent COW_HURT = new SoundEvent(new Identifier("antiquated", "cow.hurt"));
	
	public static final SoundEvent SKELETON_AMBIENT = new SoundEvent(new Identifier("antiquated", "skeleton.ambient"));
	public static final SoundEvent SKELETON_HURT = new SoundEvent(new Identifier("antiquated", "skeleton.hurt"));
	
	public static final SoundEvent DOOR_OPEN = new SoundEvent(new Identifier("antiquated", "door.open"));
	public static final SoundEvent DOOR_CLOSE = new SoundEvent(new Identifier("antiquated", "door.close"));
	
	public static final SoundEvent BOW = new SoundEvent(new Identifier("antiquated", "bow"));
	public static final SoundEvent BOWHIT = new SoundEvent(new Identifier("antiquated", "bowhit"));
	
	public static final SoundEvent EXPLODE = new SoundEvent(new Identifier("antiquated", "explode"));
	
	public static final SoundEvent HIT = new SoundEvent(new Identifier("antiquated", "hit"));
	
	public static final BlockSoundGroup GRASS_SOUNDS = new BlockSoundGroup(1, 1, SoundEvents.BLOCK_GRASS_BREAK, GRASS_STEP, SoundEvents.BLOCK_GRASS_PLACE, GRASS_HIT, SILENCE);
	public static final BlockSoundGroup GRAVEL_SOUNDS = new BlockSoundGroup(1, 1, SoundEvents.BLOCK_GRAVEL_BREAK, GRAVEL_STEP, SoundEvents.BLOCK_GRAVEL_PLACE, GRAVEL_HIT, SILENCE);
	public static final BlockSoundGroup SAND_SOUNDS = new BlockSoundGroup(1, 1, SoundEvents.BLOCK_SAND_BREAK, SAND_STEP, SoundEvents.BLOCK_SAND_PLACE, SAND_HIT, SILENCE);
	public static final BlockSoundGroup STONE_SOUNDS = new BlockSoundGroup(1, 1, SoundEvents.BLOCK_STONE_BREAK, STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, STONE_HIT, SILENCE);
	public static final BlockSoundGroup WOOD_SOUNDS = new BlockSoundGroup(1, 1, SoundEvents.BLOCK_WOOD_BREAK, WOOD_STEP, SoundEvents.BLOCK_WOOD_PLACE, WOOD_HIT, SILENCE);
	
}
