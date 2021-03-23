package com.iamanim0.businessplusmod.client.screens;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.MarketContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MarketScreen extends ContainerScreen<MarketContainer> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(BusinessPlusMod.MOD_ID, "textures/gui/marketplace.png");
	@SuppressWarnings("unused")
	private MarketContainer marketContainer;

	public MarketScreen(MarketContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.marketContainer = screenContainer;
		// TODO Auto-generated constructor stub
		this.guiLeft = this.width/2;
		this.guiTop = this.height/2;
		this.xSize = 255;
		this.ySize = 255;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		this.renderBackground(matrixStack, mouseY);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		// TODO Auto-generated method stub
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		int mouseX = (this.width - this.xSize) / 2;
		int mouseY = (this.height - this.ySize) / 2;
		this.blit(matrixStack, mouseX, mouseY, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		// TODO Auto-generated method stub
		//this.font.drawString(matrixStack, this.title.getString(), 8.0F, 15.0F, 0x404040);
		this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 48.0F, 162.0F, 0x404040);
		//this.font.drawString(matrixStack, marketContainer.tileEntity.currentMoney/100.00 + "", 180.0f, 36.0f, 16777215);
	}

}
