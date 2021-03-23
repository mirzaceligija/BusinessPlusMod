package com.iamanim0.businessplusmod.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class PriceList {
	
	public static int getPriceForItemMINER(ItemStack stack, int sellingRate) {
		if(stack.getItem().getItem() == Items.CHARCOAL.getItem()) {
			return (int) (1.69 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.COAL.getItem()) {
			return (int) (1.50 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.IRON_INGOT.getItem()) {
			return (int) (15.19 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.GOLD_INGOT.getItem()) {
			return (int) (60.19 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.DIAMOND.getItem()) {
			return (int) (125.00 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.NETHERITE_INGOT.getItem()) {
			return (int) (225.00 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.LAPIS_LAZULI.getItem()) {
			return (int) (1.95 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.EMERALD.getItem()) {
			return (int) (65.00 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.QUARTZ.getItem()) {
			return (int) (1.55 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.REDSTONE.getItem()) {
			return (int) (0.65 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.GLOWSTONE_DUST.getItem()) {
			return (int) (1.55 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.COAL_ORE.getItem()) {
			return (int) (15.74 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.IRON_ORE.getItem()) {
			return (int) (15.00 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.GOLD_ORE.getItem()) {
			return (int) (60.00 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.DIAMOND_ORE.getItem()) {
			return (int) (113.70 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.LAPIS_ORE.getItem()) {
			return (int) (23.74 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.EMERALD_ORE.getItem()) {
			return (int) (93.70 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.NETHER_QUARTZ_ORE.getItem()) {
			return (int) (14.34 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.REDSTONE_ORE.getItem()) {
			return (int) (63.70 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.COAL_BLOCK.getItem()) {
			return (int) (13.60 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.IRON_BLOCK.getItem()) {
			return (int) (136.79 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.GOLD_BLOCK.getItem()) {
			return (int) (541.79 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.DIAMOND_BLOCK.getItem()) {
			return (int) (1125.10 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.LAPIS_BLOCK.getItem()) {
			return (int) (17.65 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.EMERALD_BLOCK.getItem()) {
			return (int) (585.10 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.QUARTZ_BLOCK.getItem()) {
			return (int) (6.30 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.REDSTONE_BLOCK.getItem()) {
			return (int) (5.85 * sellingRate/ 100) * 100;
		} else if(stack.getItem().getItem() == Items.GLOWSTONE.getItem()) {
			return (int) (6.30 * sellingRate/ 100) * 100;
		} else { return 0; }
	}

	public static int getPriceForItemFARMER(ItemStack stack, int sellingRate) {
		// TODO Auto-generated method stub
		if(stack.getItem().getItem() == Items.WHEAT.getItem()) {
			return (int) (100 * sellingRate/100)*100;
		} else if(stack.getItem().getItem() == Items.WHEAT_SEEDS.getItem()) {
			return (int) (100 * sellingRate/100)*100;
		} else return 0;
	}
}
