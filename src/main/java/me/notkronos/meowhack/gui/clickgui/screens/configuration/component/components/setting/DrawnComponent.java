package me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.setting;

import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.ClickType;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.module.ModuleComponent;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.setting.SettingComponent;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.opengl.GL11.glScaled;

/**
 * @author linustouchtips
 * @since 02/02/2022
 */
public class DrawnComponent extends SettingComponent<AtomicBoolean> {

    // feature offset
    private float featureHeight;

    // animation
    private int hoverAnimation;

    public DrawnComponent(ModuleComponent moduleComponent, Setting<AtomicBoolean> setting) {
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
            float scaledX = (getModuleComponent().getCategoryFrameComponent().getPosition().x + 6) * 1.81818181F;
            float scaledY = (featureHeight + 5) * 1.81818181F;
            float scaledWidth = (getModuleComponent().getCategoryFrameComponent().getPosition().x + getModuleComponent().getCategoryFrameComponent().getWidth() - (FontUtil.getStringWidth(String.valueOf(getModuleComponent().getModule().isDrawn())) * 0.55F) - 3) * 1.81818181F;

            // drawn name
            FontUtil.drawStringWithShadow(getSetting().getName(), scaledX, scaledY, -1);

            // drawn value
            FontUtil.drawStringWithShadow(String.valueOf(getModuleComponent().getModule()), scaledWidth, scaledY, -1);
        }

        glScaled(1.81818181, 1.81818181, 1.81818181);
    }

    @Override
    public void onClick(ClickType in) {
        // toggle the drawn state if clicked
        if (in.equals(ClickType.LEFT) && isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT)) {

            // module feature bounds
            float highestPoint = featureHeight;
            float lowestPoint = highestPoint + HEIGHT;

            // check if it's able to be interacted with
            if (highestPoint >= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + 2 && lowestPoint <= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + getModuleComponent().getCategoryFrameComponent().getHeight() + 2) {
                boolean previousDrawn = getModuleComponent().getModule().isDrawn();

                // update values
                getModuleComponent().getModule().setDrawn(!previousDrawn);
            }
        }
    }
}
