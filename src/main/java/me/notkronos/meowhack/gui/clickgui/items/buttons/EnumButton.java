package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.gui.clickgui.items.Button;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;

public class EnumButton extends Button {
    private Setting<Enum<?>> setting;

    public EnumButton(Setting<Enum<?>> setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height, this.getState() ? -14540254 : -15658735);
        FontUtil.drawStringWithShadow(this.setting.getName() + ": " + this.setting.value.name(), this.x + 2.0f, this.y + 4.0f, this.getState() ? -1 : -5592406);
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.setting.setValue(this.setting.getNextMode());
            } else if (mouseButton == 1) {
                this.setting.setValue(this.setting.getLastMode());
            }
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
    }

    @Override
    public boolean getState() {
        return true;
    }
}
