package com.iamanim0.businessplusmod.common.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.capability.provider.CreditCardProvider;
import com.iamanim0.businessplusmod.core.init.ItemInit;

public class CapabilityHandler {
    public static final ResourceLocation CREDIT_CARD_CAP = new ResourceLocation(BusinessPlusMod.MOD_ID, "credit_card_item");
    public static final ResourceLocation BANK_ACCOUNT_CAP = new ResourceLocation(BusinessPlusMod.MOD_ID, "bank_account");

    @SubscribeEvent
    public void attachCapabilityStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == ItemInit.CREDIT_CARD_ITEM.get()) {
        	event.addCapability(CREDIT_CARD_CAP, new CreditCardProvider());
        }
    }
}
