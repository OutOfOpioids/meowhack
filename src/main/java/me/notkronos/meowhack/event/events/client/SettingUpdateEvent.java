package me.notkronos.meowhack.event.events.client;

import me.notkronos.meowhack.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SettingUpdateEvent extends Event {

    private final Setting<?> setting;

    public SettingUpdateEvent(Setting<?> setting) {
        this.setting = setting;
    }

    public Setting<?> getSetting() {
        return setting;
    }
}