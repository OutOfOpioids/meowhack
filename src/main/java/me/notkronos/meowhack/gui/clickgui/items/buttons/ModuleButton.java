package me.notkronos.meowhack.gui.clickgui.items.buttons;

import me.notkronos.meowhack.gui.clickgui.items.Button;
import me.notkronos.meowhack.gui.clickgui.items.Item;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.ClickGUIModule;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ModuleButton extends Button {
    private final Module module;
    private List<Item> settings = new ArrayList<>();
    private boolean subOpen;

    public ModuleButton(Module module) {
        super(module.getName());
        this.module = module;
        if(module.getAllSettings().size() > 0) {
            for(Setting<?> setting : module.getAllSettings()) {
                if (setting.getValue() instanceof Bind) {
                    settings.add(new BindButton((Setting<Bind>) setting));
                }

                else if (setting.getValue() instanceof Boolean) {
                    settings.add(new BooleanButton((Setting<Boolean>) setting));
                }
                else if (setting.getValue() instanceof Integer) {
                    settings.add(new IntegerSlider((Setting<Integer>) setting));
                }
                else if (setting.getValue() instanceof Enum) {
                    settings.add(new EnumButton((Setting<Enum<?>>) setting));
                }
                else if (setting.getValue() instanceof Float) {
                    settings.add(new FloatSlider((Setting<Float>) setting));
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(settings.size() > 0) {
            if(subOpen) {
                float height = 1;
                for(Item item : settings) {
                    item.setLocation(this.x + 1.0f, this.y + (height += 15.0f));
                    item.setHeight(15);
                    item.setWidth(this.width - 9);
                    item.drawScreen(mouseX, mouseY, partialTicks);
                }
            }
        }
    }
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.settings.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
            if (this.subOpen) {
                for (Item item : settings) {
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (Item item : settings) {
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }

    @Override
    public void toggle() {
        if(!(module instanceof Colors)) {
            module.toggle();
            if(module.isEnabled()) {
                module.onEnable();
            } else {
                module.onDisable();
            }
        }
    }

    @Override
    public boolean getState() {
        if (!(module instanceof Colors)) {
            return module.isEnabled();
        }
        return true;
    }

    @Override
    public void onType(int in) {
        settings.forEach(item -> item.onType(in));
    }
}
