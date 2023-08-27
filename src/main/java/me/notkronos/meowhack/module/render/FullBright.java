package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class FullBright extends Module {
    public static FullBright INSTANCE;

    public FullBright() {
        super("FullBright", Category.RENDER, "Allows you to see in the dark", new String[]{"Brightness"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }

    @Override
    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
