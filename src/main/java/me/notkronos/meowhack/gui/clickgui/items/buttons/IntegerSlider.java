package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.gui.clickgui.ClickGuiScreen;
import me.notkronos.meowhack.gui.clickgui.Frame;
import me.notkronos.meowhack.gui.clickgui.items.Item;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.ColorUtil;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class IntegerSlider extends Item {
    private Setting<Integer> setting;
    private Integer min;
    private Integer max;
    private int difference;

    public IntegerSlider(Setting<Integer> setting) {
        super(setting.getName());
        this.setting = setting;
        this.min = setting.getMin();
        this.max = setting.getMax();
        this.difference = max - min;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        dragSetting(mouseX, mouseY);
        RenderUtil.drawRect(x, y, setting.getValue() <= min ? x : x + (width + 7.4F) * partialMultiplier(), y + height - 0.5f, color);
        FontUtil.drawStringWithShadow(String.format("%s\u00a77 %s", this.getName(), this.setting.getValue()), this.x + 2.0f, this.y + 4.0f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            setSettingFromX(mouseX);
        }
    }

    private void setSettingFromX(int mouseX) {
        float percent = (mouseX - x) / (width + 7.4F);
        int result = setting.getMin() + (int)(difference * percent);
        setting.setValue(MathHelper.clamp(result, setting.getMin(), setting.getMax()));
        if(setting.getModule() == CustomFontMod.INSTANCE && CustomFontMod.INSTANCE.isEnabled()) {
            FontUtil.customFont = new CustomFontRenderer(
                    new Font(me.notkronos.meowhack.command.commands.Font.fontName,
                        CustomFontMod.fontStyle.value,
                        CustomFontMod.fontSize.value
                    ),
                    CustomFontMod.antiAlias.value,
                    CustomFontMod.metrics.value
            );
        }
        if (setting.getModule() == Colors.INSTANCE) {
            int color = ColorUtil.decimalToHex(ColorUtil.getPrimaryColor()[0], ColorUtil.getPrimaryColor()[1], ColorUtil.getPrimaryColor()[2]);
            Frame.color = ColorUtil.addAlpha(color, 255);
            Item.color = ColorUtil.addAlpha(color, 255);
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    private void dragSetting(int mouseX, int mouseY) {
        if(isHovering(mouseX, mouseY) && Mouse.isButtonDown(0)) {
            setSettingFromX(mouseX);
        }
    }

    private boolean isHovering(int mouseX, int mouseY) {
        for (Frame frame : ClickGuiScreen.getClickGui().getFrames()) {
            if (!frame.dragging) continue;
            return false;
        }
        return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() + 10f && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
    }

    private float getValueWidth() {
        return this.setting.getMax() - this.setting.getMin() + this.setting.getValue();
    }

    private float middle() {
        return max.floatValue() - min;
    }

    private float part() {
        return setting.getValue() - min;
    }

    private float partialMultiplier() {
        return part() / middle();
    }
}
