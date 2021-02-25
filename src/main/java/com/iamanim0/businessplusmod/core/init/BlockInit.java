package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.blocks.VaultChestBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {


	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BusinessPlusMod.MOD_ID);
	
	public static final RegistryObject<Block> VAULT_CHEST = BLOCKS.register("vault_chest", 
			() -> new VaultChestBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE)
					.hardnessAndResistance(15f, 30f)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
					.sound(SoundType.ANVIL)));

}
