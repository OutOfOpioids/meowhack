package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.FontUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.text.TextFormatting;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class HUD extends Module {
    public static HUD INSTANCE;

    public HUD() {
        super("HUD", Category.CLIENT, "HUD of the client", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = false;
        INSTANCE.enabled = true;
    }

    //Settings
    public static Setting<Boolean> watermark = new Setting<>("Watermark", true);


    private static final float ELEMENT = FontUtil.getFontHeight() + 1;

    @Override
    public void onRender2D() {
        ScaledResolution resolution = new ScaledResolution(mc);
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        float topLeft = 2;
        float topRight = 2;
        float bottomLeft = height - ELEMENT;
        float bottomRight = height - ELEMENT;

        StringBuilder watermarkS = null;
        if (watermark.getValue()) {
            watermarkS = new StringBuilder()
                    .append(Meowhack.NAME)
                    .append(" v.")
                    .append(Meowhack.VERSION);
        }
        assert watermarkS != null;
        FontUtil.drawStringWithShadow(watermarkS.toString(), 2, topLeft, 0xff80ff);
        topLeft += ELEMENT;
    }
}
