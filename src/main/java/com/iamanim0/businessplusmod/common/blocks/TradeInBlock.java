package com.iamanim0.businessplusmod.common.blocks;

import com.iamanim0.businessplusmod.common.tiles.TradeInTileEntity;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TradeInBlock extends ContainerBlock {
	
	private final int SELLING_RATE;
	private final String CATEGORY;
	
    private static final String[] CATEGORIES = {
    		"miner", "farmer"
    };
	
	private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(12, 0, 3, 13, 10, 13);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(4, 0, 3, 12, 10, 4);
	private static final VoxelShape PARTIAL3 = Block.makeCuboidShape(4, 0, 12, 12, 10, 13);
	private static final VoxelShape PARTIAL4 = Block.makeCuboidShape(3, 0, 3, 4, 10, 13);
	private static final VoxelShape PARTIAL5 = Block.makeCuboidShape(4, 0, 4, 12, 1, 12);
	
	
	private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2, PARTIAL3, PARTIAL4, PARTIAL5);
	private static VoxelShape EMPTY_SPACE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE, IBooleanFunction.ONLY_FIRST);
	
	public TradeInBlock(Properties builder, int sellingRate, String category) {
		super(builder);
		// TODO Auto-generated constructor stub'
		this.SELLING_RATE = sellingRate;
		this.CATEGORY = category;
	}
	
	//Not needed probably
	
	public int getSellingRate() {
		return this.SELLING_RATE;
	}
	
	public String getCategory() {
		return this.CATEGORY;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		if(this.CATEGORY == "miner" && this.SELLING_RATE == 100)
			return  new TradeInTileEntity(TileEntityTypeInit.TRADEIN_MINER_100.get(),this.SELLING_RATE, this.CATEGORY);
		else if(this.CATEGORY == "miner" && this.SELLING_RATE == 40)
			return  new TradeInTileEntity(TileEntityTypeInit.TRADEIN_MINER_40.get(),this.SELLING_RATE, this.CATEGORY);
		else if(this.CATEGORY == "farmer" && this.SELLING_RATE == 100)
			return  new TradeInTileEntity(TileEntityTypeInit.TRADEIN_FARMER_100.get(),this.SELLING_RATE, this.CATEGORY);
		else if(this.CATEGORY == "farmer" && this.SELLING_RATE == 40)
			return  new TradeInTileEntity(TileEntityTypeInit.TRADEIN_FARMER_40.get(),this.SELLING_RATE, this.CATEGORY);
		else
			return  new TradeInTileEntity(TileEntityTypeInit.TRADEIN_MINER_100.get(),this.SELLING_RATE, this.CATEGORY);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		// TODO Auto-generated method stub
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TradeInTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (TradeInTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// TODO Auto-generated method stub
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// TODO Auto-generated method stub
		return COMBINED_SHAPE;
	}
}
