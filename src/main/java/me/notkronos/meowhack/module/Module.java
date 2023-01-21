package me.notkronos.meowhack.module;

import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;

public class Module {
    private final Setting<Bind> bind = new Setting<>("Bind", Bind.none());

    public Module(String name, Category category, String description, String[] alias) {
    }
}
