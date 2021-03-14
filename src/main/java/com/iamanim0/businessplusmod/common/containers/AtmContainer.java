package com.iamanim0.businessplusmod.common.containers;

import com.iamanim0.businessplusmod.common.tiles.AtmTileEntity;
import com.iamanim0.businessplusmod.core.init.BlockInit;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class AtmContainer extends Container {

	public final AtmTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public AtmContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public AtmContainer(final int windowId, final PlayerInventory playerInventory, final AtmTileEntity tileEntity) {
        super(ContainerTypeInit.ATM.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 9; k++) {
                    this.addSlot(new SlotItemHandler(tileEntity.inventory, i * 18 + j * 9 + k, k * 18 + 42, i * 50 + j * 18 + 40));
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, i * 9 + j + 9, j * 18 + 42, i * 18 + 140));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, i * 18 + 42, 198));
        }
    }

    /**
     * Generic & dynamic version of {@link Container#transferStackInSlot(PlayerEntity, int)}.
     * Handle when the stack in slot {@code index} is shift-clicked.
     * Normally this moves the stack between the player inventory and the other inventory(s).
     *
     * @param player the player passed in
     * @param index  the index passed in
     * @return the {@link ItemStack}
     */
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ATM_BLOCK.get());
    }

    private static AtmTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "PlayerInventory is null");
        Objects.requireNonNull(data, "Data is null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof AtmTileEntity) {
            ((AtmTileEntity) tileAtPos).hand = data.readEnumValue(Hand.class);
            return (AtmTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct - " + tileAtPos);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (slotId > 0 && getSlot(slotId).getStack() == player.getHeldItem(tileEntity.hand)) {
            return ItemStack.EMPTY;
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.tileEntity.freeAtm();
        super.onContainerClosed(playerIn);
    }

}
