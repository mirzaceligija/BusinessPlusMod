package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.tiles.VaultChestTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeInit {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BusinessPlusMod.MOD_ID);
	
	public static final RegistryObject<TileEntityType<VaultChestTileEntity>> VAULT_CHEST = TILE_ENTITY_TYPES.register("vault_chest", 
			() -> TileEntityType.Builder.create(VaultChestTileEntity::new, BlockInit.VAULT_CHEST.get()).build(null));
}
