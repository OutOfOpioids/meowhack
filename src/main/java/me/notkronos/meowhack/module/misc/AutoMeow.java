package me.notkronos.meowhack.module.misc;


import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;

import static me.notkronos.meowhack.util.Wrapper.mc;


public class AutoMeow extends Module {
    public static AutoMeow INSTANCE;
    private long lastMeow = 0;


    public AutoMeow() {
        super("AutoMeow", Category.MISC, "Meows so you don't have to!", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = true;
        INSTANCE.drawn = true;

    }

    @Override
    public void onTick() {
        if (isEnabled()) {
            int time = 30; // set time in seconds
            long tick = mc.world.getTotalWorldTime();
            if (tick - lastMeow >= 20 * time) {           // stops it from meowing twice
                mc.player.sendChatMessage("meow");
                lastMeow = tick;
            }
        }
    }
}