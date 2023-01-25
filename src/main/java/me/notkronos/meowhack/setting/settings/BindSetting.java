package me.notkronos.meowhack.setting.settings;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;

public class BindSetting extends Setting<Bind>{

    public BindSetting(String name, Bind value) {
        super(name, value);
    }
}
