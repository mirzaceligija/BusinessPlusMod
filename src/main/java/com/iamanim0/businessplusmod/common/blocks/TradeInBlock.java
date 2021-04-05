package com.iamanim0.businessplusmod.common.blocks;

import com.iamanim0.businessplusmod.common.tiles.TradeInTileEntity;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
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

public class TradeInBlock extends HorizontalBlock {
	
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	private final int SELLING_RATE;
	private final String CATEGORY;
	
	/*
    private static final String[] CATEGORIES = {
    		"miner", "farmer"
    };
    */
	
	/* === STARTUP VOXEL SHAPES */

    // STARTUP BASE
    private static final VoxelShape STARTUP_BASE = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
    
    //STARTUP NORTH & SOUTH
    private static final VoxelShape STARTUP1NS = Block.makeCuboidShape(3, 1, 3, 4, 6, 13);
	private static final VoxelShape STARTUP2NS = Block.makeCuboidShape(12, 1, 3, 13, 6, 13);
	private static final VoxelShape STARTUP3NS = Block.makeCuboidShape(4, 1, 3, 12, 6, 4);
	private static final VoxelShape STARTUP4NS = Block.makeCuboidShape(4, 1, 4, 12, 5, 12);
	private static final VoxelShape STARTUP5NS = Block.makeCuboidShape(4, 1, 12, 12, 6, 13);
	private static final VoxelShape STARTUP6NS = Block.makeCuboidShape(4, 8, 7.5, 12, 12, 8.5);
	private static final VoxelShape STARTUP7NS = Block.makeCuboidShape(6.25, 5, 7.75, 6.75, 8, 8.25);
	private static final VoxelShape STARTUP8NS = Block.makeCuboidShape(9.25, 5, 7.75, 9.75, 8, 8.25);

