package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.blocks.AtmBlock;
import com.iamanim0.businessplusmod.common.blocks.MoneyPileBlock;
import com.iamanim0.businessplusmod.common.blocks.StoreBlock;
import com.iamanim0.businessplusmod.common.blocks.TradeInBlock;

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

	public static final RegistryObject<Block> MONEY_PILE = BLOCKS.register("money_pile", 
			() -> new MoneyPileBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GREEN)
					.hardnessAndResistance(2f, 2f)
					.harvestTool(ToolType.AXE)
					.harvestLevel(-1)
					.sound(SoundType.WOOD)));
	
	public static final RegistryObject<Block> ATM_BLOCK  = BLOCKS.register("atm", 
			() -> new AtmBlock(AbstractBlock.Properties.create(Material.IRON)
					.hardnessAndResistance(5f, 6f)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)));
	
	// === STORE BLOCKS === //
	
	public static final RegistryObject<Block> STORE_MINER  = BLOCKS.register("store_miner", 
			() -> new StoreBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), "miner"));
	
	public static final RegistryObject<Block> STORE_FARMER  = BLOCKS.register("store_farmer", 
			() -> new StoreBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), "farmer"));
	
	// === TRADE IN BLOCKS === //
	
	public static final RegistryObject<Block> TRADEIN_MINER_40  = BLOCKS.register("tradein_miner_40", 
			() -> new TradeInBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), 40, "miner"));
	
	public static final RegistryObject<Block> TRADEIN_MINER_100  = BLOCKS.register("tradein_miner_100", 
			() -> new TradeInBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), 100, "miner"));
	
	public static final RegistryObject<Block> TRADEIN_FARMER_40  = BLOCKS.register("tradein_farmer_40", 
			() -> new TradeInBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), 40, "farmer"));
	
	public static final RegistryObject<Block> TRADEIN_FARMER_100  = BLOCKS.register("tradein_farmer_100", 
			() -> new TradeInBlock(AbstractBlock.Properties.create(Material.IRON)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
	                .sound(SoundType.METAL)
	                .hardnessAndResistance(30f, 6f), 100, "farmer"));
}
