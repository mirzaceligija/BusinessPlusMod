package com.iamanim0.businessplusmod.client.screens.widget;

public class AtmPinTextComponent {

    private String pinCode = "";
    private int value = 0;
    private String text = "";
    private boolean hasDot = false;
    private int charsAfterDot = 0;
    private final char EURO = (char) 0x0024;


    private DisplayMode displayMode = DisplayMode.PIN;

    public void appendPinDigit(int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Digit out of range 0 - 9");
        if (pinCode.length() < 4) {
            pinCode += Integer.toString(digit);
            text += " * ";
        }
    }

    public String getFormattedText() {
        if (displayMode == DisplayMode.PIN)
            return text;
        if (text.length() == 0)
            return "0" + EURO;
        return text + EURO;
    }

    public boolean isPinFull() {
        return this.pinCode.length() == 4;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public int getValue() {
        if (hasDot) {
            if (charsAfterDot == 1)
                return value * 10;
            if (charsAfterDot == 2)
                return value;
        }
        return value * 100;
    }

    public void clear() {
        this.pinCode = "";
        this.text = "";
        this.value = 0;
        this.hasDot = false;
        this.charsAfterDot = 0;
    }

    public void appendDigit(int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Digit out of range 0 - 9");
        if (text.length() >= 7)
            return;
        if (text.length() == 0 && digit == 0)
            return;
        if (hasDot) {
            if (charsAfterDot > 1)
                return;
            charsAfterDot++;
        }
        text += Integer.toString(digit);
        value = value * 10 + digit;
    }

    public void appendDot() {
        if (hasDot)
            return;
        hasDot = true;
        if (text.length() == 0)
            text = "0";
        text += ".";
    }

    public enum DisplayMode {
        PIN,
        Balance
    }
}