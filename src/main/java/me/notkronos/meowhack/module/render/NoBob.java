package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class NoBob extends Module {
    public static NoBob INSTANCE;

    public NoBob() {
        super("NoBob", Category.MOVEMENT, "Stops hand bob when walking", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    @Override
    public void onTick() {
        if (isEnabled()) {
            mc.gameSettings.viewBobbing = false;
        }
    }
}