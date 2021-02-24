package com.iamanim0.businessplusmod.common.items;

import java.util.List;
import com.ibm.icu.text.DecimalFormat;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class CurrencyItem extends Item {

	private static DecimalFormat df = new DecimalFormat("0.00");
	public double value = 0.00;

	public CurrencyItem(Properties properties, double v) {
		super(properties);
		// TODO Auto-generated constructor stub
		this.value = v;
	}
	
	public double getCurrencyValue()  {
		return this.value;
	}
	
	public double getStackValue(ItemStack stack)  {
		return stack.getCount() * this.value;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		// TODO Auto-generated method stub
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(new StringTextComponent("Value: " + df.format(getCurrencyValue())));
	}
}
