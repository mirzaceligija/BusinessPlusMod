package com.iamanim0.businessplusmod.common.containers;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.items.MoneyItem;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;
import com.iamanim0.businessplusmod.core.util.ItemStackHandlerWallet;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import net.minecraftforge.items.SlotItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class WalletContainer extends Container {
	
	private static final ResourceLocation MONEY_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "moneytagitem");
	
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ItemStackHandlerWallet itemStackHandlerFlowerBag2 = new ItemStackHandlerWallet(18);
	
	private final ItemStackHandlerWallet itemStackHandlerFlowerBag;
	private final ItemStack itemStackBeingHeld;		

	// must assign a slot number to each of the slots used by the GUI.
	// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
	// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
	//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
	//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
	//  36 - 51 = TileInventory slots, which map to our bag slot numbers 0 - 15)

	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int WALLET_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private static final int MAX_EXPECTED_BAG_SLOT_COUNT = 18;

	public static final int WALLET_INVENTORY_YPOS = 26;  // the ContainerScreenFlowerBag needs to know these so it can tell where to draw the Titles
	public static final int PLAYER_INVENTORY_YPOS = 84;

	public WalletContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, itemStackHandlerFlowerBag2, ItemStack.EMPTY);
	}

	public static WalletContainer createContainerServerSide(int windowID, PlayerInventory playerInventory, ItemStackHandlerWallet bagContents, ItemStack flowerBag) {
	  return new WalletContainer(windowID, playerInventory, bagContents, flowerBag);
	}

	public static WalletContainer createContainerClientSide(int windowID, PlayerInventory playerInventory, net.minecraft.network.PacketBuffer extraData) {
	  int numberOfFlowerSlots = extraData.readInt();
	  try {
		  ItemStackHandlerWallet itemStackHandlerFlowerBag = new ItemStackHandlerWallet(numberOfFlowerSlots);

	      return new WalletContainer(windowID, playerInventory, itemStackHandlerFlowerBag, ItemStack.EMPTY);
	  } catch (IllegalArgumentException iae) {
	      LOGGER.warn(iae);
	  }
	  return null;
	}

	/**
	* Creates a container suitable for server side or client side
	* @param windowId ID of the container
	* @param playerInv the inventory of the player
	* @param itemStackHandlerFlowerBag the inventory stored in the bag
	*/
	private WalletContainer(int windowId, PlayerInventory playerInv, ItemStackHandlerWallet itemStackHandlerFlowerBag, ItemStack itemStackBeingHeld) {
		super(ContainerTypeInit.WALLET.get(), windowId);
		this.itemStackHandlerFlowerBag = itemStackHandlerFlowerBag;
		this.itemStackBeingHeld = itemStackBeingHeld;
		
		countMoney();

		final int SLOT_X_SPACING = 18;
	    final int SLOT_Y_SPACING = 18;
	    final int HOTBAR_XPOS = 8;
	    final int HOTBAR_YPOS = 142;
	    // Add the players hotbar to the gui - the [xpos, ypos] location of each item
	    for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
	      int slotNumber = x;
	      addSlot(new Slot(playerInv, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
	    }

	    final int PLAYER_INVENTORY_XPOS = 8;
	    // Add the rest of the player's inventory to the gui
	    for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
	      for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
	        int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
	        int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
	        int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
	        addSlot(new Slot(playerInv, slotNumber, xpos, ypos));
	      }
	    }

	    int bagSlotCount = itemStackHandlerFlowerBag.getSlots();
	    if (bagSlotCount < 1 || bagSlotCount > MAX_EXPECTED_BAG_SLOT_COUNT) {
	      LOGGER.warn("Unexpected invalid slot count in ItemStackHandlerFlowerBag(" + bagSlotCount + ")");
	      bagSlotCount = MathHelper.clamp(bagSlotCount, 1, MAX_EXPECTED_BAG_SLOT_COUNT);
	    }

	    final int BAG_SLOTS_PER_ROW = 9;
	    final int BAG_INVENTORY_XPOS = 8;
	    // Add the tile inventory container to the gui
	    for (int bagSlot = 0; bagSlot < bagSlotCount; ++bagSlot) {
	      int slotNumber = bagSlot;
	      int bagRow = bagSlot / BAG_SLOTS_PER_ROW;
	      int bagCol = bagSlot % BAG_SLOTS_PER_ROW;
	      int xpos = BAG_INVENTORY_XPOS + SLOT_X_SPACING * bagCol;
	      int ypos = WALLET_INVENTORY_YPOS + SLOT_Y_SPACING * bagRow;
	      addSlot(new SlotItemHandler(itemStackHandlerFlowerBag, slotNumber, xpos, ypos));
	    }
	}

	// Check if the player is still able to access the container
	// In this case - if the player stops holding the bag, return false
	// Called on the server side only.
	@Override
	public boolean canInteractWith(@Nonnull PlayerEntity player) {
		ItemStack main = player.getHeldItemMainhand();
		ItemStack off = player.getHeldItemOffhand();
		return (!main.isEmpty() && main == itemStackBeingHeld) || (!off.isEmpty() && off == itemStackBeingHeld);
	}

	// This is where you specify what happens when a player shift clicks a slot in the gui
	//  (when you shift click a slot in the Bag Inventory, it moves it to the first available position in the hotbar and/or
	//    player inventory.  When you you shift-click a hotbar or player inventory item, it moves it to the first available
	//    position in the Bag inventory)
	// At the very least you must override this and return ItemStack.EMPTY or the game will crash when the player shift clicks a slot
	// returns ItemStack.EMPTY if the source slot is empty, or if none of the the source slot item could be moved
	//   otherwise, returns a copy of the source stack
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int sourceSlotIndex) {
	    Slot sourceSlot = inventorySlots.get(sourceSlotIndex);
	    if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
	    ItemStack sourceStack = sourceSlot.getStack();
	    @SuppressWarnings("unused")
		ItemStack copyOfSourceStack = sourceStack.copy();
	    final int BAG_SLOT_COUNT = itemStackHandlerFlowerBag.getSlots();

	    // Check if the slot clicked is one of the vanilla container slots
	    if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
	      // This is a vanilla container slot so merge the stack into the bag inventory
	      if (!mergeItemStack(sourceStack, WALLET_INVENTORY_FIRST_SLOT_INDEX, WALLET_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT, false)){
	        return ItemStack.EMPTY;  // EMPTY_ITEM
	      }
	    } else if (sourceSlotIndex >= WALLET_INVENTORY_FIRST_SLOT_INDEX && sourceSlotIndex < WALLET_INVENTORY_FIRST_SLOT_INDEX + BAG_SLOT_COUNT) {
	      // This is a bag slot so merge the stack into the players inventory
	      if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {	
	        return ItemStack.EMPTY;
	      }
	    } else {
	      LOGGER.warn("Invalid slotIndex: " + sourceSlotIndex);
	      return ItemStack.EMPTY;
	    }

	    // If stack size == 0 (the entire stack was moved) set slot contents to null
	    if (sourceStack.getCount() == 0) {
	    	sourceSlot.putStack(ItemStack.EMPTY);
	    } else {
	    	sourceSlot.onSlotChanged();
	    }
	    sourceSlot.onTake(player, sourceStack);
	    return sourceStack;
	}

	/**
	* Because capability nbt is not actually stored in the ItemStack nbt (it is created fresh each time we need to transmit or save an nbt), detectAndSendChanges
	*   does not work for our ItemFlowerBag ItemStack.  i.e. when the contents of ItemStackHandlerFlowerBag are changed, the nbt of ItemFlowerBag ItemStack don't change,
	*   so it is not sent to the client.
	* For this reason, we need to manually detect when it has changed and mark it dirty.
	* The easiest way is just to set a counter in the nbt tag and let the vanilla code notice that the itemstack has changed.
	* The side effect is that the player's hand moves down and up (because the client thinks it is a new ItemStack) but that's not objectionable.
	* Alternatively you could copy the code from vanilla detectAndSendChanges and tweak it to find the slot for itemStackBeingHeld and send it manually.
	*
	* Of course, if your ItemStack's capability doesn't affect the rendering of the ItemStack, i.e. the Capability is not needed on the client at all, then
	*   you don't need to bother with marking it dirty.
	*/
	@Override
	public void detectAndSendChanges() {
	  if (itemStackHandlerFlowerBag.isDirty()) {
		  CompoundNBT nbt = itemStackBeingHeld.getOrCreateTag();
	      int dirtyCounter = nbt.getInt("dirtyCounter");
	      nbt.putInt("dirtyCounter", dirtyCounter + 1);
	      itemStackBeingHeld.setTag(nbt);
	    }	  
	    super.detectAndSendChanges();
	}
	
	public double getMoneyIn(){
		countMoney();
		return this.MoneyCount;
	}
	
	@SuppressWarnings("static-access")
	public void countMoney() {
		final int NUMBER_OF_SLOTS = this.itemStackHandlerFlowerBag.getSlots();
		double temp = 0.00;
		this.MoneyCount = 0.00;
		for (int i = 0; i < NUMBER_OF_SLOTS; ++i) {
        	if (this.itemStackHandlerFlowerBag.getStackInSlot(i).getItem().isIn(ItemTags.getCollection().get(MONEY_TAG))) {
        		MoneyItem moneyItem = (MoneyItem) this.itemStackHandlerFlowerBag.getStackInSlot(i).getItem();
        		temp = (moneyItem.getCoinValue(this.itemStackHandlerFlowerBag.getStackInSlot(i).getItem()) * this.itemStackHandlerFlowerBag.getStackInSlot(i).getCount())/100.00;
        		this.MoneyCount = this.MoneyCount + temp;
        	}
		  }
	}
	
	public double MoneyCount;
}
