package me.notkronos.meowhack.util.render;

import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.util.Wrapper;

import java.awt.*;

public class FontUtil implements Wrapper {
    public static CustomFontRenderer customFont = new CustomFontRenderer(new Font("Verdana", 0, 14), true, true);

    public static float drawStringWithShadow(final String text, final double x, final double y, final int color) {
        if(CustomFontMod.INSTANCE.isEnabled()) {
            customFont.drawStringWithShadow(text, x, y - CustomFontMod.fontOffset.getValue(), color);
        } else {
            return mc.fontRenderer.drawStringWithShadow(text, (float) x, (float) y, color);
        }
        return 0;
    }

    public static float drawString(final String text, final float x, final float y, final int color) {
        if (CustomFontMod.INSTANCE.isEnabled()) {
            return customFont.drawString(text, x, y - CustomFontMod.fontOffset.getValue(), color);
        } else {
            return mc.fontRenderer.drawString(text, (int) x, (int) y, color);
        }
    }

    public static float drawString(final String text, final double x, final double y, final int color, final boolean shadow) {
        if (CustomFontMod.INSTANCE.isEnabled()) {
            return customFont.drawString(text, x, y, color, shadow);
        } else {
            return mc.fontRenderer.drawString(text, (int) x, (int)y, color, shadow);
        }
    }

    public static float getFontHeight() {
        if (CustomFontMod.INSTANCE.isEnabled()) {
            return (float)customFont.getHeight();
        }
        return mc.fontRenderer.FONT_HEIGHT;
    }

    public static int getStringWidth(final String text) {
        if (CustomFontMod.INSTANCE.isEnabled()) {
            return customFont.getStringWidth(text);
        }
        return mc.fontRenderer.getStringWidth(text);
    }

}
