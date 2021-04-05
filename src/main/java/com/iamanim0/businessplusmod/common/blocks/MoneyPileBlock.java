package com.iamanim0.businessplusmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class MoneyPileBlock extends HorizontalBlock {
	
	private static final VoxelShape BASE = Block.makeCuboidShape(1, 3, 1, 15, 16, 15);
	
	//NORTH & SOUTH
	private static final VoxelShape PLANK2N = Block.makeCuboidShape(2, 2, 14, 14, 3, 16);
	private static final VoxelShape PLANK3N = Block.makeCuboidShape(2, 2, 0, 14, 3, 2);
	
	private static final VoxelShape PLANK4N = Block.makeCuboidShape(7, 1, 0, 9, 2, 2);
	private static final VoxelShape PLANK5N = Block.makeCuboidShape(7, 0, 0, 9, 1, 16);
	private static final VoxelShape PLANK6N = Block.makeCuboidShape(7, 1, 14, 9, 2, 16);
	
	private static final VoxelShape PLANK7N = Block.makeCuboidShape(0, 2, 0, 2, 3, 16);
	private static final VoxelShape PLANK8N = Block.makeCuboidShape(0, 1, 0, 2, 2, 2);
	private static final VoxelShape PLANK9N = Block.makeCuboidShape(0, 0, 0, 2, 1, 16);
	private static final VoxelShape PLANK10N = Block.makeCuboidShape(0, 1, 14, 2, 2, 16);
	
	private static final VoxelShape PLANK11N = Block.makeCuboidShape(14, 2, 0, 16, 3, 16);
	private static final VoxelShape PLANK12N = Block.makeCuboidShape(14, 1, 0, 16, 2, 2);
	private static final VoxelShape PLANK13N = Block.makeCuboidShape(14, 0, 0, 16, 1, 16);
	private static final VoxelShape PLANK14N = Block.makeCuboidShape(14, 1, 14, 16, 2, 16);
	
	
	private static VoxelShape COMBINED_SHAPE_NORTH = VoxelShapes.or(BASE, PLANK2N, PLANK3N, PLANK4N, PLANK5N, PLANK6N, PLANK7N, PLANK8N, PLANK9N, PLANK10N, PLANK11N, PLANK12N, PLANK13N, PLANK14N);
	//@SuppressWarnings("unused")
	//private static VoxelShape EMPTY_SPACE_NORTH = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE_NORTH, IBooleanFunction.ONLY_FIRST);

	//EAST & WEST

	private static final VoxelShape PLANK2E = Block.makeCuboidShape(14, 2, 2, 16, 3, 14);
	private static final VoxelShape PLANK3E = Block.makeCuboidShape(0, 2, 2, 2, 3, 14);
	
	private static final VoxelShape PLANK4E = Block.makeCuboidShape(0, 1, 7, 2, 2, 9);
	private static final VoxelShape PLANK5E = Block.makeCuboidShape(0, 0, 7, 16, 1, 9);
	private static final VoxelShape PLANK6E = Block.makeCuboidShape(14, 1, 7, 16, 2, 9);
	
	private static final VoxelShape PLANK7E = Block.makeCuboidShape(0, 2, 14, 16, 3, 16);
	private static final VoxelShape PLANK8E = Block.makeCuboidShape(0, 1, 14, 2, 2, 16);
	private static final VoxelShape PLANK9E = Block.makeCuboidShape(0, 0, 14, 16, 1, 16);
	private static final VoxelShape PLANK10E = Block.makeCuboidShape(14, 1, 14, 16, 2, 16);
	
	private static final VoxelShape PLANK11E = Block.makeCuboidShape(0, 2, 0, 16, 3, 2);
	private static final VoxelShape PLANK12E = Block.makeCuboidShape(0, 1, 0, 2, 2, 2);
	private static final VoxelShape PLANK13E = Block.makeCuboidShape(0, 0, 0, 16, 1, 2);
	private static final VoxelShape PLANK14E = Block.makeCuboidShape(14, 1, 0, 16, 2, 2);
	
	private static VoxelShape COMBINED_SHAPE_EAST = VoxelShapes.or(BASE, PLANK2E, PLANK3E, PLANK4E, PLANK5E, PLANK6E, PLANK7E, PLANK8E, PLANK9E, PLANK10E, PLANK11E, PLANK12E, PLANK13E, PLANK14E);

	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public MoneyPileBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	}

	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		// TODO Auto-generated method stub
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// TODO Auto-generated method stub
		if(state.get(HORIZONTAL_FACING) == Direction.NORTH || state.get(HORIZONTAL_FACING) == Direction.SOUTH)
			return COMBINED_SHAPE_NORTH;
		else
			return COMBINED_SHAPE_EAST;
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
