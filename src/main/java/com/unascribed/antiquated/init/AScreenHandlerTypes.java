package com.unascribed.antiquated.init;

import com.unascribed.antiquated.HighStackScreenHandlerWrapper;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class AScreenHandlerTypes {

	public static final ScreenHandlerType<HighStackScreenHandlerWrapper> ANTIQUE_DOUBLE_CHEST = new ScreenHandlerType<>((syncId, playerInventory) ->
			new HighStackScreenHandlerWrapper(new GenericContainerScreenHandler(AScreenHandlerTypes.ANTIQUE_DOUBLE_CHEST, syncId, playerInventory, new SimpleInventory(9*6) {
				@Override
				public int getMaxCountPerStack() {
					return 100;
				}
			}, 6)));
	
	public static final ScreenHandlerType<HighStackScreenHandlerWrapper> ANTIQUE_CHEST = new ScreenHandlerType<>((syncId, playerInventory) ->
			new HighStackScreenHandlerWrapper(new GenericContainerScreenHandler(AScreenHandlerTypes.ANTIQUE_CHEST, syncId, playerInventory, new SimpleInventory(9*3) {
				@Override
				public int getMaxCountPerStack() {
					return 100;
				}
			}, 3)));
	
}