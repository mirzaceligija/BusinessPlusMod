package com.iamanim0.businessplusmod.common.items;

import java.util.List;

import com.iamanim0.businessplusmod.core.init.BlockInit;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BusinessLicenceItem extends Item {

	public BusinessLicenceItem(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new StringTextComponent("It spawns 7x7 office!"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		// TODO Auto-generated method stub
		int x = (int) playerIn.getPosX();
		int y =  (int) playerIn.getPosY() -1;
		int z =  (int) playerIn.getPosZ();
		
		for (int i=0; i<7; i++) {
			for (int j=6; j>=0; j--) {
				worldIn.setBlockState(new BlockPos( x-i, y, z-j), Blocks.OAK_PLANKS.getDefaultState());
			}
		}
		
		worldIn.setBlockState(new BlockPos( x-3, y+1, z-3), BlockInit.VAULT_CHEST.get().getDefaultState());

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
