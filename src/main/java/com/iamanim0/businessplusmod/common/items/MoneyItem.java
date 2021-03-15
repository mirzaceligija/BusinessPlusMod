package com.iamanim0.businessplusmod.common.items;

import com.iamanim0.businessplusmod.core.init.ItemInit;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class MoneyItem extends Item {

	private final int value;

    private static final int[] COIN_VALUES = {
    		100000000, 5000000, 1000000, 50000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1
    };

    public MoneyItem(Properties properties, int value) {
        super(properties);
        this.value = value;
    }

    public static int getMaxCoinValue(int value) {
        for (int coin : COIN_VALUES) {
            if (value / coin > 0)
                return coin;
        }
        return 0;
    }

    public static Item getCoinByValue(int value) {
        switch (value) {
            case 1:
                return ItemInit.CENT_1.get();
            case 2:
                return ItemInit.CENT_2.get();
            case 5:
                return ItemInit.CENT_5.get();
            case 10:
                return ItemInit.CENT_10.get();
            case 20:
                return ItemInit.CENT_20.get();
            case 50:
                return ItemInit.CENT_50.get();
            case 100:
                return ItemInit.DOLLAR_1.get();
            case 200:
                return ItemInit.DOLLAR_2.get();
            case 500:
                return ItemInit.DOLLAR_5.get();
            case 1000:
                return ItemInit.DOLLAR_10.get();
            case 2000:
                return ItemInit.DOLLAR_20.get();
            case 5000:
                return ItemInit.DOLLAR_50.get();
            case 10000:
                return ItemInit.DOLLAR_100.get();
            case 50000:
                return ItemInit.DOLLAR_500.get();
            case 1000000:
                return ItemInit.DOLLAR_10000.get();
            case 5000000:
                return ItemInit.DOLLAR_50000.get();
            case 100000000:
                return ItemInit.DOLLAR_1000000.get();
        }
        return Items.AIR;
    }

    public static int getCoinValue(Item item) {
        if (item instanceof MoneyItem)
            return ((MoneyItem) item).value;
        return 0;
    }
}
