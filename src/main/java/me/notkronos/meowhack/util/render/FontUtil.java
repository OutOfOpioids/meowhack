package me.notkronos.meowhack.util.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.util.Wrapper;

public class FontUtil implements Wrapper {
    public static int drawStringWithShadow(String text, float x, float y, int color) {
        return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }
    public static float getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }
}
