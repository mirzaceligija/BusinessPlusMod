package com.iamanim0.businessplusmod.common.containers;

import com.iamanim0.businessplusmod.common.capability.provider.MarketplaceStateData;
import com.iamanim0.businessplusmod.common.capability.storage.MarketContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceStockContents;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer.InputSlot;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer.OutputSlot;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer.StockSlot;
import com.iamanim0.businessplusmod.common.tiles.MarketTileEntity;
import com.iamanim0.businessplusmod.common.tiles.MarketplaceTileEntity;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class MarketContainer extends Container {
	
	private MarketplaceStockContents stockContents;
	private MarketplaceContents inputContents;
    private World world;
    private MarketplaceStateData vendingStateData;
    
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
    public static final int VENDING_TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT;
    
    // Slot Index: The unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 41 for VendingContents
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
    private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
    private static final int FIRST_STOCK_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int FIRST_INPUT_SLOT_INDEX = FIRST_STOCK_SLOT_INDEX + STOCK_SLOT_COUNT;

    // GUI pos of inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 128;
    public static final int HOTBAR_XPOS = 8;
    public static final int HOTBAR_YPOS = 186;
    public static final int STOCK_INVENTORY_XPOS = 39;
    public static final int STOCK_INVENTORY_YPOS = -31;
    public static final int INPUT_SLOTS_XPOS = 117;
    public static final int INPUT_SLOTS_YPOS = 10;
    
    // Client side Creation
    public MarketContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {
        super(ContainerTypeInit.MARKET.get(), windowID);
        if(ContainerTypeInit.MARKET.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");

        // Dummys for client
        MarketplaceContents input = new MarketplaceContents(INPUT_SLOTS_COUNT);
        MarketplaceStockContents stock = new MarketplaceStockContents(STOCK_SLOT_COUNT);

        generateSlots(playerInventory, stock, input, playerInventory);
    }

	protected MarketContainer(ContainerType<?> type, int id) {
		super(type, id);
		// TODO Auto-generated constructor stub
	}
	

    // Server side Creation
    public MarketContainer(int windowID, PlayerInventory playerInventory, MarketplaceStockContents stock, MarketContents input){
        super(ContainerTypeInit.MARKETPLACE.get(), windowID);
        if(ContainerTypeInit.MARKETPLACE.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");

        trackIntArray(vendingStateData); // Tells vanilla to track stateData and keep synchronized between client and server containers!!
    }
	

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return stockContents.isUsableByPlayer(playerIn) && inputContents.isUsableByPlayer(playerIn);
	}

	private void generateSlots(PlayerInventory invPlayer, MarketplaceStockContents stock, MarketplaceContents input, PlayerInventory playerInventory){
        //this.stockContents = stock;
        this.inputContents = input;
        this.world = playerInventory.player.world;

        final int SLOT_X_SPACING = 18;
        final int SLOT_Y_SPACING = 18;

        // Add the players hotbar to the gui
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) { // x represents slot num
            addSlot(new Slot(invPlayer, x, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
        }

        // Add the expanded player inventory to gui
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
                addSlot(new Slot(invPlayer, slotNumber,  xpos, ypos));
            }
        }

        // Add Stocks slot to gui
        final int STOCK_Y_SPACING = 22;
        for (int x = 0; x < STOCK_ROW_COUNT; x++) {
            for (int y = 0; y < STOCK_COLUMN_COUNT; y++) {
                int slotNumber = y * STOCK_COLUMN_COUNT + x;
                int xpos = STOCK_INVENTORY_XPOS + x * SLOT_X_SPACING;
                int ypos = STOCK_INVENTORY_YPOS + y * STOCK_Y_SPACING;
                //addSlot(new StockSlot(stockContents, slotNumber, xpos, ypos));
            }
        }

        // Add Input slot to gui
        //addSlot(new InputSlot(inputContents, 0, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS));
    }
}
