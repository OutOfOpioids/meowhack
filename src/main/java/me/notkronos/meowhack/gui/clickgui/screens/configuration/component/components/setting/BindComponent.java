package me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.setting;


import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.ClickType;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.module.ModuleComponent;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScaled;
/**
 * @author linustouchtips, Surge
 * @since 02/02/2022
 */
public class BindComponent extends SettingComponent<Bind> {

    // feature offset
    private float featureHeight;

    // animation
    private int hoverAnimation;

    // binding state
    private boolean binding;

    public BindComponent(ModuleComponent moduleComponent, Setting<Bind> setting) {
        super(moduleComponent, setting);
    }

    @Override
    public void drawComponent() {
        super.drawComponent();

        // feature height
        featureHeight = getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + getModuleComponent().getSettingComponentOffset() + getModuleComponent().getCategoryFrameComponent().getScroll() + 2;

        // hover alpha animation
        if (isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT) && hoverAnimation < 25) {
            hoverAnimation += 5;
        }

        else if (!isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT) && hoverAnimation > 0) {
            hoverAnimation -= 5;
        }

        // feature background
        RenderUtil.drawRect(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT, new Color(12 + hoverAnimation, 12 + hoverAnimation, 17 + hoverAnimation, 255));

        glScaled(0.55, 0.55, 0.55); {

            // key name
            String keyName = getSetting().getValue().getButtonName();

            float scaledX = (getModuleComponent().getCategoryFrameComponent().getPosition().x + 6) * 1.81818181F;
            float scaledY = (featureHeight + 5) * 1.81818181F;
            float scaledWidth = (getModuleComponent().getCategoryFrameComponent().getPosition().x + getModuleComponent().getCategoryFrameComponent().getWidth() - (FontUtil.getStringWidth(binding ? "Listening ..." : keyName) * 0.55F) - 3) * 1.81818181F;

            // setting name
            FontUtil.drawString(getSetting().getName(), scaledX, scaledY, 0xffffffff);

            // bind value
            FontUtil.drawString(binding ? "Listening ..." : keyName, scaledWidth, scaledY, 0xffffffff);
        }

        glScaled(1.81818181, 1.81818181, 1.81818181);
    }

    @Override
    public void onClick(ClickType in) {
        // toggle the binding state if clicked
        if (in.equals(ClickType.LEFT) && isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT)) {

            // module feature bounds
            float highestPoint = featureHeight;
            float lowestPoint = highestPoint + HEIGHT;

            // check if it's able to be interacted with
            if (highestPoint >= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + 2 && lowestPoint <= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + getModuleComponent().getCategoryFrameComponent().getHeight() + 2) {
                binding = !binding;
            }


            return;
        }

        // Set mouse bind
        if (binding) {
            getSetting().setValue(new Bind(in.getIdentifier(), Bind.Device.MOUSE));

            binding = false;
        }
    }

    @Override
    public void onType(int in) {
        if (binding) {
            if (in != -1 && in != Keyboard.KEY_ESCAPE) {

                // backspace -> no bind
                if (in == Keyboard.KEY_BACK || in == Keyboard.KEY_DELETE) {
                    getSetting().setValue(new Bind(Keyboard.KEY_NONE, Bind.Device.KEYBOARD));
                }

                else {
                    getSetting().setValue(new Bind(in, Bind.Device.KEYBOARD));
                }

                // we are no longer binding
                binding = false;
            }
        }
    }
}
