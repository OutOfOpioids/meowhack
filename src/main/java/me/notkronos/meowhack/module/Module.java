package me.notkronos.meowhack.module;

import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import java.util.ArrayList;
import java.util.List;

public class Module {
    protected boolean enabled;
    protected boolean drawn = true;

    private final Setting<Bind> bind = new Setting<>("Bind", Bind.none());
    private final String name;
    private final Category category;
    private final String description;
    private final String[] alias;
    private final List<Setting<?>> settings = new ArrayList<>();

    public Module(String name, Category category, String description, String[] alias) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.alias = alias;
    }

    public void toggle() {
        enabled = !enabled;
    }

    public void onRender2D() {

    }
    public boolean isEnabled() {
        return enabled;
    }

    public void onThread() {
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public Setting<Bind> getBind() {
        return bind;
    }
}
