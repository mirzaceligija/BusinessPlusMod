package com.iamanim0.businessplusmod.common.capability.storage;

import java.util.function.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MarketContents implements IInventory {
	
	private final ItemStackHandler marketComponentContents;
    private Predicate<PlayerEntity> canPlayerAccess = x-> true;
    private Notify markDirtyNotification = ()->{};
    private final Notify openInventoryNotificationLambda = ()->{};
    private final Notify closeInventoryNotificationLambda = ()->{};
	
    public MarketContents(int size, Predicate<PlayerEntity> canPlayerAccess, Notify notify) {
        this.marketComponentContents = new ItemStackHandler(size);
        this.canPlayerAccess = canPlayerAccess;
        this.markDirtyNotification = notify;
    }
    
    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for(int i = 0; i < marketComponentContents.getSlots(); i++){
			marketComponentContents.setStackInSlot(i, ItemStack.EMPTY);
        }
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return marketComponentContents.getSlots();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		for(int i = 0; i < marketComponentContents.getSlots(); i++){
            if (! marketComponentContents.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return marketComponentContents.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (count < 0) throw new IllegalArgumentException("count should be >= 0:" + count);
        return marketComponentContents.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		int maxStackSize = marketComponentContents.getSlotLimit(index);
        return marketComponentContents.extractItem(index, maxStackSize, false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		marketComponentContents.setStackInSlot(index, stack);
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		this.markDirtyNotification.invoke();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		// TODO Auto-generated method stub
		return canPlayerAccess.test(player); // Only does anything on server side
	}

    @Override
    public void openInventory(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }
}
