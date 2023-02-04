package me.notkronos.meowhack.util.render;

import me.notkronos.meowhack.module.client.CustomFont;
import me.notkronos.meowhack.util.Wrapper;

public class FontUtil implements Wrapper {
    public static int drawString(String text, float x, float y, int color) {
        return CustomFont.INSTANCE.isEnabled() ? CustomFont.getFontRenderer().drawString(text, x, y, color, false, CustomFont.antiAlias.getValue()) : mc.fontRenderer.drawString(text, (int) x, (int) y, color);
    }

    /**
     * Renders a given text with a shadow
     * @param text The given text
     * @param x The x position
     * @param y The y position
     * @param color The color of the text
     * @return The color of the text
     */
    public static int drawStringWithShadow(String text, float x, float y, int color) {
        return CustomFont.INSTANCE.isEnabled() ? CustomFont.getFontRenderer().drawStringWithShadow(text, x, y, color, CustomFont.antiAlias.getValue()) : mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    /**
     * Gets a given text's width
     * @param text The given text
     * @return The given text's width
     */
    public static int getStringWidth(String text) {
        return CustomFont.INSTANCE.isEnabled() ? CustomFont.getFontRenderer().getStringWidth(text) : mc.fontRenderer.getStringWidth(text);
    }

    /**
     * Gets the current font's height
     * @return The current font's height
     */
    public static float getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }

}
