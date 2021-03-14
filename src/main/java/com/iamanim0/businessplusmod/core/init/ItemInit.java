package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.items.BusinessLicenceItem;
import com.iamanim0.businessplusmod.common.items.CreditCardItem;
import com.iamanim0.businessplusmod.common.items.CurrencyItem;
import com.iamanim0.businessplusmod.common.items.MoneyItem;
import com.iamanim0.businessplusmod.common.items.WalletItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BusinessPlusMod.MOD_ID);
	
	/* ITEMS */
	//Wallet
	
	public static final RegistryObject<WalletItem> WALLET_ITEM = ITEMS.register("wallet_item", 
			() -> new WalletItem(new Item.Properties().maxStackSize(1).group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
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
	
	public static final RegistryObject<CurrencyItem> DOLLAR500_ITEM = ITEMS.register("dollar500_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 500.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR10000_ITEM = ITEMS.register("dollar10000_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10000.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR50000_ITEM = ITEMS.register("dollar50000_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10000.00));
	
	public static final RegistryObject<CurrencyItem> DOLLAR1000000_ITEM = ITEMS.register("dollar1000000_item", 
			() -> new CurrencyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1000000.00));
	
	//Licence
	public static final RegistryObject<BusinessLicenceItem> LICENCE_ITEM = ITEMS.register("licence_item", 
			() -> new BusinessLicenceItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	/*BLOCK ITEMS*/
	public static final RegistryObject<BlockItem> VAULT_CHEST_BLOCK = ITEMS.register("vault_chest", () ->
	new BlockItem(BlockInit.VAULT_CHEST.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	public static final RegistryObject<BlockItem> MONEY_PILE_BLOCK = ITEMS.register("money_pile", () ->
	new BlockItem(BlockInit.MONEY_PILE.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	public static final RegistryObject<BlockItem> FIRST_BLOCK = ITEMS.register("firstblock", () ->
	new BlockItem(BlockInit.FIRSTBLOCK.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	

	
	
	/*CUSTOM*/
	public static final RegistryObject<CreditCardItem> CREDIT_CARD_ITEM = ITEMS.register("credit_card_item", 
			() -> new CreditCardItem(new Item.Properties().maxStackSize(1).group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	public static final RegistryObject<BlockItem> ATM = ITEMS.register("atm", () ->
	new BlockItem(BlockInit.ATM_BLOCK.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
			
	
	public static final RegistryObject<Item> CENT_1 = ITEMS.register("cent1", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1));
    public static final RegistryObject<Item> CENT_2 = ITEMS.register("cent2", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 2));
    public static final RegistryObject<Item> CENT_5 = ITEMS.register("cent5", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5));
    public static final RegistryObject<Item> CENT_10 = ITEMS.register("cent10", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10));
    public static final RegistryObject<Item> CENT_20 = ITEMS.register("cent20", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 20));
    public static final RegistryObject<Item> CENT_50 = ITEMS.register("cent50", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 50));
    public static final RegistryObject<Item> EURO_1 = ITEMS.register("euro1", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 100));
    public static final RegistryObject<Item> EURO_2 = ITEMS.register("euro2", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 200));
    public static final RegistryObject<Item> EURO_5 = ITEMS.register("euro5", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 500));
    public static final RegistryObject<Item> EURO_10 = ITEMS.register("euro10", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1000));
    public static final RegistryObject<Item> EURO_20 = ITEMS.register("euro20", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 2000));
    public static final RegistryObject<Item> EURO_50 = ITEMS.register("euro50", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5000));
    public static final RegistryObject<Item> EURO_100 = ITEMS.register("euro100", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10000));
    public static final RegistryObject<Item> EURO_500 = ITEMS.register("euro500", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 50000));
    public static final RegistryObject<Item> EURO_10000 = ITEMS.register("euro10000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1000000));
    public static final RegistryObject<Item> EURO_50000 = ITEMS.register("euro50000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5000000));
    public static final RegistryObject<Item> EURO_1000000 = ITEMS.register("euro1000000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 100000000));

}
