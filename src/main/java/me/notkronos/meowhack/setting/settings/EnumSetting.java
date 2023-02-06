package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.EnumUtil;

public class EnumSetting<E extends Enum<E>> extends Setting<E> {
    private final String concatenated;
    public EnumSetting(String name, E value) {
        super(name, value);
        concatenated = concatenateInputs();
    }

    public String getInputs(String string) {
        if(string == null || string.isEmpty()) {
            return concatenated;
        }
        Enum<?> entry = EnumUtil.getEnumStartingWith(string, value.getDeclaringClass());
        return entry == null ? "" : entry.toString();
    }

    private String concatenateInputs() {
        StringBuilder concatenated = new StringBuilder("<");
        Class<? extends Enum<?>> clazz = this.value.getDeclaringClass();
        for(Enum<?> entry : clazz.getEnumConstants()) {
            concatenated.append(entry.name()).append(", ");
        }
        concatenated.replace(concatenated.length() - 2, concatenated.length(), ">");
        return concatenated.toString();
    }
}
