package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.tiles.StoreTileEntity;
import com.iamanim0.businessplusmod.common.tiles.TradeInTileEntity;
import com.iamanim0.businessplusmod.common.tiles.AtmTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BusinessPlusMod.MOD_ID);
	
	public static final RegistryObject<TileEntityType<AtmTileEntity>> ATM = TILE_ENTITY_TYPES.register("atm", 
			() -> TileEntityType.Builder.create(AtmTileEntity::new, BlockInit.ATM_BLOCK.get()).build(null));
	
	// == STORES == //
	public static final RegistryObject<TileEntityType<StoreTileEntity>> STORE_MINER = TILE_ENTITY_TYPES.register("store_miner", 
			() -> TileEntityType.Builder.create(StoreTileEntity::new, BlockInit.STORE_MINER.get()).build(null));
	
	public static final RegistryObject<TileEntityType<StoreTileEntity>> STORE_FARMER = TILE_ENTITY_TYPES.register("store_farmer", 
			() -> TileEntityType.Builder.create(StoreTileEntity::new, BlockInit.STORE_FARMER.get()).build(null));
	
	// == TRADEIN == //
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_MINER_40 = TILE_ENTITY_TYPES.register("tradein_miner_40", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_MINER_40.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_MINER_100 = TILE_ENTITY_TYPES.register("tradein_miner_100", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_MINER_100.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_FARMER_40 = TILE_ENTITY_TYPES.register("tradein_farmer_40", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_FARMER_40.get()).build(null));
	
	public static final RegistryObject<TileEntityType<TradeInTileEntity>> TRADEIN_FARMER_100 = TILE_ENTITY_TYPES.register("tradein_farmer_100", 
			() -> TileEntityType.Builder.create(TradeInTileEntity::new, BlockInit.TRADEIN_FARMER_100.get()).build(null));
}
