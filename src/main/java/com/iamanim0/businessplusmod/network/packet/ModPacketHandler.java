package com.iamanim0.businessplusmod.network.packet;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.network.packet.client.*;
import com.iamanim0.businessplusmod.network.packet.server.*;

public class ModPacketHandler {

	private static int id = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(BusinessPlusMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessages() {
        INSTANCE.registerMessage(id++, AtmInitSessionMsg.class, AtmInitSessionMsg::encode, AtmInitSessionMsg::decode, AtmInitSessionMsg::handle);
        INSTANCE.registerMessage(id++, AtmCardSignedMsg.class, AtmCardSignedMsg::encode, AtmCardSignedMsg::decode, AtmCardSignedMsg::handle);
        INSTANCE.registerMessage(id++, AtmSignCardMsg.class, AtmSignCardMsg::encode, AtmSignCardMsg::decode, AtmSignCardMsg::handle);
        INSTANCE.registerMessage(id++, AtmLoginMsg.class, AtmLoginMsg::encode, AtmLoginMsg::decode, AtmLoginMsg::handle);
        INSTANCE.registerMessage(id++, AtmBalanceMsg.class, AtmBalanceMsg::encode, AtmBalanceMsg::decode, AtmBalanceMsg::handle);
        INSTANCE.registerMessage(id++, AtmWrongPasswordMsg.class, AtmWrongPasswordMsg::encode, AtmWrongPasswordMsg::decode, AtmWrongPasswordMsg::handle);
        INSTANCE.registerMessage(id++, AtmErrorMsg.class, AtmErrorMsg::encode, AtmErrorMsg::decode, AtmErrorMsg::handle);
        INSTANCE.registerMessage(id++, AtmDepositMsg.class, AtmDepositMsg::encode, AtmDepositMsg::decode, AtmDepositMsg::handle);
        INSTANCE.registerMessage(id++, AtmWithdrawMsg.class, AtmWithdrawMsg::encode, AtmWithdrawMsg::decode, AtmWithdrawMsg::handle);
        INSTANCE.registerMessage(id++, AtmCardBlocked.class, AtmCardBlocked::encode, AtmCardBlocked::decode, AtmCardBlocked::handle);
    }
}
