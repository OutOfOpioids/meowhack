package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.CoreModule;
import me.notkronos.meowhack.setting.Setting;

public class Colors extends CoreModule {
    public static Colors INSTANCE;

    public Colors() {
        super("Colors", Category.CLIENT, "Decides the color of the HUD");
        INSTANCE = this;
        INSTANCE.drawn = false;
    }

    //Settings
    public static Setting<Integer> red = new Setting<Integer>("red", 255);
    public static Setting<Integer> green = new Setting<Integer>("red", 128);
    public static Setting<Integer> blue = new Setting<Integer>("red", 255);
    public static Setting<Boolean> HudPrimaryColor = new Setting<Boolean>("HUDColor", true);

    public Integer getRed() {
        return red.value;
    }
    public Integer getBlue() {
        return blue.value;
    }
    public Integer getGreen() {
        return green.value;
    }

    public String getColorHexString(Integer red, Integer green, Integer blue) {
        String redHex = Integer.toHexString(red);
        String greenHex = Integer.toHexString(green);
        String blueHex = Integer.toHexString(blue);
        return "0x" +
                redHex +
                greenHex +
                blueHex;
    }
}

