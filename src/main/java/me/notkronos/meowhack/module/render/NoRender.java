package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class NoRender extends Module {
    public static NoRender INSTANCE;

    public NoRender() {
        super("NoRender", Category.RENDER, "Prevents certain things from rendering", new String[]{"NoRender"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    public static Setting<Boolean> noBossOverlay = new Setting<>("NoBossOverlay", true);
    public static Setting<Boolean> helmet = new Setting<>("Helmet", true);
    public static Setting<Boolean> chestplate = new Setting<>("Chestplate", true);
    public static Setting<Boolean> leggings = new Setting<>("Leggings", true);
    public static Setting<Boolean> boots = new Setting<>("Boots", true);
}
