package com.iamanim0.businessplusmod.common.containers;

import java.util.Objects;

import com.iamanim0.businessplusmod.common.tiles.VaultChestTileEntity;
import com.iamanim0.businessplusmod.core.init.BlockInit;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class VaultChestContainer extends Container {
	
	//Ovdje bukvalno opisujemo container gui-a i pozicije slotova itd
	
	public final VaultChestTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public VaultChestContainer(final int windowId, final PlayerInventory playerInventory, final VaultChestTileEntity tileEntity) {
		super(ContainerTypeInit.VAULT_CHEST.get(), windowId);
		// TODO Auto-generated constructor stub
		
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		
		// Main Inventory
		int startX = 8;
		int startY = 18;
		int slotSizePlus2 = 18;
		for (int row = 0; row < 4; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(tileEntity, (row * 9) + column, startX + (column * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}

		// Main Player Inventory
		int startPlayerInvY = startY * 5 + 12;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
						startPlayerInvY + (row * slotSizePlus2)));
			}
		}

		// Hotbar
		int hotbarY = startPlayerInvY + (startPlayerInvY / 2) + 7;
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
		}
	}
	
	private static VaultChestTileEntity getTileEntity(final PlayerInventory playerInventory,
			final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof VaultChestTileEntity) {
			return (VaultChestTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
	
	public VaultChestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.VAULT_CHEST.get());
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		// TODO Auto-generated method stub
		try {
			if(slotId>=36 && slotId<=71 || slotId == -999) {
				return super.slotClick(slotId, dragType, clickTypeIn, player);
			} 
			
            System.out.println(slotId + " " + dragType + " " + clickTypeIn);
            
            player.addItemStackToInventory(tileEntity.getStackInSlot(slotId));
            this.tileEntity.setContents(slotId, tileEntity.getStackInSlot(slotId));
            return super.slotClick(slotId, dragType, clickTypeIn, player);
            //return super.slotClick(slotId, dragType, clickTypeIn, player);
		} catch (Exception exception) {
            System.out.println("CRASH IN VENDING CONTAINER- slotid:" + slotId + " dragType:" + dragType + " clickType:" + clickTypeIn + " player:" + player);
            return ItemStack.EMPTY;
        }
		//return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 36) {
				if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
}
