package me.notkronos.meowhack.module.movement;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class Sprint extends Module {
    public static Sprint INSTANCE;

    public Sprint() {
        super("Sprint", Category.MOVEMENT, "Automatically sprints", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    @Override
    public void onTick() {
        if (isEnabled()) {
            mc.player.setSprinting(true);  // Very primitive sprint module. Can refine later.
        }
    }
}