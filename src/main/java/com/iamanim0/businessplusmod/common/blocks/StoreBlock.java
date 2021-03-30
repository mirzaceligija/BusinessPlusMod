package com.iamanim0.businessplusmod.common.blocks;

import com.iamanim0.businessplusmod.common.tiles.StoreTileEntity;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class StoreBlock  extends HorizontalBlock {

	private final String CATEGORY;
	
	private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(3, 0, 2, 13, 7, 14);
	
	private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2);
	@SuppressWarnings("unused")
	private static VoxelShape EMPTY_SPACE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE, IBooleanFunction.ONLY_FIRST);
	
	public StoreBlock(Properties builder,  String category) {
		super(builder);
		// TODO Auto-generated constructor stub
		this.CATEGORY = category;
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		if(this.CATEGORY == "miner")
			return  new StoreTileEntity(TileEntityTypeInit.STORE_MINER.get(), this.CATEGORY);
		else if(this.CATEGORY == "farmer")
			return  new StoreTileEntity(TileEntityTypeInit.STORE_FARMER.get(), this.CATEGORY);
		else
			return  new StoreTileEntity(TileEntityTypeInit.STORE_MINER.get(), this.CATEGORY);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		// TODO Auto-generated method stub
		if(!worldIn.isRemote) {
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof StoreTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (StoreTileEntity) tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}
	
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// TODO Auto-generated method stub
		return COMBINED_SHAPE;
	}
	

	//Horizontal Block
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_FACING);
	}
	
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
    }
    
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}
	
	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, net.minecraft.util.Rotation direction) {
		// TODO Auto-generated method stub
		return state.with(HORIZONTAL_FACING, direction.rotate(state.get(HORIZONTAL_FACING).getOpposite()));
	}
}
