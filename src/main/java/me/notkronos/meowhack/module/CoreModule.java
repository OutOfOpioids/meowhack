package me.notkronos.meowhack.module;

import me.notkronos.meowhack.setting.Setting;
import java.util.ArrayList;
import java.util.List;

public class CoreModule {
    protected boolean drawn = true;
    private final List<Setting<?>> settings = new ArrayList<>();

    public CoreModule(String name, Category category, String description) {
    }
}
