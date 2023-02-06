package me.notkronos.meowhack.util;

import me.notkronos.meowhack.module.client.Colors;

import java.awt.*;

public class ColorUtil {


    public static int[] getPrimaryColor() {
        return new int[]{Colors.red.value, Colors.green.value, Colors.blue.value};
    }

    public static int decimalToHex(int red, int green, int blue) {
        String hexString;
        String redHex = red < 10 ? "0" + Integer.toHexString(red) : Integer.toHexString(red);
        String greenHex = blue < 10 ? "0" + Integer.toHexString(green) : Integer.toHexString(green);
        String blueHex = green < 10 ? "0" + Integer.toHexString(blue) : Integer.toHexString(blue);
        hexString = "0x" +
                redHex +
                greenHex +
                blueHex;
        return Integer.decode(hexString);
    }

    public Color hexToColor(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

    public static int toRGBA(double r, double g, double b, double a) {
        return ColorUtil.toRGBA((float) r, (float) g, (float) b, (float) a);
    }

    public static int toRGBA(int r, int g, int b) {
        return ColorUtil.toRGBA(r, g, b, 255);
    }

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }
    public static int toRGBA(float r, float g, float b, float a) {
        return ColorUtil.toRGBA((int) (r * 255.0f), (int) (g * 255.0f), (int) (b * 255.0f), (int) (a * 255.0f));
    }

    public static int staticRainbow(float offset, Color color) {
        double timer = System.currentTimeMillis() % 1750.0 / 850.0;
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = (float) (hsb[2] * Math.abs((offset + timer) % 1f - 0.55f) + 0.45f);
        return Color.HSBtoRGB(hsb[0], hsb[1], brightness);
    }

    public static Color getRainbow(int speed, int offset, float s, float brightness) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, s, brightness);
    }
}




