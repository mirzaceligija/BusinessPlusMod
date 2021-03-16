package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.blocks.AtmBlock;
import com.iamanim0.businessplusmod.common.blocks.FirstBlock;
import com.iamanim0.businessplusmod.common.blocks.MarketBlock;
import com.iamanim0.businessplusmod.common.blocks.MarketplaceBlock;
import com.iamanim0.businessplusmod.common.blocks.VaultChestBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.properties.BlockStateProperties;
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

	public static final RegistryObject<Block> MONEY_PILE = BLOCKS.register("money_pile", 
			() -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE)
					.hardnessAndResistance(15f, 30f)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
					.sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> FIRSTBLOCK = BLOCKS.register("firstblock", 
			() -> new FirstBlock(AbstractBlock.Properties.create(Material.IRON)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(2.0f)
	                .setLightLevel(state -> state.get(BlockStateProperties.POWERED) ? 14 : 0)));
	
	public static final RegistryObject<Block> ATM_BLOCK  = BLOCKS.register("atm", 
			() -> new AtmBlock(AbstractBlock.Properties.create(Material.IRON)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(2.0f)));
	
	public static final RegistryObject<Block> MARKETPLACE  = BLOCKS.register("marketplace", 
			() -> new MarketplaceBlock(AbstractBlock.Properties.create(Material.IRON)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(2.0f)));
	
	public static final RegistryObject<Block> MARKET  = BLOCKS.register("market", 
			() -> new MarketBlock(AbstractBlock.Properties.create(Material.IRON)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(2.0f)));
}
