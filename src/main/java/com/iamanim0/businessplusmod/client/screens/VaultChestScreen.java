package com.iamanim0.businessplusmod.client.screens;

import com.iamanim0.businessplusmod.BusinessPlusMod;
import com.iamanim0.businessplusmod.common.containers.VaultChestContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class VaultChestScreen extends ContainerScreen <VaultChestContainer> {
	
	//private double totalValue = 0;

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BusinessPlusMod.MOD_ID, "textures/gui/vault_chest.png");
	
	public VaultChestScreen(VaultChestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 175;
		this.ySize = 183;
	}
	
	@Override
	public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
		// TODO Auto-generated method stub
		this.renderBackground(matrixStack, mouseY);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
		// TODO Auto-generated method stub
		//super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		//this.font.drawString(matrixStack, getNarrationMessage(), 8.0f, 6.0f, 4210752);
		this.font.drawString(matrixStack, "Vault Chest", 8.0f, 6.0f, 4210752);
		this.font.drawString(matrixStack, "Inventory", 8.0f, 91.0f, 4210752);
		//this.font.drawString(matrixStack, "Total amount: " + this.totalValue, 8.0f, 6.0f, 4210752);
		
		/*
		//this.font.drawString(this.playerInventory.getDisplayName()., 8.0f, 90.0f, 4210752);
		//this.font.drawString(matrixStack, this.playerInventory.getDisplayName().toString(), 8.0f, 90.0f, 4210752);
		//this.font.drawString(matrixStack, "Vault Chest", 8.0f, 6.0f, 4210752);
		//this.font.drawString(matrixStack, "Inventory", 8.0f, 91.0f, 4210752);
		 * //this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0f, 91.0f, 4210752);
		*/
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		// TODO Auto-generated method stub
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int mouseX = (this.width - this.xSize) / 2;
		int mouseY = (this.height - this.ySize) / 2;
		this.blit(matrixStack, mouseX, mouseY, 0, 0, this.xSize, this.ySize);
	}
}
