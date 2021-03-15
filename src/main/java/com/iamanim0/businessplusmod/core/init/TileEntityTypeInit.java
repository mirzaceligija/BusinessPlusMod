package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.tiles.FirstBlockTile;
import com.iamanim0.businessplusmod.common.tiles.MarketplaceTileEntity;
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
	
	public static final RegistryObject<TileEntityType<FirstBlockTile>> FIRSTBLOCK = TILE_ENTITY_TYPES.register("firstblock", 
			() -> TileEntityType.Builder.create(FirstBlockTile::new, BlockInit.FIRSTBLOCK.get()).build(null));
	
	public static final RegistryObject<TileEntityType<AtmTileEntity>> ATM = TILE_ENTITY_TYPES.register("atm", 
			() -> TileEntityType.Builder.create(AtmTileEntity::new, BlockInit.ATM_BLOCK.get()).build(null));
	
	public static final RegistryObject<TileEntityType<MarketplaceTileEntity>> MARKETPLACE = TILE_ENTITY_TYPES.register("marketplace", 
			() -> TileEntityType.Builder.create(MarketplaceTileEntity::new, BlockInit.ATM_BLOCK.get()).build(null));
	
}
