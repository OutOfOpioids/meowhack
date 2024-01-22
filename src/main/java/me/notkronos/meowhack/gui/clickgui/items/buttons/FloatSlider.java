package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.gui.clickgui.ClickGuiScreen;
import me.notkronos.meowhack.gui.clickgui.Frame;
import me.notkronos.meowhack.gui.clickgui.items.Item;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.MathUtil;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

public class FloatSlider extends Item {
    private Setting<Float> setting;
    private Float min;
    private Float max;
    private float difference;

    public FloatSlider(Setting<Float> setting) {
        super(setting.getName());
        this.setting = setting;
        this.min = setting.getFmin();
        this.max = setting.getFmax();
        this.difference = max - min;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        dragSetting(mouseX, mouseY);
        RenderUtil.drawRect(x, y, setting.getValue() <= min.floatValue() ? x : x + (width + 7.4F) * partialMultiplier(), y + height - 0.5f, color);
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
        float result = setting.getFmin() + (difference * percent);
        float round = Math.round(10.0f * result) / 10.0f;
        setting.setValue(MathHelper.clamp(round, setting.getFmin(), setting.getFmax()));
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
        return this.setting.getFmax() - this.setting.getFmin() + this.setting.getValue();
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
