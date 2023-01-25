package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;

public class IntegerSetting  extends Setting<Integer> {
    public IntegerSetting(String name, Integer value, Integer min, Integer max) {
        super(name, value, min, max);
    }
}
