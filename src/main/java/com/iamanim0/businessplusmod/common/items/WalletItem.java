package com.iamanim0.businessplusmod.common.items;


import com.iamanim0.businessplusmod.common.containers.WalletContainer;
import com.iamanim0.businessplusmod.core.util.CapabilityProviderWallet;
import com.iamanim0.businessplusmod.core.util.ItemStackHandlerWallet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WalletItem extends Item {

	
	public WalletItem(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	
	//When the player right clicks while holding the bag, open the inventory screen
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote) {  // server only!
			INamedContainerProvider containerProviderWallet = new WalletContainerProvider(this, stack);
			final int NUMBER_OF_MONEY_SLOTS = 18;
			NetworkHooks.openGui((ServerPlayerEntity) player,
							containerProviderWallet,
                           (packetBuffer)->{packetBuffer.writeInt(NUMBER_OF_MONEY_SLOTS);});
		}
		return ActionResult.resultSuccess(stack);
	}
		

   // If we use the item on a block with an ITEM_HANDLER_CAPABILITY, automatically transfer the entire contents of the flower bag into that block
   // onItemUseFirst is a forge extension that is called before the block is activated
	@Nonnull
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx) {
	
		World world = ctx.getWorld();
		if (world.isRemote()) return ActionResultType.PASS;

	    BlockPos pos = ctx.getPos();
	    Direction side = ctx.getFace();
	    ItemStack itemStack = ctx.getItem();
	    if (!(itemStack.getItem() instanceof WalletItem)) throw new AssertionError("Unexpected WalletItem type");
	    WalletItem walletItem = (WalletItem)itemStack.getItem();
	    TileEntity tileEntity = world.getTileEntity(pos);

	    if (tileEntity == null) return ActionResultType.PASS;
	    if (world.isRemote()) return ActionResultType.SUCCESS; // always succeed on client side

	    // Check if this object has an inventory - either Forge capability, or vanilla IInventory
	    IItemHandler tileInventory;
	    LazyOptional<IItemHandler> capability = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	    if (capability.isPresent()) {
	    	tileInventory = capability.orElseThrow(AssertionError::new);
	    } else if (tileEntity instanceof IInventory) {
	    	tileInventory = new InvWrapper((IInventory)tileEntity);
	    } else {
	    	return ActionResultType.FAIL;
	    }

	    // Go through each currency ItemStack in our wallet and try to insert as many as possible into the tile's inventory.
	    @SuppressWarnings("static-access")
	    ItemStackHandlerWallet itemStackHandlerWallet =  walletItem.getItemStackHandlerWallet(itemStack);
	    for (int i = 0; i < itemStackHandlerWallet.getSlots(); i++) {
	    	ItemStack currency = itemStackHandlerWallet.getStackInSlot(i);
	    	ItemStack currencyWhichDidNotFit = ItemHandlerHelper.insertItemStacked(tileInventory, currency, false);
	    	itemStackHandlerWallet.setStackInSlot(i, currencyWhichDidNotFit);
	    }
	    tileEntity.markDirty();  // Make sure that the tileEntity knows we have changed its contents

	    CompoundNBT nbt = itemStack.getOrCreateTag();
	    int dirtyCounter = nbt.getInt("dirtyCounter");
	    nbt.putInt("dirtyCounter", dirtyCounter + 1);
	    itemStack.setTag(nbt);
	
	    return ActionResultType.SUCCESS;
	}


    // ------  Code used to generate a suitable Container for the contents of the FlowerBag

	/*
	 * Uses an inner class as an INamedContainerProvider.  This does two things:
	 *   1) Provides a name used when displaying the container, and
	 *   2) Creates an instance of container on the server which is linked to the ItemFlowerBag
	 * You could use SimpleNamedContainerProvider with a lambda instead, but I find this method easier to understand
	 * I've used a static inner class instead of a non-static inner class for the same reason
	 */
	private static class WalletContainerProvider implements INamedContainerProvider {
	    public WalletContainerProvider(WalletItem itemWallet, ItemStack itemStackWallet) {
	      this.itemStackWallet = itemStackWallet;
	      this.itemWallet = itemWallet;
	    }

	    @Override
	    public ITextComponent getDisplayName() {
	      return itemStackWallet.getDisplayName();
	    }

	    /*
	    * The name is misleading; createMenu has nothing to do with creating a Screen, it is used to create the Container on the server only
	    */
	    @Override
	    public WalletContainer createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
	    @SuppressWarnings("static-access")
			WalletContainer newContainerServerSide = WalletContainer.createContainerServerSide(windowID, playerInventory,
		    				  itemWallet.getItemStackHandlerWallet(itemStackWallet), itemStackWallet);
		      return newContainerServerSide;
	    }

	    private WalletItem itemWallet;
	    private ItemStack itemStackWallet;
	}

	// ---------------- Code related to Capabilities //
	
	@Nonnull
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {
		return new CapabilityProviderWallet();
	}
	
	private static ItemStackHandlerWallet getItemStackHandlerWallet(ItemStack itemStack) {
		IItemHandler wallet = itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
	    if (wallet == null || !(wallet instanceof ItemStackHandlerWallet)) {
	    	LOGGER.error("ItemFlowerBag did not have the expected ITEM_HANDLER_CAPABILITY");
	    	return new ItemStackHandlerWallet(1);
	    }
	    return (ItemStackHandlerWallet)wallet;
	}
	
	private final String BASE_NBT_TAG = "base";
	private final String CAPABILITY_NBT_TAG = "cap";
	

	@Nullable
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT baseTag = stack.getTag();
	    ItemStackHandlerWallet itemStackHandlerFlowerBag = getItemStackHandlerWallet(stack);
	    CompoundNBT capabilityTag = itemStackHandlerFlowerBag.serializeNBT();
	    CompoundNBT combinedTag = new CompoundNBT();
	    if (baseTag != null) {
	      combinedTag.put(BASE_NBT_TAG, baseTag);
	    }
	    if (capabilityTag != null) {
	      combinedTag.put(CAPABILITY_NBT_TAG, capabilityTag);
	    }
	    return combinedTag;
	}
	
	/** Retrieve our capability information from the transmitted NBT information
	 *
	 * @param stack The stack that received NBT
	 * @param nbt   Received NBT, can be null
	 */
	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		if (nbt == null) {
	      stack.setTag(null);
	      return;
	    }
	    CompoundNBT baseTag = nbt.getCompound(BASE_NBT_TAG);              // empty if not found
	    CompoundNBT capabilityTag = nbt.getCompound(CAPABILITY_NBT_TAG); // empty if not found
	    stack.setTag(baseTag);
	    ItemStackHandlerWallet itemStackHandlerFlowerBag = getItemStackHandlerWallet(stack);
	    itemStackHandlerFlowerBag.deserializeNBT(capabilityTag);
	}
	
	// ------------ Code used for changing the appearance of the bag based on the number of flowers in it
	
	/**
	 * gets the fullness property override, used in mbe32_flower_bag_registry_name.json to select which model should
	 *   be rendered
	 * @param itemStack
	 * @param world
	 * @param livingEntity
	 * @return 0.0 (empty) -> 1.0 (full) based on the number of slots in the bag which are in use
	 */
	public static float getFullnessPropertyOverride(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
		ItemStackHandlerWallet wallet = getItemStackHandlerWallet(itemStack);
	    float fractionEmpty = wallet.getNumberOfEmptySlots() / (float)wallet.getSlots();
	    return 1.0F - fractionEmpty;
	}
	
	private static final Logger LOGGER = LogManager.getLogger();
}
