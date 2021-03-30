package com.iamanim0.businessplusmod.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class PriceList {
	
	public static int getPriceForItemMINER(ItemStack stack, int sellingRate) {
		if(stack.getItem().getItem() == Items.CHARCOAL.getItem()) {
			return (int) ((1.69 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COAL.getItem()) {
			return (int) ((1.50 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.IRON_INGOT.getItem()) {
			return (int) ((15.19 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GOLD_INGOT.getItem()) {
			return (int) ((60.19 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.DIAMOND.getItem()) {
			return (int) ((125.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.NETHERITE_INGOT.getItem()) {
			return (int) ((225.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.LAPIS_LAZULI.getItem()) {
			return (int) ((1.95 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.EMERALD.getItem()) {
			return (int) ((65.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.QUARTZ.getItem()) {
			return (int) ((1.55 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.REDSTONE.getItem()) {
			return (int) ((0.65 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GLOWSTONE_DUST.getItem()) {
			return (int) ((1.55 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COAL_ORE.getItem()) {
			return (int) ((15.74 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.IRON_ORE.getItem()) {
			return (int) ((15.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GOLD_ORE.getItem()) {
			return (int) ((60.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.DIAMOND_ORE.getItem()) {
			return (int) ((113.70 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.LAPIS_ORE.getItem()) {
			return (int) ((23.74 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.EMERALD_ORE.getItem()) {
			return (int) ((93.70 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.NETHER_QUARTZ_ORE.getItem()) {
			return (int) ((14.34 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.REDSTONE_ORE.getItem()) {
			return (int) ((63.70 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COAL_BLOCK.getItem()) {
			return (int) ((13.60 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.IRON_BLOCK.getItem()) {
			return (int) ((136.79 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GOLD_BLOCK.getItem()) {
			return (int) ((541.79 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.DIAMOND_BLOCK.getItem()) {
			return (int) ((1125.10 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.LAPIS_BLOCK.getItem()) {
			return (int) ((17.65 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.EMERALD_BLOCK.getItem()) {
			return (int) ((585.10 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.QUARTZ_BLOCK.getItem()) {
			return (int) ((6.30 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.REDSTONE_BLOCK.getItem()) {
			return (int) ((5.85 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GLOWSTONE.getItem()) {
			return (int) ((6.30 * sellingRate/ 100) * 100);
		} else { return 0; }
	}

	public static int getPriceForItemFARMER(ItemStack stack, int sellingRate) {
		// TODO Auto-generated method stub
		if(stack.getItem().getItem() == Items.CHICKEN.getItem()) {
			return (int) ((1.15 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_CHICKEN.getItem()) {
			return (int) ((1.34 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.PORKCHOP.getItem()) {
			return (int) ((1.25 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_PORKCHOP.getItem()) {
			return (int) ((1.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BEEF.getItem()) {
			return (int) ((1.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_BEEF.getItem()) {
			return (int) ((1.54 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COD.getItem()) {
			return (int) ((1.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.SALMON.getItem()) {
			return (int) ((1.45 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.TROPICAL_FISH.getItem()) {
			return (int) ((1.65 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.PUFFERFISH.getItem()) {
			return (int) ((1.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_COD.getItem()) {
			return (int) ((1.54 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_SALMON.getItem()) {
			return (int) ((1.64 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.POTATO.getItem()) {
			return (int) ((1.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BAKED_POTATO.getItem()) {
			return (int) ((1.54 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.POISONOUS_POTATO.getItem()) {
			return (int) ((0.45 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.CARROT.getItem()) {
			return (int) ((1.25 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GOLDEN_CARROT.getItem()) {
			return (int) ((54.94 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.APPLE.getItem()) {
			return (int) ((1.65 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GOLDEN_APPLE.getItem()) {
			return (int) ((55.34 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MELON_SLICE.getItem()) {
			return (int) ((0.25 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MELON.getItem()) {
			return (int) ((2.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.PUMPKIN.getItem()) {
			return (int) ((0.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BROWN_MUSHROOM.getItem()) {
			return (int) ((1.05 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.RED_MUSHROOM.getItem()) {
			return (int) ((0.75 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MUSHROOM_STEW.getItem()) {
			return (int) ((2.30 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BREAD.getItem()) {
			return (int) ((2.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.PUMPKIN_PIE.getItem()) {
			return (int) ((1.71 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.CAKE.getItem()) {
			return (int) ((9.12 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.WHEAT_SEEDS.getItem()) {
			return (int) ((0.15 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.PUMPKIN_SEEDS.getItem()) {
			return (int) ((0.11 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MELON_SEEDS.getItem()) {
			return (int) ((0.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BEETROOT_SEEDS.getItem()) {
			return (int) ((1.25 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COCOA_BEANS.getItem()) {
			return (int) ((1.25 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.WHEAT.getItem()) {
			return (int) ((0.75 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.SUGAR_CANE.getItem()) {
			return (int) ((0.16 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.SUGAR.getItem()) {
			return (int) ((0.26 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.EGG.getItem()) {
			return (int) ((1.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MILK_BUCKET.getItem()) {
			return (int) ((5.85 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.MILK_BUCKET.getItem()) {
			return (int) ((47.51 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.GLISTERING_MELON_SLICE.getItem()) {
			return (int) ((53.94 * sellingRate/ 100) * 100);
		}  else if(stack.getItem().getItem() == Items.COOKIE.getItem()) {
			return (int) ((0.36 * sellingRate/ 100) * 100);
		}  else if(stack.getItem().getItem() == Items.HAY_BLOCK.getItem()) {
			return (int) ((6.85 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.RABBIT.getItem()) {
			return (int) ((1.35 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.COOKED_RABBIT.getItem()) {
			return (int) ((1.54 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.RABBIT_STEW.getItem()) {
			return (int) ((5.88 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.SWEET_BERRIES.getItem()) {
			return (int) ((0.10 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.HONEY_BOTTLE.getItem()) {
			return (int) ((40.00 * sellingRate/ 100) * 100);
		} else if(stack.getItem().getItem() == Items.BEETROOT_SOUP.getItem()) {
			return (int) ((22.70 * sellingRate/ 100) * 100);
		} else { return 0; }
	}
}