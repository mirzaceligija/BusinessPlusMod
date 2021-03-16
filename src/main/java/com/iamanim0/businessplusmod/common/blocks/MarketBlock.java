package com.iamanim0.businessplusmod.common.blocks;

import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MarketBlock extends ContainerBlock {

	public MarketBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return TileEntityTypeInit.MARKET.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		// TODO Auto-generated method stub
		if (worldIn.isRemote) return ActionResultType.SUCCESS; // Client do nothing
		INamedContainerProvider namedContainerProvider = this.getContainer(state, worldIn, pos);
		if(namedContainerProvider != null) {
            if(! (player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
                NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer -> {
                })); // Can put extra data in packet Buffer
        }

        return ActionResultType.SUCCESS;
	}
}
