package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;

public class BindSetting extends Setting<Bind>{

    public void setKey(int key) {
        this.value = Bind.fromKey(key);
    }
}
