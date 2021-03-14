package com.iamanim0.businessplusmod.core.init;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.VaultChestContainer;
import com.iamanim0.businessplusmod.common.containers.WalletContainer;
import com.iamanim0.businessplusmod.common.containers.AtmContainer;
import com.iamanim0.businessplusmod.common.containers.FirstBlockContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerTypeInit {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, BusinessPlusMod.MOD_ID);

	public static final RegistryObject<ContainerType<VaultChestContainer>> VAULT_CHEST = 
			CONTAINER_TYPES.register("vault_chest", () -> IForgeContainerType.create(VaultChestContainer::new));
	
	public static final RegistryObject<ContainerType<WalletContainer>> WALLET = 
			CONTAINER_TYPES.register("wallet", () -> IForgeContainerType.create(WalletContainer::new));
	
	public static final RegistryObject<ContainerType<FirstBlockContainer>> FIRSTBLOCK = 
			CONTAINER_TYPES.register("firstblock", () -> IForgeContainerType.create(FirstBlockContainer::new));
	
	public static final RegistryObject<ContainerType<AtmContainer>> ATM = 
			CONTAINER_TYPES.register("atm", () -> IForgeContainerType.create(AtmContainer::new));
}
