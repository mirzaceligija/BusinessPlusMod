package com.iamanim0.businessplusmod.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.iamanim0.businessplusmod.client.screens.AtmScreen;
import com.iamanim0.businessplusmod.client.screens.widget.AtmBalanceTextComponent;
import com.iamanim0.businessplusmod.client.screens.widget.AtmPinTextComponent;
import com.iamanim0.businessplusmod.client.screens.widget.AtmTextComponent;
import com.iamanim0.businessplusmod.common.containers.ContainerHelper;

public class AtmBalanceMsg {

	private final int balance;

    public AtmBalanceMsg(int balance) {
        this.balance = balance;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(balance);
    }

    public static AtmBalanceMsg decode(PacketBuffer buffer) {
        return new AtmBalanceMsg(buffer.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            @SuppressWarnings("resource")
			final Screen screen = Minecraft.getInstance().currentScreen;
            if (screen instanceof AtmScreen) {
                AtmScreen atmScreen = (AtmScreen) screen;
                atmScreen.keyPadMode = AtmScreen.KeyPadMode.Balance;
                atmScreen.displayPIN.clear();
                atmScreen.displayPIN.setDisplayMode(AtmPinTextComponent.DisplayMode.Balance);
                atmScreen.displayMain = new AtmTextComponent(
                        ContainerHelper.getUnlocalizedText("atm_balance"),
                        new AtmBalanceTextComponent(this.balance)
                );
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
