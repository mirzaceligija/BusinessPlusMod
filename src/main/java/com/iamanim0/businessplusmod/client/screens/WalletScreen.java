package com.iamanim0.businessplusmod.client.screens;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.WalletContainer;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;

import net.minecraft.entity.player.PlayerInventory;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.text.DecimalFormat;

public class WalletScreen extends ContainerScreen<WalletContainer> {
	
	// This is the resource location for the background image
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BusinessPlusMod.MOD_ID, "textures/gui/wallet2.png");
	private static DecimalFormat df2 = new DecimalFormat("##.##");

	public WalletScreen(WalletContainer container, PlayerInventory playerInv, ITextComponent title) {
		super(container, playerInv, title);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(matrixStack);
    super.render(matrixStack, mouseX, mouseY, partialTicks);
    this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@SuppressWarnings("resource")
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
    final float PLAYER_LABEL_XPOS = 8;
    final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);
    
    drawString(matrixStack, Minecraft.getInstance().fontRenderer, "Money: ", 6, 6, 0x16a085);
    drawString(matrixStack, Minecraft.getInstance().fontRenderer, df2.format(container.getMoneyIn()) + " $", 43, 7, 0xffffff);

    /*
    //final float BAG_LABEL_YPOS = 6;
    //TranslationTextComponent bagLabel = new TranslationTextComponent(ItemInit.WALLET_ITEM.get().getTranslationKey());
    //float BAG_LABEL_XPOS = (xSize / 2.0F) - this.font.getStringWidth(bagLabel.getString()) / 2.0F;                  // centre the label
    //this.font.func_243248_b(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.darkGray.getRGB());            //this.font.drawString;
     */

    float PLAYER_LABEL_YPOS = ySize - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
    this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(),                              //this.font.drawString;
                    PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.darkGray.getRGB());
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);                //this.minecraft
    // width and height are the size provided to the window when initialised after creation.
    // xSize, ySize are the expected size of the texture-? usually seems to be left as a default.
    // The code below is typical for vanilla containers, so I've just copied that- it appears to centre the texture within
    //  the available window
    int edgeSpacingX = (this.width - this.xSize) / 2;
    int edgeSpacingY = (this.height - this.ySize) / 2;
    this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
	}
}
