package com.iamanim0.businessplusmod.common.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
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
	
		
	public static void executeProcedure(Map<String, Object> dependencies) {

		World worldIn = (World) dependencies.get("world");
		@SuppressWarnings("unused")
		PlayerEntity entity = (PlayerEntity) dependencies.get("entity");
		
		int x = (int) dependencies.get("x");
		int y = (int) dependencies.get("y");
		int z = (int) dependencies.get("z");
		
		for (int i=x-3; i<=x+3; i++) {
			for (int j=z+3; j>=z-3; j--) {
				worldIn.setBlockState(new BlockPos( i, y, j), Blocks.OAK_PLANKS.getDefaultState());
				worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, x, y, z);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		// TODO Auto-generated method stub
		ActionResultType retval = super.onItemUseFirst(stack, context);
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		PlayerEntity entity = context.getPlayer();
		@SuppressWarnings("unused")
		Direction direction = context.getFace();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		@SuppressWarnings("unused")
		ItemStack itemstack = context.getItem();
		{
			Map<String, Object> $_dependencies = new HashMap<>();
			$_dependencies.put("x", x);
			$_dependencies.put("y", y);
			$_dependencies.put("z",z);
			$_dependencies.put("world", world);
			$_dependencies.put("entity", entity);
			this.executeProcedure($_dependencies);
		}
		return retval;
	}
}
