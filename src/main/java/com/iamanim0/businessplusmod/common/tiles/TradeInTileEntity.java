package com.iamanim0.businessplusmod.common.tiles;

import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.capability.provider.TradeInStateData;
import com.iamanim0.businessplusmod.common.capability.storage.TradeInContents;
import com.iamanim0.businessplusmod.common.capability.storage.TradeInStockContents;
import com.iamanim0.businessplusmod.common.containers.TradeInContainer;
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
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TradeInTileEntity  extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	private static final ResourceLocation MINER_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "tradeinoresitem");
	private static final ResourceLocation FARMER_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "farmeritem");
	
	/*
   private static final String[] CATEGORIES = {
    		"miner", "farmer"
    };
    */
	
	private final int SELLING_RATE;
	private final String CATEGORY;

	public static final int STOCK_ROW_COUNT = 7;
    public static final int STOCK_COLUMN_COUNT = 12;
    public static final int STOCK_SLOT_COUNT = STOCK_ROW_COUNT * STOCK_COLUMN_COUNT;
    public static final int INPUT_SLOTS_COUNT = 1;
    public static final int OUTPUT_SLOTS_COUNT = 36;
    public static final int TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;
    
    private TradeInStockContents stockContents;
    private TradeInContents inputContents;
    private TradeInContents outputContents;
    
    public int currentMoney;
    private final TradeInStateData tradeInStateData= new TradeInStateData();
    
	public TradeInTileEntity() {
		// TODO Auto-generated constructor stub
		super(TileEntityTypeInit.TRADEIN_MINER_100.get());
		this.SELLING_RATE = 0;
		this.CATEGORY = "invalid";
	}
	
	
	public TradeInTileEntity(TileEntityType<?> tileEntityTypeIn, int sr, String cat) {
		// TODO Auto-generated constructor stub
		super(tileEntityTypeIn);
		this.CATEGORY = cat;
		this.SELLING_RATE = sr;
		
		stockContents = new TradeInStockContents(STOCK_SLOT_COUNT, this::canPlayerUse, this::markDirty);
        inputContents = new TradeInContents(INPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
        outputContents = new TradeInContents(OUTPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("container.tradein");
	}

	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity player) {
		// TODO Auto-generated method stub
		return new TradeInContainer(windowID, playerInventory, stockContents, inputContents, outputContents, this, tradeInStateData);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if(world.isRemote){
	    	return;
	    } else {
	    	if(this.CATEGORY == "miner" && this.inputContents.getStackInSlot(0).getItem().isIn(ItemTags.getCollection().get(MINER_TAG))) {
	    		this.inputContents.inputItemIn(this.inputContents.getStackInSlot(0), this.SELLING_RATE);
	    		this.currentMoney = this.inputContents.getMoneyCount();
	    		System.out.println("TRENUTNO MONEY IN TILE ENTITY --> " + this.currentMoney);
	    	} else if (this.CATEGORY == "farmer" && this.inputContents.getStackInSlot(0).getItem().isIn(ItemTags.getCollection().get(FARMER_TAG))) {
	    		System.out.println("TICK REACTION");
	    		this.inputContents.inputItemIn(this.inputContents.getStackInSlot(0), this.SELLING_RATE);
	    		this.currentMoney = this.inputContents.getMoneyCount();
	    		System.out.println("TRENUTNO MONEY IN TILE ENTITY --> " + this.currentMoney);
	    	}
	    }
	}
	
	public String getCategory() {
		return this.CATEGORY;
	}
	
	public void generateStockContents() {	
		if(this.CATEGORY == "miner")
			generateStockContentsMINER();
		if(this.CATEGORY == "farmer")
			generateStockContentsFARMER();
    }
	
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }
    
	public int getVendingStateData(int index){
        return tradeInStateData.get(index);
    }

    public void setVendingStateData(int index, int value){
        this.tradeInStateData.set(index, value);
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
        tradeInStateData.putIntoNBT(compound);
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

        tradeInStateData.readFromNBT(nbt);

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
	
	// == GENERATE SLOTS BY CATEGORY == //
	
	public void generateStockContentsMINER() {	
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
        
        for(int i=0; i < stockContents.getSizeInventory(); i++) {
    		stockContents.getStackInSlot(i).setDisplayName(new StringTextComponent( stockContents.getStackInSlot(i).getDisplayName().getString() +" - "+ (PriceList.getPriceForItemMINER(stockContents.getStackInSlot(i), this.SELLING_RATE)/100.00) + "$"));
    	}
    }
	
	public void generateStockContentsFARMER() {	
		stockContents.setInventorySlotContents(0, new ItemStack(Items.CHICKEN.getItem(), 1));
        stockContents.setInventorySlotContents(1, new ItemStack(Items.COOKED_CHICKEN.getItem(), 1));
        stockContents.setInventorySlotContents(2, new ItemStack(Items.PORKCHOP.getItem(), 1));
        stockContents.setInventorySlotContents(3, new ItemStack(Items.COOKED_PORKCHOP.getItem(), 1));
        stockContents.setInventorySlotContents(4, new ItemStack(Items.BEEF.getItem(), 1));
        stockContents.setInventorySlotContents(5, new ItemStack(Items.COOKED_BEEF.getItem(), 1));
        stockContents.setInventorySlotContents(6, new ItemStack(Items.COD.getItem(), 1));
        stockContents.setInventorySlotContents(7, new ItemStack(Items.SALMON.getItem(), 1));
        stockContents.setInventorySlotContents(8, new ItemStack(Items.TROPICAL_FISH.getItem(), 1));
        stockContents.setInventorySlotContents(9, new ItemStack(Items.PUFFERFISH.getItem(), 1));
        stockContents.setInventorySlotContents(10, new ItemStack(Items.COOKED_COD.getItem(), 1));
        stockContents.setInventorySlotContents(11, new ItemStack(Items.COOKED_SALMON.getItem(), 1));
        stockContents.setInventorySlotContents(12, new ItemStack(Items.POTATO.getItem(), 1));
        stockContents.setInventorySlotContents(13, new ItemStack(Items.BAKED_POTATO.getItem(), 1));
        stockContents.setInventorySlotContents(14, new ItemStack(Items.POISONOUS_POTATO.getItem(), 1));
        stockContents.setInventorySlotContents(15, new ItemStack(Items.CARROT.getItem(), 1));
        stockContents.setInventorySlotContents(16, new ItemStack(Items.GOLDEN_CARROT.getItem(), 1));
        stockContents.setInventorySlotContents(17, new ItemStack(Items.APPLE.getItem(), 1));
        stockContents.setInventorySlotContents(18, new ItemStack(Items.GOLDEN_APPLE.getItem(), 1));
        stockContents.setInventorySlotContents(19, new ItemStack(Items.MELON_SLICE.getItem(), 1));
        stockContents.setInventorySlotContents(20, new ItemStack(Items.MELON.getItem(), 1));
        stockContents.setInventorySlotContents(21, new ItemStack(Items.PUMPKIN.getItem(), 1));
        stockContents.setInventorySlotContents(22, new ItemStack(Items.BROWN_MUSHROOM.getItem(), 1));
        stockContents.setInventorySlotContents(23, new ItemStack(Items.RED_MUSHROOM.getItem(), 1));
        stockContents.setInventorySlotContents(24, new ItemStack(Items.MUSHROOM_STEW.getItem(), 1));
        stockContents.setInventorySlotContents(25, new ItemStack(Items.BREAD.getItem(), 1));
        stockContents.setInventorySlotContents(26, new ItemStack(Items.COOKIE.getItem(), 1));
        stockContents.setInventorySlotContents(27, new ItemStack(Items.PUMPKIN_PIE.getItem(), 1));
        stockContents.setInventorySlotContents(28, new ItemStack(Items.CAKE.getItem(), 1));
        stockContents.setInventorySlotContents(29, new ItemStack(Items.WHEAT_SEEDS.getItem(), 1));
        stockContents.setInventorySlotContents(30, new ItemStack(Items.PUMPKIN_SEEDS.getItem(), 1));
        stockContents.setInventorySlotContents(31, new ItemStack(Items.MELON_SEEDS.getItem(), 1));
        stockContents.setInventorySlotContents(32, new ItemStack(Items.BEETROOT_SEEDS.getItem(), 1));
        stockContents.setInventorySlotContents(33, new ItemStack(Items.COCOA_BEANS.getItem(), 1));
        stockContents.setInventorySlotContents(34, new ItemStack(Items.WHEAT.getItem(), 1));
        stockContents.setInventorySlotContents(35, new ItemStack(Items.SUGAR_CANE.getItem(), 1));
        stockContents.setInventorySlotContents(36, new ItemStack(Items.SUGAR.getItem(), 1));
        stockContents.setInventorySlotContents(37, new ItemStack(Items.EGG.getItem(), 1));
        stockContents.setInventorySlotContents(38, new ItemStack(Items.MILK_BUCKET.getItem(), 1));
        stockContents.setInventorySlotContents(39, new ItemStack(Items.HAY_BLOCK.getItem(), 1));
        stockContents.setInventorySlotContents(40, new ItemStack(Items.GLISTERING_MELON_SLICE.getItem(), 1));
        stockContents.setInventorySlotContents(41, new ItemStack(Items.RABBIT.getItem(), 1));
        stockContents.setInventorySlotContents(42, new ItemStack(Items.COOKED_RABBIT.getItem(), 1));
        stockContents.setInventorySlotContents(43, new ItemStack(Items.RABBIT_STEW.getItem(), 1));
        stockContents.setInventorySlotContents(44, new ItemStack(Items.BEETROOT_SOUP.getItem(), 1));
        stockContents.setInventorySlotContents(45, new ItemStack(Items.SWEET_BERRIES.getItem(), 1));
        stockContents.setInventorySlotContents(46, new ItemStack(Items.HONEY_BOTTLE.getItem(), 1));
        
        for(int i=0; i < stockContents.getSizeInventory(); i++) {
        	stockContents.getStackInSlot(i).setDisplayName(new StringTextComponent(stockContents.getStackInSlot(i).getDisplayName().getString() +" - "+ (PriceList.getPriceForItemFARMER(stockContents.getStackInSlot(i), this.SELLING_RATE)/100.00) + "$"));
        }
    }
}
