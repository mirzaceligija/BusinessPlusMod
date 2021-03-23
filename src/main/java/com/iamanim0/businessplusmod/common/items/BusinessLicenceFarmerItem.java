package com.iamanim0.businessplusmod.common.items;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BusinessLicenceFarmerItem extends Item {

	public BusinessLicenceFarmerItem(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT))
			tooltip.add(new StringTextComponent("Crafts a Farmer Business Machine with 100% selling rate!"));
		else
			tooltip.add(new StringTextComponent("Hold" +  "\u00A7e" + " Shift" + "\u00A77" + " for More Information"));
		
		//tooltip.add(new StringTextComponent("Crafts a Farmer Business Machine with 100% selling rate!"));
	}
}
