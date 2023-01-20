package me.notkronos.meowhack.setting;

public class Setting<T> {
    public String name;

    public T value;

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
