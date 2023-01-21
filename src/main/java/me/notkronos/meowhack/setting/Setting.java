package me.notkronos.meowhack.setting;

public class Setting<T> {
    public String name;

    public T value;

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
