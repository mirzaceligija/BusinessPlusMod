package com.iamanim0.businessplusmod.client.screens;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.MarketContainer;
import com.iamanim0.businessplusmod.common.containers.MarketplaceContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MarketScreen extends ContainerScreen<MarketContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(BusinessPlusMod.MOD_ID, "textures/gui/marketplace.png");
	
	private MarketContainer marketplaceContainer;

	public MarketScreen(MarketContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
        this.marketplaceContainer = screenContainer;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		// TODO Auto-generated method stub
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        // Draw Background
        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;

        //Draw Player Inventory background
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY + 111, 0, 157, 175, 99);

        //Draw Vending Machine background
        this.blit(matrixStack, edgeSpacingX + 32, edgeSpacingY - 47, 0, 0, 124, 157);
	}

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.title, 40, -41, 4210752); //Block Title
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, 117, 4210752); //Inventory Title

        // Draw Top tips of outer machine to cover items inside
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(matrixStack, 98, -31, 176, 245, 11, 11);
        this.blit(matrixStack, 39, -31, 187, 245, 11, 11);
    }
}
