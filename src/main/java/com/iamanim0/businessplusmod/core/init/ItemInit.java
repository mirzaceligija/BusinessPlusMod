package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.items.BusinessLicenceItem;
import com.iamanim0.businessplusmod.common.items.CreditCardItem;
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
	
	public static final RegistryObject<WalletItem> WALLET_ITEM = ITEMS.register("wallet_item",  () -> new WalletItem(new Item.Properties().maxStackSize(1).group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<BusinessLicenceItem> LICENCE_ITEM = ITEMS.register("licence_item", () -> new BusinessLicenceItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<CreditCardItem> CREDIT_CARD_ITEM = ITEMS.register("credit_card_item", () -> new CreditCardItem(new Item.Properties().maxStackSize(1).group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	
	// Money		
	
	public static final RegistryObject<Item> CENT_1 = ITEMS.register("cent1", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1));
    public static final RegistryObject<Item> CENT_2 = ITEMS.register("cent2", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 2));
    public static final RegistryObject<Item> CENT_5 = ITEMS.register("cent5", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5));
    public static final RegistryObject<Item> CENT_10 = ITEMS.register("cent10", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10));
    public static final RegistryObject<Item> CENT_20 = ITEMS.register("cent20", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 20));
    public static final RegistryObject<Item> CENT_50 = ITEMS.register("cent50", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 50));
    public static final RegistryObject<Item> DOLLAR_1 = ITEMS.register("dollar1", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 100));
    public static final RegistryObject<Item> DOLLAR_2 = ITEMS.register("dollar2", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 200));
    public static final RegistryObject<Item> DOLLAR_5 = ITEMS.register("dollar5", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 500));
    public static final RegistryObject<Item> DOLLAR_10 = ITEMS.register("dollar10", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1000));
    public static final RegistryObject<Item> DOLLAR_20 = ITEMS.register("dollar20", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 2000));
    public static final RegistryObject<Item> DOLLAR_50 = ITEMS.register("dollar50", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5000));
    public static final RegistryObject<Item> DOLLAR_100 = ITEMS.register("dollar100", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 10000));
    public static final RegistryObject<Item> DOLLAR_500 = ITEMS.register("dollar500", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 50000));
    public static final RegistryObject<Item> DOLLAR_10000 = ITEMS.register("dollar10000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 1000000));
    public static final RegistryObject<Item> DOLLAR_50000 = ITEMS.register("dollar50000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 5000000));
    public static final RegistryObject<Item> DOLLAR_1000000 = ITEMS.register("dollar1000000", () -> new MoneyItem(new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP), 100000000));

    
	/*BLOCK ITEMS*/
    
	public static final RegistryObject<BlockItem> ATM = ITEMS.register("atm", () -> new BlockItem(BlockInit.ATM_BLOCK.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<BlockItem> VAULT_CHEST_BLOCK = ITEMS.register("vault_chest", () -> new BlockItem(BlockInit.VAULT_CHEST.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<BlockItem> MONEY_PILE_BLOCK = ITEMS.register("money_pile", () -> new BlockItem(BlockInit.MONEY_PILE.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<BlockItem> FIRST_BLOCK = ITEMS.register("firstblock", () -> new BlockItem(BlockInit.FIRSTBLOCK.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));
	public static final RegistryObject<BlockItem> MARKETPLACE = ITEMS.register("marketplace", () -> new BlockItem(BlockInit.MARKETPLACE.get(), new Item.Properties().group(BusinessPlusMod.BUSINESSPLUS_GROUP)));

}
