package com.iamanim0.businessplusmod.client.screens.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

import com.iamanim0.businessplusmod.BusinessPlusMod;

public class AtmButton extends Button {
	private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(BusinessPlusMod.MOD_ID, "textures/gui/atm.png");

    private final int offset;
    private boolean isClicked = false;

    public AtmButton(int widthIn, int heightIn, int width, int height, String text, int buttonType, Button.IPressable onPress) {
        super(widthIn, heightIn, width, height, new StringTextComponent(text), onPress);
        this.offset = getButtonOffset(buttonType);
    }

    @SuppressWarnings({ "deprecation", "static-access" })
	@Override
    public void renderButton(MatrixStack matrixStack, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        // idk
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        this.blit(matrixStack, this.x, this.y, this.width * getState() + this.offset, 225, this.width, this.height);
        this.renderBg(matrixStack, minecraft, p_230431_2_, p_230431_3_);
        int j = getFGColor();
        this.drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        super.onClick(p_onClick_1_, p_onClick_3_);
        this.isClicked = true;
    }

    @Override
    public void onRelease(double p_onRelease_1_, double p_onRelease_3_) {
        super.onRelease(p_onRelease_1_, p_onRelease_3_);
        this.isClicked = false;
    }

    private int getState() {
        if (isHovered())
            if (isClicked)
                return 0;
            else
                return 2;
        isClicked = false;
        return 1;
    }

    private static int getButtonOffset(int buttonType) {
        switch (buttonType) {
            case 0:
                return 0;
            case 1:
                return 48;
            case 2:
                return 96;
            case 3:
                return 165;
        }

        return 0;
    }
}
