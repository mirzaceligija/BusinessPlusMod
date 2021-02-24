package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.items.CurrencyItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BusinessPlusMod.MOD_ID);
	
	/* ITEMS */
	//Coins
	
	public static final RegistryObject<CurrencyItem> COIN1_ITEM = ITEMS.register("coin1_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 0.01));
	
	public static final RegistryObject<CurrencyItem> COIN5_ITEM = ITEMS.register("coin5_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 0.05));
	
	public static final RegistryObject<CurrencyItem> COIN10_ITEM = ITEMS.register("coin10_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 0.10));
	
	public static final RegistryObject<CurrencyItem> COIN25_ITEM = ITEMS.register("coin25_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 0.25));
	
	public static final RegistryObject<CurrencyItem> COIN50_ITEM = ITEMS.register("coin50_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 0.50));
	
	
	//Paper money
	
	public static final RegistryObject<CurrencyItem> DOLLAR1_ITEM = ITEMS.register("dollar1_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR5_ITEM = ITEMS.register("dollar5_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR10_ITEM = ITEMS.register("dollar10_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR20_ITEM = ITEMS.register("dollar20_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 20.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR50_ITEM = ITEMS.register("dollar50_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 50.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR100_ITEM = ITEMS.register("dollar100_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 100.00));
	
	/*BLOCK ITEMS*/
	public static final RegistryObject<BlockItem> DEF_BLOCK = ITEMS.register("def_block", () ->
		new BlockItem(BlockInit.DEF_BLOCK.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	public static final RegistryObject<BlockItem> VAULT_CHEST_BLOCK = ITEMS.register("vault_chest", () ->
	new BlockItem(BlockInit.VAULT_CHEST.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
}
