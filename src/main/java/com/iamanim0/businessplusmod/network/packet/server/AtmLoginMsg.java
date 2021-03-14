package com.iamanim0.businessplusmod.network.packet.server;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;
import java.util.function.Supplier;

import com.iamanim0.businessplusmod.common.capability.capability.ICreditCardInfo;
import com.iamanim0.businessplusmod.common.capability.provider.CreditCardProvider;
import com.iamanim0.businessplusmod.network.packet.ModPacketHandler;
import com.iamanim0.businessplusmod.network.packet.client.AtmBalanceMsg;
import com.iamanim0.businessplusmod.network.packet.client.AtmCardBlocked;
import com.iamanim0.businessplusmod.network.packet.client.AtmErrorMsg;
import com.iamanim0.businessplusmod.network.packet.client.AtmWrongPasswordMsg;
import com.iamanim0.businessplusmod.world.AccountWorldSavedData;

public class AtmLoginMsg {

	private final Hand hand;
    private final String pinCode;

    public AtmLoginMsg(Hand hand, String pinCode) {
        this.hand = hand;
        this.pinCode = pinCode;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeEnumValue(hand);
        buffer.writeString(pinCode);
    }

    public static AtmLoginMsg decode(PacketBuffer buffer) {
        return new AtmLoginMsg(buffer.readEnumValue(Hand.class), buffer.readString());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity sender = Objects.requireNonNull(ctx.get().getSender());
            final ICreditCardInfo cap = sender.getHeldItem(this.hand).getCapability(CreditCardProvider.CREDIT_CARD_CAPABILITY).orElseThrow(
                    () -> new NullPointerException("Null CreditCard capability")
            );

            if (!cap.hasOwner()) {
                ModPacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sender), new AtmErrorMsg()
                );
                return;
            }

            AccountWorldSavedData accountData = AccountWorldSavedData.get(sender.getServerWorld());
            String realPin = accountData.getCardPIN(cap.getCardNumber());
            if (pinCode.equals(realPin)) {
                cap.resetAttempts();
                ModPacketHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sender),
                        new AtmBalanceMsg(accountData.getBalance(cap.getOwner()))
                );
            } else {
                cap.decreaseAttempts();
                if (cap.hasAnyAttemptsLeft()) {
                    ModPacketHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(() -> sender),
                            new AtmWrongPasswordMsg(cap.getAttemptsLeft())
                    );
                } else {
                    sender.getHeldItem(hand).shrink(1);
                    ModPacketHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(() -> sender),
                            new AtmCardBlocked()
                    );
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
