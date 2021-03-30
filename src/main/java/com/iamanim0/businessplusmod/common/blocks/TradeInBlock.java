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
	
    //BIG Office
    private static final VoxelShape PARTIAL1 = Block.makeCuboidShape(3, 1, 3, 13, 12, 12);
	private static final VoxelShape PARTIAL2 = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
    private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2);
    
    //Small Office
    private static final VoxelShape PARTIAL1B = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
	private static final VoxelShape PARTIAL2B = Block.makeCuboidShape(3, 1, 3, 13, 6, 12);
	private static final VoxelShape PARTIAL3B = Block.makeCuboidShape(6, 5, 8, 7, 8, 9);
	private static final VoxelShape PARTIAL4B = Block.makeCuboidShape(4, 8, 8, 12, 12, 9);
	private static final VoxelShape PARTIAL5B = Block.makeCuboidShape(9, 5, 8, 10, 8, 9);
    private static VoxelShape COMBINED_SHAPEB = VoxelShapes.or(PARTIAL1B, PARTIAL2B, PARTIAL3B, PARTIAL4B, PARTIAL5B);

	
	//private static VoxelShape COMBINED_SHAPE = VoxelShapes.or(PARTIAL1, PARTIAL2, PARTIAL3, PARTIAL4, PARTIAL5);
	@SuppressWarnings("unused")
	private static VoxelShape EMPTY_SPACE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPE, IBooleanFunction.ONLY_FIRST);
	@SuppressWarnings("unused")
	private static VoxelShape EMPTY_SPACEB = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), COMBINED_SHAPEB, IBooleanFunction.ONLY_FIRST);
	
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
		// TODO Auto-generated method stub
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

	/*
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
	*/
	
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
		//return super.getCollisionShape(state, reader, pos);
		if(this.SELLING_RATE == 100)
			return COMBINED_SHAPE;
		return COMBINED_SHAPEB;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		// TODO Auto-generated method stub
		if(this.SELLING_RATE == 100)
			return COMBINED_SHAPE;
		return COMBINED_SHAPEB;
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
		return state.with(HORIZONTAL_FACING, direction.rotate(state.get(HORIZONTAL_FACING)));
	}
}
