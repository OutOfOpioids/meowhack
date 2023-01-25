package me.notkronos.meowhack.module;

import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Module {
    protected boolean enabled;
    protected boolean drawn = true;

    private final Setting<Bind> bind = new Setting<Bind>("Bind", Bind.none());
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
        Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> Setting.class.isAssignableFrom(field.getType()))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        Setting<?> setting = ((Setting<?>) field.get(this));

                        // set the setting's current module as this module
                        setting.setModule(this);

                        // add it this module's settings
                        settings.add(setting);
                    } catch (IllegalArgumentException | IllegalAccessException exception) {
                        exception.printStackTrace();
                    }
                });

        // Add bind
        settings.add(bind);
        drawn = true;
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

    public void disable(boolean b) {
        enabled = false;
    }

    public Setting<Bind> getBind() {
        return bind;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean b) {
        drawn = true;
    }

    public Category getCategory() {
        return category;
    }

    public List<Setting<?>> getAllSettings() {
        return settings;
    }

    public List<Setting<?>> getSettings(Predicate<? super Setting<?>> predicate) {
        return settings.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Setting<?> getSetting(Predicate<? super Setting<?>> predicate) {
        return settings.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public boolean isActive() {
        return isEnabled();
    }

    public String getName() {
        return name;
    }

    public void onTick() {

    }
}
