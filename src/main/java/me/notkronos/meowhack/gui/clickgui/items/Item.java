package me.notkronos.meowhack.gui.clickgui.items;

import me.notkronos.meowhack.util.ColorUtil;

public class Item {
    private final String name;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    private static int clientColor = ColorUtil.decimalToHex(ColorUtil.getPrimaryColor()[0], ColorUtil.getPrimaryColor()[1], ColorUtil.getPrimaryColor()[2]);
    public static int color = ColorUtil.addAlpha(clientColor, 255);

    public Item(final String name) {
        this.name = name;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    }

    public final String getName() {
        return this.name;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void onType(int in) { }
}
