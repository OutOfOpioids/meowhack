package me.notkronos.meowhack.setting;

import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.EnumUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Setting<T> {

    public String name;
    public T value;

    Integer max;
    Integer min;
    Float fmax;
    Float fmin;
    private int index;

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

    public Setting(String name, T value, Float fmin, Float fmax) {
        this.name = name;
        this.value = value;
        this.fmin = fmin;
        this.fmax = fmax;
    }

    @SafeVarargs
    public final Setting<T> setExclusion(T... in) {
        // add to our exclusion
        exclusions.addAll(Arrays.asList(in));

        // builder
        return this;
    }

    @SuppressWarnings("unchecked")
    public T getNextMode() {
        if (value instanceof Enum<?>) {
            Enum<?> enumVal = (Enum<?>) value;

            // search all values
            String[] values = Arrays.stream(enumVal.getClass().getEnumConstants()).filter(in -> !isExclusion((T) in)).map(Enum::name).toArray(String[]::new);
            index = index + 1 > values.length - 1 ? 0 : index + 1;

            // use value index
            return (T) Enum.valueOf(enumVal.getClass(), values[index]);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public T getLastMode() {
        if (value instanceof Enum<?>) {
            Enum<?> enumVal = (Enum<?>) value;

            // search all values
            String[] values = Arrays.stream(enumVal.getClass().getEnumConstants()).filter(in -> !isExclusion((T) in)).map(Enum::name).toArray(String[]::new);
            index = index - 1 < 0 ? values.length - 1 : index - 1;

            // use value index
            return (T) Enum.valueOf(enumVal.getClass(), values[index]);
        }

        return null;
    }

    public boolean isExclusion(T in) {
        return exclusions.contains(in);
    }
    public int getRoundingScale() {
        return 1;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
    public void setModule(Module in) {
        module = in;
    }
    public void setMin(Integer in) {
        min = in;
    }
    public void setMax(Integer in) {
        max = in;
    }

    public String getName() {
        return name;
    }
    public T getValue() {
        return value;
    }
    public Integer getMin() {
        return min;
    }
    public Float getFmin() { return fmin; }
    public Integer getMax() {
        return max;
    }
    public Float getFmax() { return fmax; }

    public Module getModule() {
        return module;
    }

    public void setValueFromString(String in) {
        this.value = (T) EnumUtil.fromString((Enum<?>) value, in);
    }
}
