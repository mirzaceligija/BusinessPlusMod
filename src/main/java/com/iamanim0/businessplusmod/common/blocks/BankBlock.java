package com.iamanim0.businessplusmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.state.StateContainer.Builder;

public class BankBlock extends HorizontalBlock {
	
	private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(0, 1, 0, 4, 2, 4);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(0, 1, 12, 4, 2, 16);
	private static final VoxelShape PARTIAL3 = Block.makeCuboidShape(0, 1, 6, 4, 2, 10);
	
	private static final VoxelShape PARTIAL4 = Block.makeCuboidShape(12, 1, 12, 16, 2, 16);
	private static final VoxelShape PARTIAL5 = Block.makeCuboidShape(12, 1, 0, 16, 2, 4);
	private static final VoxelShape PARTIAL6 = Block.makeCuboidShape(12, 1, 6, 16, 2, 10);
	
	private static final VoxelShape PARTIAL7 = Block.makeCuboidShape(1, 2, 1, 3, 7, 3);
	private static final VoxelShape PARTIAL8 = Block.makeCuboidShape(1, 2, 7, 3, 7, 9);
	private static final VoxelShape PARTIAL9 = Block.makeCuboidShape(1, 2, 13, 3, 7, 15);
	
	private static final VoxelShape PARTIAL10 = Block.makeCuboidShape(13, 2, 7, 15, 7, 9);
	private static final VoxelShape PARTIAL11 = Block.makeCuboidShape(13, 2, 1, 15, 7, 3);
	private static final VoxelShape PARTIAL12 = Block.makeCuboidShape(13, 2, 13, 15, 7, 15);
	
	private static final VoxelShape PARTIAL13 = Block.makeCuboidShape(0, 7, 0, 16, 8, 16);
	private static final VoxelShape PARTIAL14 = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
	
	private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2, PARTIAL3, PARTIAL4, PARTIAL5, PARTIAL6, PARTIAL7, PARTIAL8, PARTIAL9, PARTIAL10, PARTIAL11, PARTIAL12, PARTIAL13, PARTIAL14);
	@SuppressWarnings("unused")
	private static VoxelShape EMPTY_SPACE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE, IBooleanFunction.ONLY_FIRST);
	
	public BankBlock(Properties builder) {
		super(builder);
		// TODO Auto-generated constructor stub
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}
	
	@Override
	public BlockState rotate(BlockState state, IWorld world, BlockPos pos, net.minecraft.util.Rotation direction) {
		// TODO Auto-generated method stub
		return state.with(HORIZONTAL_FACING, direction.rotate(state.get(HORIZONTAL_FACING)));
	}


	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_FACING);
	}

    @Override
    public boolean hasTileEntity(BlockState state) {
        return false;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
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
