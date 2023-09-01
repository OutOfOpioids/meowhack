package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;

public class PopParticles extends Module {
    public static PopParticles INSTANCE;

    public PopParticles() {
        super("PopParticles", Category.RENDER, "Modifies the color of pop particles", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    public static Setting<Enum<ParticleType>> mode = new Setting<>("Mode", ParticleType.TRANS);
    public static Setting<Float> duration = new Setting<>("Duration", 1.0f, 0.1f, 2.5f);
    public static Setting<Integer> red = new Setting<>("Red", 59, 0, 255);
    public static Setting<Integer> green = new Setting<>("Green", 119, 0, 255);
    public static Setting<Integer> blue = new Setting<>("Blue", 200, 0, 255);

    public enum ParticleType {
        TRANS,
        CUSTOM
    }
}
