package com.iamanim0.businessplusmod.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Objects;

import com.iamanim0.businessplusmod.common.containers.ContainerHelper;
import com.iamanim0.businessplusmod.common.tiles.AtmTileEntity;
import com.iamanim0.businessplusmod.core.init.BlockInit;

public class CreditCardItem extends Item {

    public CreditCardItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("resource")
	@Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getWorld().isRemote)
            return super.onItemUse(context);
        if (context.getWorld().getBlockState(context.getPos()).getBlock() != BlockInit.ATM_BLOCK.get())
            return super.onItemUse(context);

        TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
        if (tileEntity instanceof AtmTileEntity) {
            AtmTileEntity atmTileEntity = (AtmTileEntity) tileEntity;
            if (!atmTileEntity.isAvailable()) {
                final PlayerEntity player = context.getPlayer();
                if (player == null)
                    return ActionResultType.PASS;
                player.sendMessage(new TranslationTextComponent(ContainerHelper.getUnlocalizedText("atm_not_available")), player.getGameProfile().getId());
                return ActionResultType.SUCCESS;
            }
            atmTileEntity.hand = context.getHand();
            NetworkHooks.openGui((ServerPlayerEntity) Objects.requireNonNull(context.getPlayer()), atmTileEntity, packet -> {
                        packet.writeBlockPos(context.getPos());
                        packet.writeByte(context.getHand().ordinal());
                    }
            );
        }

        return ActionResultType.SUCCESS;
    }
    
}
