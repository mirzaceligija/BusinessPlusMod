package com.iamanim0.businessplusmod.common.tiles;

import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.capability.provider.MarketStateData;
import com.iamanim0.businessplusmod.common.capability.storage.MarketContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketStockContents;
import com.iamanim0.businessplusmod.common.containers.MarketContainer;
import com.iamanim0.businessplusmod.common.items.MoneyItem;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;
import com.iamanim0.businessplusmod.core.util.PriceList;

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
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MarketTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	private static final ResourceLocation MONEY_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "moneytagitem");
	
    public static final int STOCK_ROW_COUNT = 7;
    public static final int STOCK_COLUMN_COUNT = 12;
    public static final int STOCK_SLOT_COUNT = STOCK_ROW_COUNT * STOCK_COLUMN_COUNT;
    public static final int INPUT_SLOTS_COUNT = 1;
    public static final int OUTPUT_SLOTS_COUNT = 12;
    public static final int TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;
  
    private MarketStockContents stockContents;
    private MarketContents inputContents;
    private MarketContents outputContents;
    
    public int currentMoney;
    
    private final MarketStateData marketplaceStateData= new MarketStateData();
	
	public MarketTileEntity() {
		super(TileEntityTypeInit.MARKET.get());
		stockContents = new MarketStockContents(STOCK_SLOT_COUNT, this::canPlayerUse, this::markDirty);
        inputContents = new MarketContents(INPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
        outputContents = new MarketContents(OUTPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
	}

	public void generateStockContents() {	
		stockContents.setInventorySlotContents(0, new ItemStack(Items.CHARCOAL.getItem(), 1));
        stockContents.setInventorySlotContents(1, new ItemStack(Items.COAL.getItem(), 1));
        stockContents.setInventorySlotContents(2, new ItemStack(Items.IRON_INGOT.getItem(), 1));
        stockContents.setInventorySlotContents(3, new ItemStack(Items.GOLD_INGOT.getItem(), 1));
        stockContents.setInventorySlotContents(4, new ItemStack(Items.DIAMOND.getItem(), 1));
        stockContents.setInventorySlotContents(5, new ItemStack(Items.NETHERITE_INGOT.getItem(), 1));
        stockContents.setInventorySlotContents(6, new ItemStack(Items.LAPIS_LAZULI.getItem(), 1));
        stockContents.setInventorySlotContents(7, new ItemStack(Items.EMERALD.getItem(), 1));
        stockContents.setInventorySlotContents(8, new ItemStack(Items.QUARTZ.getItem(), 1));
        stockContents.setInventorySlotContents(9, new ItemStack(Items.REDSTONE.getItem(), 1));
        stockContents.setInventorySlotContents(10, new ItemStack(Items.GLOWSTONE_DUST.getItem(), 1));
        stockContents.setInventorySlotContents(11, new ItemStack(Items.COAL_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(12, new ItemStack(Items.IRON_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(13, new ItemStack(Items.GOLD_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(14, new ItemStack(Items.DIAMOND_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(15, new ItemStack(Items.LAPIS_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(16, new ItemStack(Items.EMERALD_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(17, new ItemStack(Items.NETHER_QUARTZ_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(18, new ItemStack(Items.REDSTONE_ORE.getItem(), 1));
        stockContents.setInventorySlotContents(19, new ItemStack(Items.COAL_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(20, new ItemStack(Items.IRON_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(21, new ItemStack(Items.GOLD_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(22, new ItemStack(Items.DIAMOND_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(23, new ItemStack(Items.LAPIS_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(24, new ItemStack(Items.EMERALD_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(25, new ItemStack(Items.QUARTZ_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(26, new ItemStack(Items.REDSTONE_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(27, new ItemStack(Items.GLOWSTONE.getItem(), 1));
        
        //for(int i=0; i < stockContents.getSizeInventory(); i++) {
        	//stockContents.getStackInSlot(i).setDisplayName(new StringTextComponent( stockContents.getStackInSlot(i).getDisplayName().getString() +" - "+ (PriceList.getPriceForItemORE(stockContents.getStackInSlot(i))/100.00) + "$"));
        //}
	}
	
	public ItemStack getStockContents(int index) {
		return this.stockContents.getStackInSlot(index-37).copy();
	}
	
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }
	
	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		return new MarketContainer(windowID, playerInventory, stockContents, inputContents, outputContents, this, marketplaceStateData);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("container.market");
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	    if(world.isRemote){
	    	return;
	    } else {
	    	if(this.inputContents.getStackInSlot(0).getItem().isIn(ItemTags.getCollection().get(MONEY_TAG))) {
	    		try {
	    			this.inputContents.inputMoney(this.inputContents.getStackInSlot(0));
	    			this.currentMoney = this.inputContents.getMoneyCount();
	    		} catch  (Exception exception) {
	    			System.out.println("NE MERE MEDA" + exception);
	    		}
		    	markDirty();
	    	}
	    }
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

	@SuppressWarnings("null")
	public void dropMoney(PlayerEntity playerIn) {
		double x = playerIn.getPosX();
		double y = playerIn.getPosY();
		double z = playerIn.getPosZ();

        while (this.currentMoney > 0) {
            int max = MoneyItem.getMaxCoinValue(this.currentMoney);
            ItemStack stack = new ItemStack(MoneyItem.getCoinByValue(max), this.currentMoney / max);
            this.currentMoney -= (this.currentMoney / max) * max;

            InventoryHelper.spawnItemStack(playerIn.world, x, y, z, stack);
            if (!stack.isEmpty()) {
            	this.currentMoney += MoneyItem.getCoinValue(stack.getItem()) * stack.getCount();
            }
        }
	}	
}
