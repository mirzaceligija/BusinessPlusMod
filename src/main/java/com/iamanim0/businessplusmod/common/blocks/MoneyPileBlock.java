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
	
	private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(1, 3, 1, 15, 14, 15);
	
	private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2);
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
