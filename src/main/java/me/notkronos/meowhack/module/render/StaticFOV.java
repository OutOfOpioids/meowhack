package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class StaticFOV extends Module {
    public static StaticFOV INSTANCE;

    public StaticFOV() {
        super("StaticFOV", Category.RENDER, "StaticFOV", new String[]{"fov", "nodynamicfov"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    public static Setting<Float> fovModifier = new Setting<>("fovModifier", 1.0f, 0.1f, 2.0f);
}
