package com.iamanim0.businessplusmod.common.containers;

import java.util.Objects;

import com.iamanim0.businessplusmod.common.capability.provider.TradeInStateData;
import com.iamanim0.businessplusmod.common.capability.storage.TradeInContents;
import com.iamanim0.businessplusmod.common.capability.storage.TradeInStockContents;
import com.iamanim0.businessplusmod.common.tiles.TradeInOreTileEntity;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TradeInOreContainer extends Container{
	
	public final TradeInOreTileEntity tileEntity;
	private TradeInStockContents stockContents;
    private TradeInContents inputContents;
    private TradeInContents outputContents;
    private World world;
    
    // Slot Calculations
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    
    public static final int STOCK_ROW_COUNT = TradeInOreTileEntity.STOCK_ROW_COUNT;
    public static final int STOCK_COLUMN_COUNT = TradeInOreTileEntity.STOCK_COLUMN_COUNT;
    public static final int STOCK_SLOT_COUNT = TradeInOreTileEntity.STOCK_SLOT_COUNT;
    public static final int INPUT_SLOTS_COUNT = TradeInOreTileEntity.INPUT_SLOTS_COUNT;
    public static final int OUTPUT_SLOTS_COUNT = TradeInOreTileEntity.OUTPUT_SLOTS_COUNT;
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
	
    private TradeInStateData tradeInStateData;
    
	public TradeInOreContainer(int windowID, PlayerInventory playerInventory, TradeInStockContents stockContents,
			TradeInContents inputContents, TradeInContents outputContents, TradeInOreTileEntity te,
			TradeInStateData tradeInStateData) {
		// TODO Auto-generated constructor stub
		super(ContainerTypeInit.TRADEIN_ORE.get(), windowID);
		 if(ContainerTypeInit.TRADEIN_ORE.get() == null)
	            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");
		// TODO Auto-generated constructor stub
		this.tileEntity = te;
        this.tradeInStateData = tradeInStateData;
        this.world = playerInventory.player.world;
        this.tileEntity.generateStockContents();
        
        trackIntArray(tradeInStateData); // Tells vanilla to track stateData and keep synchronized between client and server containers!!
        generateSlots(playerInventory, inputContents, outputContents, stockContents);
	}
	
	// Client side Creation
	public TradeInOreContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {		
		super(ContainerTypeInit.TRADEIN_ORE.get(), windowID);

        if(ContainerTypeInit.TRADEIN_ORE.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");
        
        this.tileEntity = getTileEntity(playerInventory, extraData);
        this.tileEntity.generateStockContents();

        // Dummys for client
        this.tradeInStateData = new TradeInStateData();
        TradeInContents input = new TradeInContents(INPUT_SLOTS_COUNT);
        TradeInContents output = new TradeInContents(OUTPUT_SLOTS_COUNT);
        TradeInStockContents stock = new TradeInStockContents(STOCK_SLOT_COUNT);
        
        generateSlots(playerInventory, input, output, stock);
	}
	
	private static TradeInOreTileEntity getTileEntity(final PlayerInventory playerInventory,
			final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof TradeInOreTileEntity) {
			return (TradeInOreTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
		
	private void generateSlots(PlayerInventory playerInventory,TradeInContents input,
			TradeInContents output, TradeInStockContents stock) {
		
		this.tradeInStateData.set(1, this.tileEntity.getVendingStateData(1));
		
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
	
    public class InputSlot extends Slot {
        public InputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }
    
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
    	
    	if(clickTypeIn == ClickType.PICKUP_ALL || clickTypeIn == ClickType.QUICK_MOVE)
			return ItemStack.EMPTY;
    	// TODO Auto-generated method stub
		try {
			System.out.println(slotId + " " + dragType + " " + clickTypeIn);
			if ((slotId >= 0 && //PLAYER INVENTORY
                    slotId < 36 + 1) || (slotId == -999)) {
                return super.slotClick(slotId, dragType, clickTypeIn, player);
            } else if (slotId >= 37 && // STOCK INVENTORY
                    slotId < 120 +1) {
            	return ItemStack.EMPTY;
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
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return true;
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
}
