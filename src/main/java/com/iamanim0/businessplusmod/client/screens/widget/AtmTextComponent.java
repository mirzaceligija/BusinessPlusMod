package com.iamanim0.businessplusmod.client.screens.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class AtmTextComponent {

    private String text;
    private final List<String> formats;
    private final int width;
    private int offset = 0;

    private static final int MAX_SENTENCE_LENGTH = 156;

    @SuppressWarnings("resource")
	public AtmTextComponent(String unlocalizedName, Object... args) {
        this.formats = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof String)
                formats.add((String) arg);
            else if (arg instanceof Integer)
                formats.add(Integer.toString((Integer) arg));
            else if (arg instanceof AtmBalanceTextComponent)
                formats.add(arg.toString());
            else
                throw new IllegalArgumentException("Invalid Type Of AtmTextComponent");
        }
        text = applyFormats(new TranslationTextComponent(unlocalizedName).getString());
        width = Minecraft.getInstance().fontRenderer.getStringWidth(text);
        if (width > MAX_SENTENCE_LENGTH) {
            text += "        " + text;
            offset = width;
        }
    }

    public void tick() {
        if (width <= MAX_SENTENCE_LENGTH)
            return;
        if (offset++ == width + 32)
            offset = 1;
    }

    private String applyFormats(String text) {
        text = text.replaceAll("@s", "%s");
        for (String format : formats) {
            text = String.format(text, format);
        }
        return text;
    }

    public String getFormattedText() {
        return text;
    }

    public int getOffset() {
        return this.offset;
    }
}
