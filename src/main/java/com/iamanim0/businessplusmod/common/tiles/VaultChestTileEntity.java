package com.iamanim0.businessplusmod.common.tiles;

import javax.annotation.Nonnull;

import com.iamanim0.businessplusmod.common.blocks.VaultChestBlock;
import com.iamanim0.businessplusmod.common.containers.VaultChestContainer;
import com.iamanim0.businessplusmod.core.init.TileEntityTypeInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class VaultChestTileEntity extends LockableLootTileEntity {

	private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
	protected int numPlayerUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	
	public VaultChestTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
		// TODO Auto-generated constructor stub
		
		setInventorySlotContents(0, new ItemStack(Items.CHARCOAL.getItem(), 1));
		setInventorySlotContents(1, new ItemStack(Items.COAL.getItem(), 1));
		setInventorySlotContents(2, new ItemStack(Items.IRON_INGOT.getItem(), 1));
		setInventorySlotContents(3, new ItemStack(Items.GOLD_INGOT.getItem(), 1));
		setInventorySlotContents(4, new ItemStack(Items.DIAMOND.getItem(), 1));
		setInventorySlotContents(5, new ItemStack(Items.EMERALD.getItem(), 1));
		
		this.chestContents.set(numPlayerUsing, new ItemStack(Items.NETHERRACK.getItem(), 1));
	}
	
	public VaultChestTileEntity() {
		this(TileEntityTypeInit.VAULT_CHEST.get());
	}
	
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 36;
	}
	
	@Override
	public NonNullList<ItemStack> getItems() {
		// TODO Auto-generated method stub
		return this.chestContents;
	}
	
	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn) {
		// TODO Auto-generated method stub
		this.chestContents = itemsIn;
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent("container.vault_chest");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		// TODO Auto-generated method stub
		super.write(compound);
		if(!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.chestContents);
		}
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		// TODO Auto-generated method stub
		super.read(state, nbt);
		this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if(!this.checkLootAndRead(nbt)) {
			ItemStackHelper.loadAllItems(nbt, this.chestContents);
		}
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type) {
		// TODO Auto-generated method stub
		if(id== 1){
			this.numPlayerUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void openInventory(PlayerEntity player) {
		// TODO Auto-generated method stub
		
		if(!player.isSpectator()) {
			if(this.numPlayerUsing < 0) {
				this.numPlayerUsing = 0;
			}
			
			++this.numPlayerUsing;
			this.onOpenOrClose();
		}
	}
	
	@Override
	public void closeInventory(PlayerEntity player) {
		// TODO Auto-generated method stub
		if(!player.isSpectator()) {			
			--this.numPlayerUsing;
			this.onOpenOrClose();
		}
	}
	
	private void onOpenOrClose() {
		// TODO Auto-generated method stub
		Block block = this.getBlockState().getBlock();
		if(block instanceof VaultChestBlock) {
			this.world.addBlockEvent(this.pos, block, 1, numPlayerUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}
	
	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos);
		if (blockstate.hasTileEntity()) {
			TileEntity tileentity = reader.getTileEntity(pos);
			if (tileentity instanceof VaultChestTileEntity) {
				return ((VaultChestTileEntity) tileentity).numPlayerUsing;
			}
		}
		return 0;
	}
	
	public static void swapContents(VaultChestTileEntity te, VaultChestTileEntity otherTe) {
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		if (this.itemHandler != null) {
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}
	
	@Override
	public void remove() {
		super.remove();
		if(itemHandler != null) {
			itemHandler.invalidate();
		}
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new VaultChestContainer(id, player, this);
	}
	
	
}
