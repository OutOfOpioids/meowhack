package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.gui.clickgui.items.Button;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;

import java.awt.*;

public class BooleanButton extends Button {
    private Setting<Boolean> setting;

    public BooleanButton(Setting<Boolean> setting) {
        super(setting.name);
        this.setting = setting;
        this.width = 15;
    }

    //:troll:
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height, this.getState() ? color : -15658735);
        FontUtil.drawStringWithShadow(this.getName(), this.x + 2.0f, this.y + 4.0f, this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
//            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("random.click"), 1.0f));
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
        this.setting.setValue(!(Boolean)this.setting.getValue());
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
    }

    @Override
    public boolean getState() {
        return (Boolean)this.setting.getValue();
    }
}
