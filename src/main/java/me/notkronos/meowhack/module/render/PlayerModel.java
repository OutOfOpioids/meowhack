package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class PlayerModel extends Module {
    public static PlayerModel INSTANCE;

    public PlayerModel() {
        super("PlayerModel", Category.RENDER, "Changes the player model", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = true;
        INSTANCE.enabled = false;
    }

    public static Setting<Boolean> limbAnimation = new Setting<>("LimbAnimation", true);
    public static Setting<Boolean> crouch = new Setting<>("Crouch", true);
    public static Setting<Float> limbSwing = new Setting<>("LimbSwing", 0.0f, 0.0f, 15.0f);
    public static Setting<Float> limbSwingAmount = new Setting<>("LimbSwingAmount", 0.0f, 0.0f, 15.0f);
}
