package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class Colors extends Module {
    public static Colors INSTANCE;

    public Colors() {
        super("Colors", Category.CLIENT, "Decides the color of the HUD", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = false;
    }

    //Settings
    public static Setting<Integer> red = new Setting<Integer>("red", 255, 0, 255);
    public static Setting<Integer> green = new Setting<Integer>("green", 128, 0, 255);
    public static Setting<Integer> blue = new Setting<Integer>("blue", 255, 0, 255);
    public static Setting<Boolean> HudPrimaryColor = new Setting<Boolean>("HUDColor", true);

    public Integer getRed() { return red.value; }
    public Integer getBlue() {
        return blue.value;
    }
    public Integer getGreen() {
        return green.value;
    }

    public boolean getHudPrimaryColor() {
        return HudPrimaryColor.getValue();
    }
}

