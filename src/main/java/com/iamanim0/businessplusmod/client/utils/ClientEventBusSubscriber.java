package com.iamanim0.businessplusmod.client.utils;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.client.screens.AtmScreen;
import com.iamanim0.businessplusmod.client.screens.FirstBlockScreen;
import com.iamanim0.businessplusmod.client.screens.VaultChestScreen;
import com.iamanim0.businessplusmod.client.screens.WalletScreen;
import com.iamanim0.businessplusmod.core.init.ContainerTypeInit;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BusinessPlusMod.MOD_ID, bus = Bus.MOD, value=Dist.CLIENT)
public class ClientEventBusSubscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ContainerTypeInit.VAULT_CHEST.get(), VaultChestScreen::new);
		ScreenManager.registerFactory(ContainerTypeInit.WALLET.get(), WalletScreen::new);
		ScreenManager.registerFactory(ContainerTypeInit.FIRSTBLOCK.get(), FirstBlockScreen::new);
		ScreenManager.registerFactory(ContainerTypeInit.ATM.get(), AtmScreen::new);
	}
}
