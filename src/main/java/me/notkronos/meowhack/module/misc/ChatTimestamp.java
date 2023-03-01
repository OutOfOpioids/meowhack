package me.notkronos.meowhack.module.misc;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class ChatTimestamp extends Module {
    public static ChatTimestamp INSTANCE;

    public ChatTimestamp() {
        super("ChatTimestamp", Category.MISC, "Adds timestamps before chat messages", new String[]{});
        INSTANCE = this;
        this.enabled = true;
        this.drawn = false;
    }

    public static Setting<Boolean> rainbow = new Setting<>("Rainbow", false);
}
