package com.iamanim0.businessplusmod.common.tiles;

import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MarketplaceTileEntity extends TileEntity {

	public MarketplaceTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}
    
	public MarketplaceTileEntity() {
		this(TileEntityTypeInit.MARKETPLACE.get());
	}
}
