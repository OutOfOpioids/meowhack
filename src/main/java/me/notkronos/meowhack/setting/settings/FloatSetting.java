package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;

public class FloatSetting  extends Setting<Float> {
    public FloatSetting(String name, Float value, Integer min, Integer max) {
        super(name, value, min, max);
    }
}
