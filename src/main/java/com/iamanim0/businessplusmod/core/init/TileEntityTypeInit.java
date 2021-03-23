package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.tiles.MarketTileEntity;
import com.iamanim0.businessplusmod.common.tiles.TradeInOreTileEntity;
import com.iamanim0.businessplusmod.common.tiles.TradeInTileEntity;
import com.iamanim0.businessplusmod.common.tiles.AtmTileEntity;
import com.iamanim0.businessplusmod.common.tiles.VaultChestTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BusinessPlusMod.MOD_ID);
	
	public static final RegistryObject<TileEntityType<VaultChestTileEntity>> VAULT_CHEST = TILE_ENTITY_TYPES.register("vault_chest", 
			() -> TileEntityType.Builder.create(VaultChestTileEntity::new, BlockInit.VAULT_CHEST.get()).build(null));
	
	public static final RegistryObject<TileEntityType<AtmTileEntity>> ATM = TILE_ENTITY_TYPES.register("atm", 
			() -> TileEntityType.Builder.create(AtmTileEntity::new, BlockInit.ATM_BLOCK.get()).build(null));
	
	public static final RegistryObject<TileEntityType<MarketTileEntity>> MARKET = TILE_ENTITY_TYPES.register("market", 
			() -> TileEntityType.Builder.create(MarketTileEntity::new, BlockInit.MARKET.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInOreTileEntity>> TRADEIN_ORE = TILE_ENTITY_TYPES.register("tradein_ore", 
			() -> TileEntityType.Builder.create(TradeInOreTileEntity::new, BlockInit.TRADEIN_ORE.get()).build(null));
	
	//TRADEIN
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_MINER_40 = TILE_ENTITY_TYPES.register("tradein_miner_40", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_MINER_40.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_MINER_100 = TILE_ENTITY_TYPES.register("tradein_miner_100", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_MINER_100.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_FARMER_40 = TILE_ENTITY_TYPES.register("tradein_farmer_40", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_FARMER_40.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_FARMER_100 = TILE_ENTITY_TYPES.register("tradein_farmer_100", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_FARMER_100.get()).build(null));
}
