package com.iamanim0.businessplusmod.common.tiles;

import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.common.capability.storage.MarketContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceContents;
import com.iamanim0.businessplusmod.common.capability.storage.MarketplaceStockContents;
import com.iamanim0.businessplusmod.common.containers.MarketContainer;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer;
import com.iamanim0.businessplusmod.core.init.BlockInit;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MarketTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	public static final int STOCK_ROW_COUNT = 4;
    public static final int STOCK_COLUMN_COUNT = 4;
    public static final int STOCK_SLOT_COUNT = STOCK_ROW_COUNT * STOCK_COLUMN_COUNT;
    public static final int INPUT_SLOTS_COUNT = 1;
    public static final int TOTAL_SLOTS_COUNT = STOCK_SLOT_COUNT + INPUT_SLOTS_COUNT;
	
	private MarketContents inputContents;
	private MarketplaceStockContents stockContents;

    public MarketTileEntity() {
        super(TileEntityTypeInit.MARKET.get());
        inputContents = new MarketContents(INPUT_SLOTS_COUNT, this::canPlayerUse, this::markDirty);
    }

	
	@Nullable
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		// TODO Auto-generated method stub
		return new MarketContainer(windowID, playerInventory, stockContents, inputContents);
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent(BlockInit.MARKET.get().getTranslationKey());
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }
}
