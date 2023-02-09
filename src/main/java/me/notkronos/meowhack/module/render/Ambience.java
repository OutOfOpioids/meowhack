package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Ambience extends Module {
    public static Ambience INSTANCE;

    public Ambience() {
        super("Ambience", Category.RENDER, "Changes sky color.", new String[]{"SkyColor", "Environment"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    // Settings
    public static Setting<Integer> red = new Setting<>("Red", 0, 0, 255);
    public static Setting<Integer> green = new Setting<>("Green", 0, 0, 255);
    public static Setting<Integer> blue = new Setting<>("Blue", 0, 0, 255);
    public static Setting<Boolean> fog = new Setting<Boolean>("Fog", false);
    public static Setting<Boolean> useGlobalColor = new Setting<>("GlobalColor", true);

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        if(useGlobalColor.getValue()) {
            setFogColors(event, Colors.red.getValue(), Colors.green.getValue(), Colors.blue.getValue());
        } else {
            setFogColors(event, red.getValue(), green.getValue(), blue.getValue());
        }
    }
    @SubscribeEvent
    public void fog_density(EntityViewRenderEvent.FogDensity event) {
        if (fog.getValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {
        Meowhack.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        Meowhack.EVENT_BUS.unregister(this);
    }

    public void setFogColors(EntityViewRenderEvent.FogColors event, int r, int g, int b) {
        event.setRed(r / 255.0f);
        event.setGreen(g / 255.0f);
        event.setBlue(b / 255.0f);
    }
}
