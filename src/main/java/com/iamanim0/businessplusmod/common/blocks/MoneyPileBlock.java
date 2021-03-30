package com.iamanim0.businessplusmod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class MoneyPileBlock extends Block {
	
	private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(14, 1, 14, 16, 2, 16);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(14, 0, 0, 16, 1, 16);
	private static final VoxelShape PARTIAL3 = Block.makeCuboidShape(14, 1, 0, 16, 2, 2);
	private static final VoxelShape PARTIAL4 = Block.makeCuboidShape(7, 0, 0, 9, 2, 16);
	private static final VoxelShape PARTIAL5 = Block.makeCuboidShape(0, 2, 0, 16, 3, 16);
	private static final VoxelShape PARTIAL6 = Block.makeCuboidShape(0, 1, 0, 2, 2, 2);
	private static final VoxelShape PARTIAL7 = Block.makeCuboidShape(0, 1, 14, 2, 2, 16);
	private static final VoxelShape PARTIAL8 = Block.makeCuboidShape(0, 0, 0, 2, 1, 16);
	private static final VoxelShape PARTIAL9 =Block.makeCuboidShape(1, 3, 1, 15, 16, 15);
	
	
	private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2, PARTIAL3, PARTIAL4, PARTIAL5, PARTIAL6, PARTIAL7, PARTIAL8, PARTIAL9);
	@SuppressWarnings("unused")
	private static VoxelShape EMPTY_SPACE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE, IBooleanFunction.ONLY_FIRST);

	public MoneyPileBlock(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
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
