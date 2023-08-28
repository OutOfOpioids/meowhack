package me.notkronos.meowhack.util.render;

import me.notkronos.meowhack.font.CustomFontManager;
import me.notkronos.meowhack.util.Wrapper;

public class FontUtil implements Wrapper {
    public static float drawString(String text, float x, float y, int color) {
        return CustomFontManager.drawString(text, x, y, color);
    }

    /**
     * Renders a given text with a shadow
     * @param text The given text
     * @param x The x position
     * @param y The y position
     * @param color The color of the text
     * @return The color of the text
     */
    public static float drawStringWithShadow(String text, float x, float y, int color) {
       return CustomFontManager.drawStringWithShadow(text, x, y, color);
    }

    /**
     * Gets a given text's width
     * @param text The given text
     * @return The given text's width
     */
    public static int getStringWidth(String text) {
        return CustomFontManager.getStringWidth(text);
    }

    /**
     * Gets the current font's height
     * @return The current font's height
     */
    public static float getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }

}
