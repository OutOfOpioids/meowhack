package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class SwingSpeed extends Module {
    public static SwingSpeed INSTANCE;

    public SwingSpeed() {
        super("SwingSpeed", Category.RENDER, "Allows you to customize hand swing animation.", new String[]{"SwingAnimation"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }

    //Mode Settings

    public static Setting<Enum<Mode>> mode = new Setting<>("Mode", Mode.SELF);

    //Swing Settings
    public static Setting<Integer> speed = new Setting<>("Speed", 16, -1, 32);

    public boolean isCausedByRClick = false;

    public enum Mode {
        SELF,
        OTHERS,
        BOTH
    }
}
