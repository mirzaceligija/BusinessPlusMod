package com.iamanim0.businessplusmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;


import javax.annotation.Nullable;

import com.iamanim0.businessplusmod.common.tiles.AtmTileEntity;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

public class AtmBlock  extends HorizontalBlock {

    public AtmBlock(Properties properties) {
        super(properties);
        super.setDefaultState(this.getDefaultState()
                .with(HORIZONTAL_FACING, Direction.NORTH)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    	return TileEntityTypeInit.ATM.get().create();
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof AtmTileEntity) {
                final ItemStackHandler inventory = ((AtmTileEntity) tileEntity).inventory;
                for (int slot = 0; slot < inventory.getSlots(); ++slot)
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(slot));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }
}
