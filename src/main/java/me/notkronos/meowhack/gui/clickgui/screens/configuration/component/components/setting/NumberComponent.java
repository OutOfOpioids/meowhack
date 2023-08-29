package me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.setting;

import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.module.ModuleComponent;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.MathUtil;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScaled;

/**
 * @author linustouchtips, Surge
 * @since 02/02/2022
 */
public class NumberComponent extends SettingComponent<Integer> {

    // feature offset
    private float featureHeight;

    // animation
    private int hoverAnimation;

    public NumberComponent(ModuleComponent moduleComponent, Setting<Integer> setting) {
        super(moduleComponent, setting);

        // slider feature height is 20
        HEIGHT = 20;
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
            float scaledWidth = (getModuleComponent().getCategoryFrameComponent().getPosition().x + getModuleComponent().getCategoryFrameComponent().getWidth() - (FontUtil.getStringWidth(String.valueOf(getSetting().getValue())) * 0.55F) - 3) * 1.81818181F;

            // drawn name
            FontUtil.drawString(getSetting().getName(), scaledX, scaledY, 0xffffffff);

            // drawn value
            FontUtil.drawString(String.valueOf(getSetting().getValue()), scaledWidth, scaledY, 0xffffffff);
        }

        glScaled(1.81818181, 1.81818181, 1.81818181);

        // module feature bounds
        float highestPoint = featureHeight;
        float lowestPoint = highestPoint + HEIGHT;

        // check if it's able to be interacted with
        if (highestPoint >= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + 2 && lowestPoint <= getModuleComponent().getCategoryFrameComponent().getPosition().y + getModuleComponent().getCategoryFrameComponent().getTitle() + getModuleComponent().getCategoryFrameComponent().getHeight() + 2) {
            if (getMouse().isLeftHeld()) {
                if (isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight + 13, getModuleComponent().getCategoryFrameComponent().getWidth(), HEIGHT - 13)) {
                    // the percentage of the slider that is filled
                    float percentFilled = ((getMouse().getPosition().x - getModuleComponent().getCategoryFrameComponent().getPosition().x) * 130 / ((getModuleComponent().getCategoryFrameComponent().getPosition().x + (getModuleComponent().getCategoryFrameComponent().getWidth() - 6)) - getModuleComponent().getCategoryFrameComponent().getPosition().x));

                    int max = getSetting().getMax();
                    int min = getSetting().getMin();

                    // set the value based on the type
                    if (getSetting().getValue() instanceof Integer) {
                        double valueSlid = MathHelper.clamp(MathUtil.roundDouble(percentFilled * ((max - min) / 130.0D) + min, getSetting().getRoundingScale()), min, max);

                        // exclude number
                        if (getSetting().isExclusion((int) valueSlid)) {
                            getSetting().setValue((int) valueSlid);
                            getSetting().setValue((int) (valueSlid + Math.pow(1, -getSetting().getRoundingScale())));
                        }
                        else {
                            getSetting().setValue((int) valueSlid);
                        }
                    }
                }

                // if less than min, setting is min
                else if (isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x, featureHeight + 13, 5, HEIGHT - 13)) {
                    getSetting().setValue(getSetting().getMin());
                }

                // if greater than max, setting is max
                else if (isMouseOver(getModuleComponent().getCategoryFrameComponent().getPosition().x + (getModuleComponent().getCategoryFrameComponent().getWidth() - 5), featureHeight + 13, 5, HEIGHT - 13)) {
                    getSetting().setValue(getSetting().getMax());
                }
            }
        }

        // slider length
        float sliderWidth = 91 * (getSetting().getValue().floatValue() - getSetting().getMin().floatValue()) / (getSetting().getMax().floatValue() - getSetting().getMin().floatValue());

        // clamp
        if (sliderWidth < 2) {
            sliderWidth = 2;
        }

        if (sliderWidth > 91) {
            sliderWidth = 91;
        }

        // slider
        RenderUtil.drawRoundedRect(getModuleComponent().getCategoryFrameComponent().getPosition().x + 6, featureHeight + 14, getModuleComponent().getCategoryFrameComponent().getWidth() - 10, 3, 2, new Color(23 + hoverAnimation, 23 + hoverAnimation, 29 + hoverAnimation, 255));

        if (getSetting().getValue().doubleValue() > getSetting().getMin().doubleValue()) {
            RenderUtil.drawRoundedRect(getModuleComponent().getCategoryFrameComponent().getPosition().x + 6, featureHeight + 14, sliderWidth, 3, 2, Color.pink);
        }

        // RenderUtil.drawPolygon(getModuleComponent().getCategoryFrameComponent().getPosition().x + 4 + sliderWidth, featureHeight + 15.5, 2, 360, ColorUtil.getPrimaryColor());
    }
}
