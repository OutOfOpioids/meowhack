package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.FramebufferWrapper;
import me.notkronos.meowhack.util.render.GlShader;

public class Shader extends Module {
    public static Shader INSTANCE;

    //Entity type Settings
    public static Setting<Boolean> self = new Setting<>("Self", true);
    public static Setting<Boolean> players = new Setting<>("Players", true);
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static Setting<Boolean> exp = new Setting<>("Exp", true);
    public static Setting<Boolean> pearls = new Setting<>("Pearls", true);
    public static Setting<Boolean> items = new Setting<>("Items", true);

    //Color Settings
    public static Setting<Integer> red = new Setting<>("Red", 0, 1, 255);
    public static Setting<Integer> green = new Setting<>("Green", 0, 1, 255);
    public static Setting<Integer> blue = new Setting<>("Blue", 0, 1, 255);
    public static Setting<Integer> alpha = new Setting<>("Alpha", 0, 1, 255);
    public static Setting<Boolean> global = new Setting<>("UseGlobalColor", false);

    //Shader Settings
    public static Setting<Float> radius = new Setting<>("Radius", 3.0f, 0.1f, 6.0f);
    public static Setting<Boolean> filled = new Setting<>("Filled", true);
    public static Setting<Float> blend = new Setting<>("FilledBlend", 0.25f, 0.1f, 1.0f);

    //Shader Stuff
    protected final GlShader shader = new GlShader("shader");
    protected final FramebufferWrapper fbWrapper = new FramebufferWrapper();
    protected boolean forceRender = false;

    public Shader() {
        super("Shader", Category.RENDER, "Alternative Chams module", new String[]{"ShaderChams"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }
}
