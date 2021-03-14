package com.iamanim0.businessplusmod.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.iamanim0.businessplusmod.client.screens.AtmScreen;
import com.iamanim0.businessplusmod.client.screens.widget.AtmTextComponent;
import com.iamanim0.businessplusmod.common.containers.ContainerHelper;

public class AtmErrorMsg {
	public void encode(PacketBuffer buffer) {

    }

    public static AtmErrorMsg decode(PacketBuffer buffer) {
        return new AtmErrorMsg();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            @SuppressWarnings("resource")
			final Screen screen = Minecraft.getInstance().currentScreen;
            if (screen instanceof AtmScreen) {
                AtmScreen atmScreen = (AtmScreen) screen;
                atmScreen.keyPadMode = AtmScreen.KeyPadMode.KeyPadOff;
                atmScreen.displayPIN.clear();
                atmScreen.displayMain = new AtmTextComponent(
                        ContainerHelper.getUnlocalizedText("atm_error")
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
