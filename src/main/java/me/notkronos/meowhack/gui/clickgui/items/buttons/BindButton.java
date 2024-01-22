package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.gui.clickgui.items.Button;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

public class BindButton extends Button {
    private Setting<Bind> bindSetting;
    private boolean isListening;

    public BindButton(Setting<Bind> bind) {
        super(bind.name);
        this.bindSetting = bind;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height, this.getState() ? -14540254 : -15658735);
        FontUtil.drawStringWithShadow(isListening ? "Listening..." : this.bindSetting.getValue().getButtonName(), this.x + 2.3f, this.y + 2.7f - (float) FontUtil.getFontHeight() / 2 + (float) this.height / 2.0f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            isListening = !isListening;
        }
    }

    @Override
    public void onType(int in) {
        if(!isListening) return;
        if(in != -1 && in != Keyboard.KEY_ESCAPE) {
            if(in == Keyboard.KEY_DELETE) {
                this.bindSetting.setValue(new Bind(Keyboard.KEY_NONE, Bind.Device.KEYBOARD));
            } else {
                this.bindSetting.setValue(new Bind(in, Bind.Device.KEYBOARD));
            }
        }
        isListening = false;
    }
}
