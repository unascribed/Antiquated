package com.unascribed.antiquated;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.Chunk;

public class AntiquatedComponents implements ChunkComponentInitializer {

	public static class UncursedComponent implements Component, AutoSyncedComponent {

		private final Chunk provider;
		private int strength;
		
		public UncursedComponent(Chunk provider) {
			this.provider = provider;
		}
		
		public void setStrength(int strength) {
			this.strength = strength;
			AntiquatedComponents.UNCURSED.sync(provider);
			provider.setShouldSave(true);
		}
		
		public int getStrength() {
			return strength;
		}
		
		@Override
		public void readFromNbt(NbtCompound tag) {
			strength = tag.getInt("Strength");
		}

		@Override
		public void writeToNbt(NbtCompound tag) {
			tag.putInt("Strength", strength);
		}
		
		@Override
		public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
			buf.writeVarInt(strength);
		}
		
		@Override
		public void applySyncPacket(PacketByteBuf buf) {
			strength = buf.readVarInt();
		}

	}
	
	public static final ComponentKey<UncursedComponent> UNCURSED = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("antiquated", "uncursed"), UncursedComponent.class);
	
	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(UNCURSED, UncursedComponent::new);
	}

}
