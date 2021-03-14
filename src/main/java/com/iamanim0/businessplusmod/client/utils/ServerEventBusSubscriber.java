package com.iamanim0.businessplusmod.client.utils;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.world.AccountWorldSavedData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BusinessPlusMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventBusSubscriber {

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.getWorld().isRemote)
            return;
        if (!(event.getEntity() instanceof PlayerEntity))
            return;

        final PlayerEntity player = (PlayerEntity) event.getEntity();
        AccountWorldSavedData.get(event.getWorld()).createAccount(player.getUniqueID(), player.getGameProfile().getName());
    }
}
