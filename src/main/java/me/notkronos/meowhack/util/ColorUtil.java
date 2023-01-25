package me.notkronos.meowhack.util;

import me.notkronos.meowhack.module.client.Colors;

import java.awt.*;

public class ColorUtil {


    public static int[] getPrimaryColor() {
        return new int[]{Colors.red.value, Colors.green.value, Colors.blue.value};
    }

    public static int decimalToHex(int red, int green, int blue) {
        String hexString;
        String redHex = Integer.toHexString(red);
        String greenHex = Integer.toHexString(green);
        String blueHex = Integer.toHexString(blue);
        hexString = "0x" +
                redHex +
                greenHex +
                blueHex;
        return Integer.decode(hexString);
    }

    public Color hexToColor(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

}


