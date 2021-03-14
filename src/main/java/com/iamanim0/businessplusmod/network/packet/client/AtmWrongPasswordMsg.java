package com.iamanim0.businessplusmod.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.iamanim0.businessplusmod.client.screens.AtmScreen;
import com.iamanim0.businessplusmod.client.screens.widget.AtmTextComponent;
import com.iamanim0.businessplusmod.common.containers.ContainerHelper;

public class AtmWrongPasswordMsg {

	 private final int attemptsLeft;

	    public AtmWrongPasswordMsg(int attemptsLeft) {
	        this.attemptsLeft = attemptsLeft;
	    }

	    public void encode(PacketBuffer buffer) {
	        buffer.writeInt(attemptsLeft);
	    }

	    public static AtmWrongPasswordMsg decode(PacketBuffer buffer) {
	        return new AtmWrongPasswordMsg(buffer.readInt());
	    }

	    public void handle(Supplier<NetworkEvent.Context> ctx) {
	        ctx.get().enqueueWork(() -> {
	            @SuppressWarnings("resource")
				final Screen screen = Minecraft.getInstance().currentScreen;
	            if (screen instanceof AtmScreen) {
	                AtmScreen atmScreen = (AtmScreen) screen;
	                atmScreen.keyPadMode = AtmScreen.KeyPadMode.Login;
	                atmScreen.displayPIN.clear();
	                atmScreen.displayMain = new AtmTextComponent(
	                        ContainerHelper.getUnlocalizedText("atm_wrong_password"),
	                        this.attemptsLeft
	                );
	            }
	        });
	        ctx.get().setPacketHandled(true);
	    }
}
