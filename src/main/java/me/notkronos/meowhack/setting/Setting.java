package me.notkronos.meowhack.setting;

import me.notkronos.meowhack.module.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Setting<T> {
    public String name;

    public T value;

    private Module module;
    private final List<T> exclusions = new ArrayList<>();

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Setting(String name, T value, Integer min, Integer max) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value)
    {
            this.value = value;
    }


    public void setModule(Module in) {
        module = in;
    }

    public Module getModule() {
        return module;
    }

    public boolean isExclusion(T in) {
        return exclusions.contains(in);
    }

    @SafeVarargs
    public final Setting<T> setExclusion(T... in) {
        // add to our exclusion
        exclusions.addAll(Arrays.asList(in));

        // builder
        return this;
    }

    Integer max;
    Integer min;

    public void setMin(Integer in) {
        min = in;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer in) {
        max = in;
    }

    public int getRoundingScale() {
        return 1;
    }
}