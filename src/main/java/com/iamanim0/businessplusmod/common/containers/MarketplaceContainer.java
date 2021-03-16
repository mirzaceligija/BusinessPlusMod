package com.iamanim0.businessplusmod.common.containers;

import com.iamanim0.businessplusmod.common.capability.provider.MarketplaceStateData;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceStockContents;
import com.iamanim0.businessplusmod.common.tiles.MarketplaceTileEntity;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class MarketplaceContainer  extends Container {
	
	private MarketplaceStockContents stockContents;
    private MarketplaceContents inputContents;
    private MarketplaceContents outputContents;
    private World world;
    
    // Slot Calculations
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    public static final int STOCK_ROW_COUNT = MarketplaceTileEntity.STOCK_ROW_COUNT;
    public static final int STOCK_COLUMN_COUNT = MarketplaceTileEntity.STOCK_COLUMN_COUNT;
    public static final int STOCK_SLOT_COUNT = MarketplaceTileEntity.STOCK_SLOT_COUNT;
    public static final int INPUT_SLOTS_COUNT = MarketplaceTileEntity.INPUT_SLOTS_COUNT;
    public static final int OUTPUT_SLOTS_COUNT = MarketplaceTileEntity.OUTPUT_SLOTS_COUNT;
    public static final int VENDING_TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    // Slot Index: The unique index for all slots in this container i.e. 0 - 35 for invPlayer then 36 - 41 for VendingContents
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
    private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;
    private static final int FIRST_STOCK_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int FIRST_INPUT_SLOT_INDEX = FIRST_STOCK_SLOT_INDEX + STOCK_SLOT_COUNT;
    private static final int FIRST_OUTPUT_SLOT_INDEX = FIRST_INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;

    // GUI pos of inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 128;
    public static final int HOTBAR_XPOS = 8;
    public static final int HOTBAR_YPOS = 186;
    public static final int STOCK_INVENTORY_XPOS = 39;
    public static final int STOCK_INVENTORY_YPOS = -31;
    public static final int OUTPUT_SLOTS_XPOS = 61;
    public static final int OUTPUT_SLOTS_YPOS = 71;
    public static final int INPUT_SLOTS_XPOS = 117;
    public static final int INPUT_SLOTS_YPOS = 10;

    // slot number is the slot number within each component;
    // i.e. invPlayer slots 0 - 35 (hotbar 0 - 8 then main inventory 9 to 35)
    // and vending: input slots 0 - 2, output slots 0, stock slots 0 - 16 (for 1x2)

    private MarketplaceStateData vendingStateData;

 // Client side Creation
    public MarketplaceContainer(int windowID, PlayerInventory playerInventory, PacketBuffer extraData) {
        super(ContainerTypeInit.MARKETPLACE.get(), windowID);
        if(ContainerTypeInit.MARKETPLACE.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");

        // Dummys for client
        this.vendingStateData = new MarketplaceStateData();
        MarketplaceContents input = new MarketplaceContents(INPUT_SLOTS_COUNT);
        MarketplaceContents output = new MarketplaceContents(OUTPUT_SLOTS_COUNT);
        MarketplaceStockContents stock = new MarketplaceStockContents(STOCK_SLOT_COUNT);

        generateSlots(playerInventory, stock, input, output, playerInventory);
    }

    // Server side Creation
    public MarketplaceContainer(int windowID, PlayerInventory playerInventory, MarketplaceStockContents stock, MarketplaceContents input, MarketplaceContents output, MarketplaceStateData vendingStateData){
        super(ContainerTypeInit.MARKETPLACE.get(), windowID);
        if(ContainerTypeInit.MARKETPLACE.get() == null)
            throw new IllegalStateException("Must initialise containerTypeVendingContainer before constructing a ContainerVending!");

        this.vendingStateData = vendingStateData;
        trackIntArray(vendingStateData); // Tells vanilla to track stateData and keep synchronized between client and server containers!!

        generateSlots(playerInventory, stock, input, output, playerInventory);
    }

    private void generateSlots(PlayerInventory invPlayer, MarketplaceStockContents stock, MarketplaceContents input, MarketplaceContents output, PlayerInventory playerInventory){
        this.stockContents = stock;
        this.inputContents = input;
        this.outputContents = output;
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
                addSlot(new StockSlot(stockContents, slotNumber, xpos, ypos));
            }
        }

        // Add Input slot to gui
        addSlot(new InputSlot(inputContents, 0, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS));

        // Add Output slots to gui
        for (int x = 0; x < OUTPUT_SLOTS_COUNT; x++) { // x is slot num
            addSlot(new OutputSlot(outputContents, x, OUTPUT_SLOTS_XPOS + SLOT_X_SPACING * x, OUTPUT_SLOTS_YPOS));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return stockContents.isUsableByPlayer(playerIn) && inputContents.isUsableByPlayer(playerIn) && outputContents.isUsableByPlayer(playerIn);
    }

    // ---- Slot manipulation ----

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        try {
            System.out.println(slotId + " " + dragType + " " + clickTypeIn);
            if ((slotId >= PLAYER_INVENTORY_FIRST_SLOT_INDEX && //PLAYER INVENTORY
                    slotId < PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT) || (slotId == -999)) {
                return super.slotClick(slotId, dragType, clickTypeIn, player);
            } else if (slotId >= FIRST_STOCK_SLOT_INDEX && // STOCK INVENTORY
                    slotId < FIRST_INPUT_SLOT_INDEX) {
                return stockSlotClick(slotId, dragType, clickTypeIn, player);

            } else if (slotId >= FIRST_INPUT_SLOT_INDEX && // INPUT INVENTORY
                    slotId < FIRST_OUTPUT_SLOT_INDEX) {
                return inputSlotClick(slotId, dragType, clickTypeIn, player);

            } else if (slotId >= FIRST_OUTPUT_SLOT_INDEX && // OUTPUT INVENTORY
                    slotId < FIRST_OUTPUT_SLOT_INDEX + OUTPUT_SLOTS_COUNT){
                return outputSlotClick(slotId, dragType, clickTypeIn, player);
            } else {
                // SlotID -1...not sure what this is yet
            }
            return ItemStack.EMPTY;
        } catch (Exception exception) {
            System.out.println("CRASH IN VENDING CONTAINER- slotid:" + slotId + " dragType:" + dragType + " clickType:" + clickTypeIn + " player:" + player);
            return ItemStack.EMPTY;
        }
    }

    private ItemStack stockSlotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        return ItemStack.EMPTY;
    }

    private ItemStack inputSlotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        return ItemStack.EMPTY;
    }

    private ItemStack outputSlotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        return ItemStack.EMPTY;
    }

    // Shift clicking
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }

    // Try to merge from given source stack into slot
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return super.canMergeSlot(stack, slotIn);
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

    public class OutputSlot extends Slot {

        public OutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }

    public int getVendingStateData(int index){
        return vendingStateData.get(index);
    }

    public void setVendingStateData(int index, int value){
        this.vendingStateData.set(index, value);
    }

    private enum SlotZones {
        STOCK_ZONE(FIRST_STOCK_SLOT_INDEX, STOCK_SLOT_COUNT),
        INPUT_ZONE(FIRST_INPUT_SLOT_INDEX, INPUT_SLOTS_COUNT),
        OUTPUT_ZONE(FIRST_OUTPUT_SLOT_INDEX, OUTPUT_SLOTS_COUNT),
        PLAYER_MAIN(PLAYER_INVENTORY_FIRST_SLOT_INDEX, PLAYER_INVENTORY_SLOT_COUNT),
        PLAYER_HOT(HOTBAR_FIRST_SLOT_INDEX, HOTBAR_SLOT_COUNT);

        public final int firstIndex;
        public final int slotCount;
        public final int lastIndexPlus1;

        SlotZones(int startIndex, int slotsCount) {
            this.firstIndex = startIndex;
            this.slotCount = slotsCount;
            this.lastIndexPlus1 = firstIndex + slotsCount;
        }

        public static SlotZones getZoneFromIndex(int slotIndex){
            for (SlotZones slotZone : SlotZones.values()){
                if (slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1) return slotZone;
            }
            throw new IndexOutOfBoundsException("Unexpected slotIndex");
        }
    }
}
