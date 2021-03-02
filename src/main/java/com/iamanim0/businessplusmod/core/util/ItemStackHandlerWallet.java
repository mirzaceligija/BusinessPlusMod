package com.iamanim0.businessplusmod.core.util;

import javax.annotation.Nonnull;

import com.iamanim0.businessplusmod.BusinessPlusMod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerWallet extends ItemStackHandler {
	
	private static final ResourceLocation CURRENCY_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "currencytagitem");

	public static final int MIN_MONEY_SLOTS = 1;
	public static final int MAX_MONEY_SLOTS = 18;
	
	private boolean isDirty = true;

	public ItemStackHandlerWallet(int numberOfSlots) {
	  super(MathHelper.clamp(numberOfSlots, MIN_MONEY_SLOTS, MAX_MONEY_SLOTS));
	  if (numberOfSlots < MIN_MONEY_SLOTS || numberOfSlots > MAX_MONEY_SLOTS) {
	    throw new IllegalArgumentException("Invalid number of flower slots: " + numberOfSlots);
	  }
	  
	  //setStackInSlot(0, new ItemStack(ItemInit.COIN10_ITEM.get()));
	}
	  
	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
	  if (slot < 0 || slot >= MAX_MONEY_SLOTS) {
	    throw new IllegalArgumentException("Invalid slot number: " + slot);
	  }
	  if (stack.isEmpty()) return false;
	  Item item = stack.getItem();
	  if (item.isIn(ItemTags.getCollection().get(CURRENCY_TAG))) return true;
	  return false;
	}
	  
	/**Count how many empty slots are in the bag
	* @return the number of empty slots
	*/
	public int getNumberOfEmptySlots() {
	  final int NUMBER_OF_SLOTS = getSlots();

	  int emptySlotCount = 0;
	  for (int i = 0; i < NUMBER_OF_SLOTS; ++i) {
	    if (getStackInSlot(i) == ItemStack.EMPTY) {
	      ++emptySlotCount;
	    }
	  }
	  return emptySlotCount;
	}
	  
	/** returns true if the contents have changed since the last call.
	* Resets to false after each call.
	* @return true if changed since the last call
	*/
	public boolean isDirty() {
	  boolean currentState = isDirty;
	  isDirty = false;
	  return currentState;
	}
	  
	/** Called whenever the contents of the bag have changed.
	*   We need to do this manually in order to make sure that the server sends a synchronisation packet to the client for the parent ItemStack
	*   The reason is because capability information is not stored in the ItemStack nbt tag, so vanilla does not notice when
	*   the flowerbag's capability has changed.
	* @param slot
	*/
	protected void onContentsChanged(int slot) {
	  isDirty = true;
	}
}
