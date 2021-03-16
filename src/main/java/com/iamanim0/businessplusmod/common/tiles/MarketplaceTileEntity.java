package com.iamanim0.businessplusmod.common.tiles;

import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.common.capability.provider.MarketplaceStateData;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceStockContents;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer;
import com.iamanim0.businessplusmod.common.containers.VaultChestContainer;
import com.iamanim0.businessplusmod.core.init.ItemInit;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MarketplaceTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity{
	
	private static final String CONTENTS_INVENTORY_TAG = "contents";

    public static final int STOCK_ROW_COUNT = 4;
    public static final int STOCK_COLUMN_COUNT = 4;
    public static final int STOCK_SLOT_COUNT = STOCK_ROW_COUNT * STOCK_COLUMN_COUNT;
    public static final int INPUT_SLOTS_COUNT = 1;
    public static final int OUTPUT_SLOTS_COUNT = 3;
    public static final int TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    private MarketplaceStockContents stockContents;
    private MarketplaceContents inputContents;
    private MarketplaceContents outputContents;
    
    private final MarketplaceStateData marketplaceStateData= new MarketplaceStateData();
    
	public MarketplaceTileEntity() {
		super(TileEntityTypeInit.MARKETPLACE.get());
		stockContents = new MarketplaceStockContents(STOCK_SLOT_COUNT, this::canPlayerUse, this::markDirty);
        inputContents = new MarketplaceContents(INPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
        outputContents = new MarketplaceContents(OUTPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
        
        inputContents.setInventorySlotContents(0, new ItemStack(ItemInit.DOLLAR_1.get(), 1));
        
        outputContents.setInventorySlotContents(0, new ItemStack(ItemInit.DOLLAR_100.get(), 1));
        outputContents.setInventorySlotContents(1, new ItemStack(ItemInit.DOLLAR_5.get(), 1));
        outputContents.setInventorySlotContents(2, new ItemStack(ItemInit.CENT_20.get(), 1));
        
        stockContents.setInventorySlotContents(0, new ItemStack(Items.CHARCOAL.getItem(), 1));
        stockContents.setInventorySlotContents(1, new ItemStack(Items.COAL.getItem(), 1));
        stockContents.setInventorySlotContents(2, new ItemStack(Items.IRON_INGOT.getItem(), 1));
        stockContents.setInventorySlotContents(3, new ItemStack(Items.GOLD_INGOT.getItem(), 1));
        stockContents.setInventorySlotContents(4, new ItemStack(Items.DIAMOND.getItem(), 1));
        stockContents.setInventorySlotContents(5, new ItemStack(Items.EMERALD.getItem(), 1));
	}

	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		return new MarketplaceContainer(windowID, playerInventory, stockContents, inputContents, outputContents, marketplaceStateData);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
        if(world.isRemote){
          // System.out.println("CLIENT: " + marketplaceStateData.get(MarketplaceStateData.MODE_INDEX));
        } else {
         //   System.out.println("SERVER: " + vendingStateData.get(VendingStateData.MODE_INDEX));
        }
	}
	
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    public void dropAllContents(World world, BlockPos blockPos){
        InventoryHelper.dropInventoryItems(world, blockPos, stockContents);
        InventoryHelper.dropInventoryItems(world, blockPos, inputContents);
        InventoryHelper.dropInventoryItems(world, blockPos, outputContents);
    }

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("container.marketplace");
	}
	
    public int getVendingStateData(int index){
        return marketplaceStateData.get(index);
    }

    public void setVendingStateData(int index, int value){
        this.marketplaceStateData.set(index, value);
    }
	
	// ---- NBT Stuff ----
    private final String STOCK_SLOTS_NBT = "stockSlots";
    private final String INPUT_SLOTS_NBT = "inputSlots";
    private final String OUTPUT_SLOTS_NBT = "outputSlots";
    
    @Override
    public CompoundNBT write(CompoundNBT compound) {
    	// TODO Auto-generated method stub
    	super.write(compound);
        compound.put(STOCK_SLOTS_NBT, stockContents.serializeNBT());
        compound.put(INPUT_SLOTS_NBT, inputContents.serializeNBT());
        compound.put(OUTPUT_SLOTS_NBT, outputContents.serializeNBT());
        marketplaceStateData.putIntoNBT(compound);
        return compound;
    }
    
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
    	// TODO Auto-generated method stub
    	super.read(state, nbt);
        CompoundNBT stockNBT = nbt.getCompound(STOCK_SLOTS_NBT);
        stockContents.deserializeNBT(stockNBT);

        CompoundNBT inputNBT = nbt.getCompound(INPUT_SLOTS_NBT);
        inputContents.deserializeNBT(inputNBT);

        CompoundNBT outputNBT = nbt.getCompound(OUTPUT_SLOTS_NBT);
        outputContents.deserializeNBT(outputNBT);

        marketplaceStateData.readFromNBT(nbt);

        if (stockContents.getSizeInventory() != STOCK_SLOT_COUNT || inputContents.getSizeInventory() != INPUT_SLOTS_COUNT
                || outputContents.getSizeInventory() != OUTPUT_SLOTS_COUNT)
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
    }
    
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
    	// TODO Auto-generated method stub
    	CompoundNBT updateTag = getUpdateTag();
        return new SUpdateTileEntityPacket(this.pos, 42, updateTag); //Type in # is arbitrary
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    	// TODO Auto-generated method stub
    	CompoundNBT updateTag = pkt.getNbtCompound();
        BlockState blockState = world.getBlockState(pos);
        handleUpdateTag(blockState, updateTag);
    }
    
    @Override
    public CompoundNBT getUpdateTag() {
    	// TODO Auto-generated method stub
    	CompoundNBT compoundNBT = new CompoundNBT();
        write(compoundNBT);
        return compoundNBT;
    }
    
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
    	// TODO Auto-generated method stub
    	read(state, tag);
    }
}
