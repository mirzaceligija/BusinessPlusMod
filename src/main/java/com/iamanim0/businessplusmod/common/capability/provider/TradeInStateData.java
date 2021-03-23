package com.iamanim0.businessplusmod.common.capability.provider;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class TradeInStateData implements IIntArray {

	private int mode;
    private int storedCash = 0;
    private int playerCash = 0;
    private int rate;
    private int category;

    public void putIntoNBT(CompoundNBT compoundNBT){
        compoundNBT.putInt("mode", mode);
        compoundNBT.putInt("storedCash", storedCash);
        compoundNBT.putInt("playerCash", playerCash);
        compoundNBT.putInt("rate", rate);
        compoundNBT.putInt("category", category);
    }

    public void readFromNBT(CompoundNBT compoundNBT){
        mode = compoundNBT.getInt("mode");
        storedCash = compoundNBT.getInt("storedCash");
        playerCash = compoundNBT.getInt("playerCash");
        rate = compoundNBT.getInt("rate");
        category = compoundNBT.getInt("category");
    }

    // Vanilla Stuff, NO TOUCHY
    public static final int MODE_INDEX = 0;
    public static final int STOREDCASH_INDEX = 1;
    public static final int PLAYERCASH_INDEX = 2;
    
    public static final int RATE_INDEX = 3;
    public static final int CATEGORY_INDEX = 4;
    
    public static final int END_OF_INDEX_PLUS_ONE = 4 + 1;

    @Override
    public int get(int index) {
        validateIndex(index);
        switch (index){
            case MODE_INDEX:
                return mode;
            case STOREDCASH_INDEX:
                return storedCash;
            case PLAYERCASH_INDEX:
                return playerCash;
            case RATE_INDEX:
                return rate;
            case CATEGORY_INDEX:
                return category;
            default:
                return -1;
        }
    }

    @Override
    public void set(int index, int value) {
    	System.out.println(index);
        validateIndex(index);
        switch (index){
            case MODE_INDEX:
                System.out.println(value);
                mode = value;
                break;
            case STOREDCASH_INDEX:
                storedCash = value;
                System.out.println(storedCash);
                break;
            case PLAYERCASH_INDEX:
                playerCash = value;
                break;
            case RATE_INDEX:
                rate = value;
                break;
            case CATEGORY_INDEX:
                category = value;
                break;
        }
    }

    @Override
    public int size() {
        return END_OF_INDEX_PLUS_ONE;
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds:"+index);
        }
    }
}