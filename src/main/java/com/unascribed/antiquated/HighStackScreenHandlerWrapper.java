package com.unascribed.antiquated;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;

public class HighStackScreenHandlerWrapper extends GenericContainerScreenHandler {

	private final ScreenHandler delegate;

	public HighStackScreenHandlerWrapper(GenericContainerScreenHandler delegate) {
		super(delegate.getType(), delegate.syncId, (PlayerInventory)delegate.slots.get(delegate.slots.size()-1).inventory, delegate.getInventory(), delegate.getRows());
		this.delegate = delegate;
		this.slots = delegate.slots;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return delegate.getType();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public void addListener(ScreenHandlerListener listener) {
		delegate.addListener(listener);
	}

	@Override
	public void removeListener(ScreenHandlerListener listener) {
		delegate.removeListener(listener);
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public DefaultedList<ItemStack> getStacks() {
		return delegate.getStacks();
	}

	@Override
	public void sendContentUpdates() {
		try {
			Antiquated.increaseStackSize.get().increment();
			delegate.sendContentUpdates();
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		try {
			Antiquated.increaseStackSize.get().increment();
			return delegate.onButtonClick(player, id);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public Slot getSlot(int index) {
		return delegate.getSlot(index);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		try {
			Antiquated.increaseStackSize.get().increment();
			return delegate.transferSlot(player, index);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public ItemStack onSlotClick(int i, int j, SlotActionType actionType,
			PlayerEntity playerEntity) {
		try {
			Antiquated.increaseStackSize.get().increment();
			return delegate.onSlotClick(i, j, actionType, playerEntity);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		try {
			Antiquated.increaseStackSize.get().increment();
			return delegate.canInsertIntoSlot(stack, slot);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public void close(PlayerEntity player) {
		delegate.close(player);
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		try {
			Antiquated.increaseStackSize.get().increment();
			delegate.onContentChanged(inventory);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		try {
			Antiquated.increaseStackSize.get().increment();
			delegate.setStackInSlot(slot, stack);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public void updateSlotStacks(List<ItemStack> stacks) {
		try {
			Antiquated.increaseStackSize.get().increment();
			delegate.updateSlotStacks(stacks);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}

	@Override
	public void setProperty(int id, int value) {
		delegate.setProperty(id, value);
	}

	@Override
	public short getNextActionId(PlayerInventory playerInventory) {
		return delegate.getNextActionId(playerInventory);
	}

	@Override
	public boolean isNotRestricted(PlayerEntity player) {
		return delegate.isNotRestricted(player);
	}

	@Override
	public void setPlayerRestriction(PlayerEntity player,
			boolean unrestricted) {
		delegate.setPlayerRestriction(player, unrestricted);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return delegate.canUse(player);
	}

	@Override
	public boolean canInsertIntoSlot(Slot slot) {
		try {
			Antiquated.increaseStackSize.get().increment();
			return delegate.canInsertIntoSlot(slot);
		} finally {
			Antiquated.increaseStackSize.get().decrement();
		}
	}



}
