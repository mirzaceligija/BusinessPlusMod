package com.iamanim0.businessplusmod.common.capability.storage;

import java.util.function.Predicate;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.capability.storage.TradeInContents.Notify;
import com.iamanim0.businessplusmod.core.util.PriceList;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemStackHandler;

public class TradeInContents implements IInventory {

	private final ItemStackHandler tradeInComponentContents;
    private Predicate<PlayerEntity> canPlayerAccess = x-> true;
    private Notify markDirtyNotification = ()->{};
    private final Notify openInventoryNotificationLambda = ()->{};
    private final Notify closeInventoryNotificationLambda = ()->{};
    
	private static final ResourceLocation MINER_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "tradeinoresitem");
	private static final ResourceLocation FARMER_TAG = new ResourceLocation(BusinessPlusMod.MOD_ID, "farmeritem");
    
    private int moneyIn = 0;

    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

    // Server side initialization
    public TradeInContents(int size, Predicate<PlayerEntity> canPlayerAccess, Notify notify) {
        this.tradeInComponentContents = new ItemStackHandler(size);
        this.canPlayerAccess = canPlayerAccess;
        this.markDirtyNotification = notify;
    }

    // Client side initialization
    public TradeInContents(int size) {
        this.tradeInComponentContents = new ItemStackHandler(size);
    }
    
    // ----- Custom for container -----
    public int getFreeSlot() {
    	for(int i=0; i< getSizeInventory(); i++) {
    		if(getStackInSlot(i) == ItemStack.EMPTY)
    			return i;
    	}
    	return 99;
    }
    
    //Custom for container
    public int getItemStackIndex(ItemStack stack) {
    	for(int i=0; i< getSizeInventory(); i++) {
    		if(getStackInSlot(i).getItem() == stack.getItem()) {
    			return i;
    		}
    	}
    	return 99;
    }
    
    public int getMoneyCount() {
    	return this.moneyIn;
    }
    
    public void decreaseMoney(int price) {
    	this.moneyIn = this.getMoneyCount() - price;
    }
    
    public void inputMoney(ItemStack stack, int rate) {
    	removeStackFromSlot(0);
    	if(stack.getItem().isIn(ItemTags.getCollection().get(MINER_TAG))) {
    		System.out.println("CIJENA ZA ITEM -->" + PriceList.getPriceForItemMINER(stack, rate) * stack.getCount());
    		this.moneyIn = PriceList.getPriceForItemMINER(stack, rate) * stack.getCount();
    		System.out.println("MONEY IN-->" + this.moneyIn);
    	}
    	else if (stack.getItem().isIn(ItemTags.getCollection().get(FARMER_TAG))) {
    		this.moneyIn = PriceList.getPriceForItemFARMER(stack, rate) * stack.getCount();
    		System.out.println("CIJENA ZA ITEM -->" + PriceList.getPriceForItemFARMER(stack, rate) * stack.getCount());
    		System.out.println("MONEY IN-->" + this.moneyIn);
    	} else
    		this.moneyIn = 0;
    }

    // ----- Setters and Getters -----
    public void setPlayerAccess(Predicate<PlayerEntity> canPlayerAccess){
        this.canPlayerAccess = canPlayerAccess;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return canPlayerAccess.test(player); // Only does anything on server side
    }

    public void setMarkDirty(Notify mark){
        this.markDirtyNotification = mark;
    }

    @Override
    public void markDirty() {
        this.markDirtyNotification.invoke();
    }

    @Override
    public void openInventory(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }

    // ---- NBT STUFF -----
    public CompoundNBT serializeNBT() {
        return tradeInComponentContents.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt){
    	tradeInComponentContents.deserializeNBT(nbt);
    }

    // ---- Called by VANILLA to manipulate inventory contents
    @Override
    public int getSizeInventory() {
        return tradeInComponentContents.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < tradeInComponentContents.getSlots(); i++){
            if (! tradeInComponentContents.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return tradeInComponentContents.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (count < 0) throw new IllegalArgumentException("count should be >= 0:" + count);
        return tradeInComponentContents.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        int maxStackSize = tradeInComponentContents.getSlotLimit(index);
        return tradeInComponentContents.extractItem(index, maxStackSize, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
    	tradeInComponentContents.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for(int i = 0; i < tradeInComponentContents.getSlots(); i++){
        	tradeInComponentContents.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public ItemStack increaseStackSize(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = tradeInComponentContents.insertItem(index, itemStackToInsert, false);
        return leftoverItemStack;
    }

    public boolean doesItemStackFit(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = tradeInComponentContents.insertItem(index, itemStackToInsert, true);
        return leftoverItemStack.isEmpty();
    }
}
