package com.iamanim0.businessplusmod.common.containers;

import java.util.Objects;

import com.iamanim0.businessplusmod.common.capability.provider.MarketStateData;
import com.iamanim0.businessplusmod.common.capability.provider.MarketStateData;
import com.iamanim0.businessplusmod.common.capability.storage.MarketContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketStockContents;
import com.iamanim0.businessplusmod.common.containers.MarketContainer.InputSlot;
import com.iamanim0.businessplusmod.common.items.MoneyItem;
import com.iamanim0.businessplusmod.common.tiles.MarketTileEntity;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;
import com.iamanim0.businessplusmod.core.init.ItemInit;
import com.iamanim0.businessplusmod.core.util.PriceList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MarketContainer extends Container {

	public final MarketTileEntity tileEntity;
	private MarketStockContents stockContents;
    private MarketContents inputContents;
    private MarketContents outputContents;
    private World world;
	
    // Slot Calculations
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    
    public static final int STOCK_ROW_COUNT = MarketTileEntity.STOCK_ROW_COUNT;
    public static final int STOCK_COLUMN_COUNT = MarketTileEntity.STOCK_COLUMN_COUNT;
    public static final int STOCK_SLOT_COUNT = MarketTileEntity.STOCK_SLOT_COUNT;
    public static final int INPUT_SLOTS_COUNT = MarketTileEntity.INPUT_SLOTS_COUNT;
    public static final int OUTPUT_SLOTS_COUNT = MarketTileEntity.OUTPUT_SLOTS_COUNT;
    public static final int VENDING_TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;
    
    // Slot Index: The unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 41 for VendingContents
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
    private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
    private static final int FIRST_STOCK_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int FIRST_INPUT_SLOT_INDEX = FIRST_STOCK_SLOT_INDEX + STOCK_SLOT_COUNT;
    private static final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;
	
    // GUI pos of inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 49;
    public static final int PLAYER_INVENTORY_YPOS = 174;
    public static final int HOTBAR_XPOS = 48;
    public static final int HOTBAR_YPOS = 232;
    public static final int STOCK_INVENTORY_XPOS = 21;
    public static final int STOCK_INVENTORY_YPOS = 16;
    public static final int OUTPUT_SLOTS_XPOS = 17;
    public static final int OUTPUT_SLOTS_YPOS = 140;
    public static final int INPUT_SLOTS_XPOS = 225;
    public static final int INPUT_SLOTS_YPOS = 173;
	
    private MarketStateData vendingStateData;
	
	// Client side Creation
	public MarketContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {		
		super(ContainerTypeInit.MARKET.get(), windowID);

        if(ContainerTypeInit.MARKET.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");
        
        this.tileEntity = getTileEntity(playerInventory, extraData);
        this.tileEntity.generateStockContents();

        // Dummys for client
        this.vendingStateData = new MarketStateData();
        MarketContents input = new MarketContents(INPUT_SLOTS_COUNT);
        MarketContents output = new MarketContents(OUTPUT_SLOTS_COUNT);
        MarketStockContents stock = new MarketStockContents(STOCK_SLOT_COUNT);
        
        generateSlots(playerInventory, input, output, stock);
	}
	

	// Server side Creation
	public MarketContainer(int windowID, PlayerInventory playerInventory, MarketStockContents stockContents,
			MarketContents inputContents, MarketContents outputContents, MarketTileEntity te, MarketStateData marketplaceStateData ) {
		super(ContainerTypeInit.MARKET.get(), windowID);
		 if(ContainerTypeInit.MARKET.get() == null)
	            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");
		// TODO Auto-generated constructor stub
		this.tileEntity = te;
        this.vendingStateData = marketplaceStateData;
        this.world = playerInventory.player.world;
        this.tileEntity.generateStockContents();
        
        trackIntArray(vendingStateData); // Tells vanilla to track stateData and keep synchronized between client and server containers!!
        generateSlots(playerInventory, inputContents, outputContents, stockContents);
	}
	
	private void generateSlots(PlayerInventory playerInventory, MarketContents input,
			MarketContents output, MarketStockContents stock) {
		
		this.vendingStateData.set(1, this.tileEntity.getVendingStateData(1));
		
		// TODO Auto-generated method stub
		this.stockContents = stock;
        this.inputContents = input;
        this.outputContents = output;
        this.world = playerInventory.player.world;
		
		final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;
        
        // Add the players hotbar to the gui
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) { // x represents slot num
            addSlot(new Slot(playerInventory, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }
        
        // Add the expanded player inventory to gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(playerInventory, slotNumber,  xpos, ypos));
            }
        }
        
        // Add Input slot to gui
        addSlot(new InputSlot(inputContents, 0, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS));
        
        /*
        // Add Output slots to gui
        for (int x = 0; x < OUTPUT_SLOTS_COUNT; x++) { // x is slot num
            addSlot(new OutputSlot(outputContents, x, OUTPUT_SLOTS_XPOS + SLOT_X_SPACING * x, OUTPUT_SLOTS_YPOS));
        }
        */
        
        // Add Stocks slot to gui
        for (int x = 0; x < STOCK_ROW_COUNT ; x++) {
            for (int y = 0; y < STOCK_COLUMN_COUNT; y++) {
                int slotNumber = x * STOCK_COLUMN_COUNT + y;
                int xpos = STOCK_INVENTORY_XPOS + y * SLOT_X_SPACING;
                int ypos = STOCK_INVENTORY_YPOS + x * SLOT_Y_SPACING;
                addSlot(new StockSlot(stockContents, slotNumber, xpos, ypos));
            }
        }
	}
	
    // --- Slot customization ----
    public class StockSlot extends Slot {
        public StockSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }
	
    public class OutputSlot extends Slot {
        public OutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }
	
    public class InputSlot extends Slot {
        public InputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }
	
	private static MarketTileEntity getTileEntity(final PlayerInventory playerInventory,
			final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MarketTileEntity) {
			return (MarketTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
	

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		// TODO Auto-generated method stub
		
		
		if(clickTypeIn == ClickType.PICKUP_ALL || clickTypeIn == ClickType.QUICK_MOVE)
			return ItemStack.EMPTY;
		
		try {
			System.out.println(slotId + " " + dragType + " " + clickTypeIn);
			if ((slotId >= 0 && //PLAYER INVENTORY
                    slotId < 36 + 1) || (slotId == -999)) {
                return super.slotClick(slotId, dragType, clickTypeIn, player);
            } else if (slotId >= 37 && // STOCK INVENTORY
                    slotId < 120 + 1) {
                return stockSlotClick(slotId, dragType, clickTypeIn, player);
            } else if (slotId == 36) {
            	return super.slotClick(slotId, dragType, clickTypeIn, player);
            } else {
            	return ItemStack.EMPTY;
            }
		} catch (Exception exception) {
            System.out.println("CRASH IN VENDING CONTAINER- slotid:" + slotId + " dragType:" + dragType + " clickType:" + clickTypeIn + " player:" + player);
            return ItemStack.EMPTY;
        }
	}
	
    private ItemStack stockSlotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {  
    	int itemPrice = PriceList.getPriceForItemMINER(this.tileEntity.getStockContents(slotId), 100);
    	
    	if(clickTypeIn == ClickType.PICKUP) {	
    		
    		if(this.tileEntity.currentMoney < itemPrice)
    			return ItemStack.EMPTY;
    		
        	buyItems(itemPrice);
    		
    		int freeSlot = this.outputContents.getFreeSlot();
    		
	    	if(!this.outputContents.isEmpty()) { 
	    		
	        	int slotIndex = this.outputContents.getItemStackIndex(this.tileEntity.getStockContents(slotId));
	    		
	    		if(slotIndex == 99 && freeSlot !=99) {
	    			this.outputContents.setInventorySlotContents(freeSlot, this.tileEntity.getStockContents(slotId));
	    			
	    			InventoryHelper.dropInventoryItems(world, player, outputContents);
	        		return ItemStack.EMPTY;
	    		} else {
	    			this.outputContents.increaseStackSize(slotIndex, this.tileEntity.getStockContents(slotId));
	    			InventoryHelper.dropInventoryItems(world, player, outputContents);
	    			return ItemStack.EMPTY;
	    		}
	    	} else {
	    		this.outputContents.setInventorySlotContents(freeSlot, this.tileEntity.getStockContents(slotId));
	    		InventoryHelper.dropInventoryItems(world, player, outputContents);
	    		return ItemStack.EMPTY;
	    	}
    	} else {
    		return ItemStack.EMPTY;
    	}
    }
    
    // Shift clicking
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }
    
    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
    	// TODO Auto-generated method stub
    	this.tileEntity.dropMoney(playerIn);
    	super.onContainerClosed(playerIn);
    }
	
	public int getInputCount() {
		return this.inputContents.getMoneyCount();
	}
	
    public int getVendingStateData(int index){
        return vendingStateData.get(index);
    }

    public void setVendingStateData(int index, int value){
        this.vendingStateData.set(index, value);
    }
    
    public void buyItems(int price) {
    	if(this.tileEntity.currentMoney> price)
    		this.tileEntity.currentMoney -= price;
    	else 
    		return;
    }
}
