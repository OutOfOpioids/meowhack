package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
import me.notkronos.meowhack.font.CustomFontManager;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class CustomFontMod extends Module
{

    public static CustomFontMod INSTANCE;

    public CustomFontMod() {
        super("CustomFont", Category.CLIENT, "Allows you to use custom fonts", new String[]{"CustomFont"});
        CustomFontMod.INSTANCE = this;
        this.enabled = false;
        this.drawn = false;
        Meowhack.EVENT_BUS.register(this);
    }

    public static Setting<Integer> fontStyle = new Setting<>("Font Style", 0, 0, 3);
    public static Setting<Integer> fontSize = new Setting<>("Font Size", 14, 8, 30);
    public static Setting<Boolean> antiAlias = new Setting<>("Anti Alias", true);
    public static Setting<Boolean> metrics = new Setting<>("Metrics", true);
    public static Setting<Boolean> shadow = new Setting<>("Shadow", true);
    public static Setting<Integer> fontOffset = new Setting<>("Font Offset", 0, -5, 5);

    @Override
    //TODO: optimize
    public void onRender2D() {
        CustomFontManager.customFont = new CustomFontRenderer(
                new Font("Verdana",
                        CustomFontMod.fontStyle.value,
                        CustomFontMod.fontSize.value),
                CustomFontMod.antiAlias.value,
                CustomFontMod.metrics.value
        );
    }
}