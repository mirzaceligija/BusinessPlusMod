package com.iamanim0.businessplusmod.common.tiles;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.AtmContainer;
import com.iamanim0.businessplusmod.common.items.MoneyItem;
import com.iamanim0.businessplusmod.core.init.BlockInit;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

public class AtmTileEntity extends TileEntity implements INamedContainerProvider {
	
	private static final ResourceLocation MONEY_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "moneytagitem");

    public Hand hand;
    private boolean isAvailable = true;

    public final ItemStackHandler inventory = new ItemStackHandler(36) {
    	
        @Override
        public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
        
            if (slot >= 0 && slot < 18)
                return stack.getItem().isIn(ItemTags.getCollection().get(MONEY_TAG));
            else
                return false;
        }

        @Override
        protected void onContentsChanged(final int slot) {
            super.onContentsChanged(slot);
            markDirty();
        }
    };

    public AtmTileEntity() {
        super(TileEntityTypeInit.ATM.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(BlockInit.ATM_BLOCK.get().getTranslationKey());
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        lockAtm();
        return new AtmContainer(windowId, playerInventory, this);
    }

    public int deposit() {
        int value = 0;
        for (int i = 0; i < 18; i++) {
            int coinValue = MoneyItem.getCoinValue(inventory.getStackInSlot(i).getItem());
            value += coinValue * inventory.getStackInSlot(i).getCount();
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        return value;
    }

    public int withdraw(int value) {
        while (value > 0) {
            int max = MoneyItem.getMaxCoinValue(value);
            ItemStack stack = new ItemStack(MoneyItem.getCoinByValue(max), value / max);
            value -= (value / max) * max;
            for (int i = 18; i < 36 && !stack.isEmpty(); i++) {
                stack = insertItemForce(i, stack, false);
            }
            if (!stack.isEmpty()) {
                value += MoneyItem.getCoinValue(stack.getItem()) * stack.getCount();
                return value;
            }
        }
        return 0;
    }

    public ItemStack insertItemForce(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (slot < 0 || slot >= 36)
            throw new RuntimeException("Slot " + slot + " not in valid range - [0,36)");

        ItemStack existing = inventory.getStackInSlot(slot);

        int limit = Math.min(inventory.getSlotLimit(slot), stack.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                inventory.setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                final ItemStack newStack = ItemHandlerHelper.copyStackWithSize(stack, (reachedLimit ? limit : stack.getCount() + existing.getCount()));
                inventory.setStackInSlot(slot, newStack);
            }
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void lockAtm() {
        isAvailable = false;
    }

    public void freeAtm() {
        isAvailable = true;
    }
}