	private static VoxelShape COMBINED_SHAPE_STARTUP_NS = VoxelShapes.or(STARTUP_BASE, STARTUP1NS, STARTUP2NS, STARTUP3NS, STARTUP4NS, STARTUP5NS, STARTUP6NS, STARTUP7NS, STARTUP8NS);
	private static VoxelShape EMPTY_SPACE_STARTUP_NS = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE_STARTUP_NS, IBooleanFunction.ONLY_FIRST);
	
    
    // STARTUP WEST & EAST
    private static final VoxelShape STARTUP1WE = Block.makeCuboidShape(7.5, 8, 4, 8.5, 12, 12);
	private static final VoxelShape STARTUP2WE = Block.makeCuboidShape(7.75, 5, 9.25, 8.25, 8, 9.75);
	private static final VoxelShape STARTUP3WE = Block.makeCuboidShape(7.75, 5, 6.25, 8.25, 8, 6.75);
	private static final VoxelShape STARTUP4WE = Block.makeCuboidShape(3, 1, 3, 4, 6, 13);
	private static final VoxelShape STARTUP5WE = Block.makeCuboidShape(12, 1, 3, 13, 6, 13);
	private static final VoxelShape STARTUP6WE = Block.makeCuboidShape(4, 1, 3, 12, 6, 4);
	private static final VoxelShape STARTUP7WE = Block.makeCuboidShape(4, 1, 12, 12, 6, 13);
	private static final VoxelShape STARTUP8WE = Block.makeCuboidShape(4, 1, 4, 12, 5, 12);

	private static VoxelShape COMBINED_SHAPE_STARTUP_WE = VoxelShapes.or(STARTUP_BASE, STARTUP1WE, STARTUP2WE, STARTUP3WE, STARTUP4WE, STARTUP5WE, STARTUP6WE, STARTUP7WE, STARTUP8WE);
	private static VoxelShape EMPTY_SPACE_STARTUP_WE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE_STARTUP_WE, IBooleanFunction.ONLY_FIRST);
	
	/* === BUSINESS VOXEL SHAPES */

    // BUSINESS BASE
    private static final VoxelShape BUSINESS_BASE = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
    private static final VoxelShape BUSINESS1NSWE = Block.makeCuboidShape(4, 1, 4, 12, 9, 12);
	private static final VoxelShape BUSINESS2NSWE = Block.makeCuboidShape(3, 1, 3, 4, 10, 13);
	private static final VoxelShape BUSINESS3NSWE = Block.makeCuboidShape(12, 1, 3, 13, 10, 13);
	private static final VoxelShape BUSINESS4NSWE = Block.makeCuboidShape(4, 1, 3, 12, 10, 4);
	private static final VoxelShape BUSINESS5NSWE = Block.makeCuboidShape(4, 1, 12, 12, 10, 13);
	
	//BUSINESS NORTH & SOUTH
	private static final VoxelShape BUSINESS6NS = Block.makeCuboidShape(4, 12, 7.5, 12, 16, 8.5);
	private static final VoxelShape BUSINESS7NS = Block.makeCuboidShape(6.25, 9, 7.75, 6.75, 12, 8.25);
	private static final VoxelShape BUSINESS8NS = Block.makeCuboidShape(9.25, 9, 7.75, 9.75, 12, 8.25);

	private static VoxelShape COMBINED_SHAPE_BUSINESS_NS = VoxelShapes.or(BUSINESS_BASE, BUSINESS1NSWE, BUSINESS2NSWE, BUSINESS3NSWE, BUSINESS4NSWE, BUSINESS5NSWE, BUSINESS6NS, BUSINESS7NS, BUSINESS8NS);
	private static VoxelShape EMPTY_SPACE_BUSINESS_NS = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE_BUSINESS_NS, IBooleanFunction.ONLY_FIRST);
	
	//BUSINESS WEST & EAST
	private static final VoxelShape BUSINESS6WE = Block.makeCuboidShape(7.5, 12, 4, 8.5, 16, 12);
	private static final VoxelShape BUSINESS7WE = Block.makeCuboidShape(7.75, 9, 9.25, 8.25, 12, 9.75);
	private static final VoxelShape BUSINESS8WE = Block.makeCuboidShape(7.75, 9, 6.25, 8.25, 12, 6.75);

	private static VoxelShape COMBINED_SHAPE_BUSINESS_WE = VoxelShapes.or(BUSINESS_BASE, BUSINESS1NSWE, BUSINESS2NSWE, BUSINESS3NSWE, BUSINESS4NSWE, BUSINESS5NSWE, BUSINESS6WE, BUSINESS7WE, BUSINESS8WE);
	private static VoxelShape EMPTY_SPACE_BUSINESS_WE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE_BUSINESS_WE, IBooleanFunction.ONLY_FIRST);
	
	
	public TradeInBlock(Properties builder, int sellingRate, String category) {
		super(builder);
		// TODO Auto-generated constructor stub'
		this.SELLING_RATE = sellingRate;
		this.CATEGORY = category;
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_FACING);
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
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
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
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
		// TODO Auto-generated method stub
		
		if(this.SELLING_RATE == 40 && (state.get(HORIZONTAL_FACING) == Direction.NORTH || state.get(HORIZONTAL_FACING) == Direction.SOUTH))
			return EMPTY_SPACE_STARTUP_NS;
		else if(this.SELLING_RATE == 40 && (state.get(HORIZONTAL_FACING) == Direction.EAST || state.get(HORIZONTAL_FACING) == Direction.WEST))
			return EMPTY_SPACE_STARTUP_WE;

		else if(this.SELLING_RATE == 100 && (state.get(HORIZONTAL_FACING) == Direction.NORTH || state.get(HORIZONTAL_FACING) == Direction.SOUTH))
			return EMPTY_SPACE_BUSINESS_NS;
		else if(this.SELLING_RATE == 100 && (state.get(HORIZONTAL_FACING) == Direction.EAST || state.get(HORIZONTAL_FACING) == Direction.WEST))
			return EMPTY_SPACE_BUSINESS_WE;

		else
			return EMPTY_SPACE_STARTUP_NS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// TODO Auto-generated method stub
		if(this.SELLING_RATE == 40 && (state.get(HORIZONTAL_FACING) == Direction.NORTH || state.get(HORIZONTAL_FACING) == Direction.SOUTH))
			return COMBINED_SHAPE_STARTUP_NS;
		else if(this.SELLING_RATE == 40 && (state.get(HORIZONTAL_FACING) == Direction.EAST || state.get(HORIZONTAL_FACING) == Direction.WEST))
			return COMBINED_SHAPE_STARTUP_WE;

		else if(this.SELLING_RATE == 100 && (state.get(HORIZONTAL_FACING) == Direction.NORTH || state.get(HORIZONTAL_FACING) == Direction.SOUTH))
			return COMBINED_SHAPE_BUSINESS_NS;
		else if(this.SELLING_RATE == 100 && (state.get(HORIZONTAL_FACING) == Direction.EAST || state.get(HORIZONTAL_FACING) == Direction.WEST))
			return COMBINED_SHAPE_BUSINESS_WE;

		else
			return COMBINED_SHAPE_STARTUP_NS;
	}
	
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
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

}
