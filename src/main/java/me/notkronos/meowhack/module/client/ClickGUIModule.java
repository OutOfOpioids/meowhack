package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
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

    //Settings
    public static Setting<Boolean> pauseGame = new Setting<>("PauseGame", false);
    public static Setting<Boolean> blur = new Setting<>("Blur", false);

    @Override
    public void onTick() {

        // custom toggling
        if (isEnabled() && mc.currentScreen == null) {

            // open gui
            mc.displayGuiScreen(meowhack.getClickGUI());
            mc.currentScreen = meowhack.getClickGUI();
            Meowhack.EVENT_BUS.register(meowhack.getClickGUI());

            // open frames
            meowhack.getClickGUI().getCategoryFrameComponents().forEach(categoryFrameComponent -> {
                categoryFrameComponent.setOpen(true);
            });

            // blur shader for background
            if (blur.getValue()) {
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
    }

    @SubscribeEvent
    public void onSettingEnable(SettingUpdateEvent event) {
        if (event.getSetting().equals(blur)) {

            // blur shader for background
            if (blur.getValue()) {
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }

            else if (mc.entityRenderer.isShaderActive()) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
}