package com.iamanim0.businessplusmod.client.screens.widget;
import net.minecraft.client.gui.widget.button.Button;

public class AtmNumericButton extends AtmButton {

    public int value;
    public AtmNumericButton(int widthIn, int heightIn, int width, int height, int value, Button.IPressable onPress) {
        super(widthIn, heightIn, width, height, Integer.toString(value), 0, onPress);
        this.value = value;
    }
}
