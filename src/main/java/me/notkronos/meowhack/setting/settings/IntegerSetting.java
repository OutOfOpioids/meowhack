package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;

public class IntegerSetting  extends Setting<Float> {
    public IntegerSetting(String name, Float value, Float fmin, Float fmax) {
        super(name, value, fmin, fmax);
    }
}
