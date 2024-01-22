package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
import me.notkronos.meowhack.gui.clickgui.ClickGuiScreen;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ClickGUIModule extends Module {
    public static ClickGUIModule INSTANCE;

    public ClickGUIModule() {
        super("ClickGUI", Category.CLIENT, "This screen.", new String[] {"GUI", "UI"});
        INSTANCE = this;
        INSTANCE.drawn = false;
        getBind().setValue(new Bind(Keyboard.KEY_RCONTROL, Bind.Device.KEYBOARD));
    }

    Meowhack meowhack = Meowhack.INSTANCE;

    @Override
    public void onEnable() {
        mc.displayGuiScreen(ClickGuiScreen.getClickGui());
        this.setEnabled(false);
    }
}