package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;

public class BooleanSetting extends Setting<Boolean> {
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    public BooleanSetting(String name, Boolean value) {
        super(name, value);
    }
}
