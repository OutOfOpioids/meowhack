package me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.module;

import me.notkronos.meowhack.gui.clickgui.screens.DrawableComponent;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.ClickType;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.category.CategoryFrameComponent;
import me.notkronos.meowhack.gui.clickgui.screens.configuration.component.components.setting.*;
import me.notkronos.meowhack.gui.util.animation.Animation;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.ColorUtil;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.glScaled;

/**
 * @author linustouchtips
 * @since 01/29/2022
 */
public class ModuleComponent extends DrawableComponent {

    int[] colors = ColorUtil.getPrimaryColor();
    int primaryColor = ColorUtil.decimalToHex(colors[0], colors[1], colors[2]);

    String[] coreModules = new String[]{"Colors", "ClickGUI"};

    // immutable module feature traits
    public static final int HEIGHT = 14;

    // parent frame
    private final CategoryFrameComponent categoryFrameComponent;
    private float featureHeight;

    // module associated with this feature
    private final Module module;

    // setting features under this module feature
    private final List<SettingComponent<?>> settingComponents = new ArrayList<>();

    // offset for setting features (don't have the animation applied to them)
    private float settingComponentOffset;

    // hover animation
    private final Animation animation = new Animation(300, false);
    private int hoverAnimation;

    // open/close state
    private boolean open;

    @SuppressWarnings("unchecked")
    public ModuleComponent(CategoryFrameComponent categoryFrameComponent, Module module) {
        this.categoryFrameComponent = categoryFrameComponent;
        this.module = module;

        if (module != null) {

            // add all module's settings
            module.getAllSettings().forEach(setting -> {
                if (setting.getValue() instanceof Bind) {
                    settingComponents.add(new BindComponent(this, (Setting<Bind>) setting));
                }

                else if (setting.getValue() instanceof Boolean) {
                    settingComponents.add(new BooleanComponent(this, (Setting<Boolean>) setting));
                }
                else if (setting.getValue() instanceof Integer) {
                    settingComponents.add(new NumberComponent(this, (Setting<Integer>) setting));
                }
                else if (setting.getValue() instanceof Enum) {
                    settingComponents.add(new EnumComponent(this, (Setting<Enum<?>>) setting));
                }
            });
        }
    }

    @Override
    public void drawComponent() {
        // feature height
        featureHeight = (float) (categoryFrameComponent.getPosition().y + categoryFrameComponent.getTitle() + categoryFrameComponent.getComponentOffset() + categoryFrameComponent.getScroll() + 2);

        if (module != null) {

            // hover alpha animation
            if (isMouseOver(categoryFrameComponent.getPosition().x, featureHeight, categoryFrameComponent.getWidth(), HEIGHT) && hoverAnimation < 25 && !categoryFrameComponent.isExpanding()) {
                hoverAnimation += 5;
            }

            else if (!isMouseOver(categoryFrameComponent.getPosition().x, featureHeight, categoryFrameComponent.getWidth(), HEIGHT) && hoverAnimation > 0) {
                hoverAnimation -= 5;
            }
        }

        // offset to account for this feature
        categoryFrameComponent.addComponentOffset(HEIGHT);

        if (module != null) {

            // draw all setting features
            if (animation.getAnimationFactor() > 0) {
                settingComponentOffset = (int) categoryFrameComponent.getComponentOffset();
                settingComponents.forEach(settingComponent -> {
                        settingComponent.drawComponent();

                        // add offset with animation factor accounted for
                        settingComponentOffset += settingComponent.getHeight();
                        categoryFrameComponent.addComponentOffset(settingComponent.getHeight() * animation.getAnimationFactor());
                });
            }
        }

        // module background
        RenderUtil.drawRect(categoryFrameComponent.getPosition().x, featureHeight, categoryFrameComponent.getWidth(), HEIGHT, new Color(23 + hoverAnimation, 23 + hoverAnimation, 29 + hoverAnimation, 255));

        if (module != null) {

            // module name
            glScaled(0.8, 0.8, 0.8); {

                // scaled position
                float scaledX = (categoryFrameComponent.getPosition().x + 4) * 1.25F;
                float scaledY = (featureHeight + 4.5F) * 1.25F;
                float scaledWidth = (categoryFrameComponent.getPosition().x + categoryFrameComponent.getWidth() - (FontUtil.getStringWidth("...") * 0.8F) - 3) * 1.25F;
                if(module.isEnabled()) {
                    FontUtil.drawStringWithShadow(getModule().getName(), scaledX, scaledY, primaryColor);
                    FontUtil.drawStringWithShadow("...", scaledWidth, scaledY - 3, new Color(255, 255, 255).getRGB());
                } else {
                    if(!Arrays.asList(coreModules).contains((module.getName()))) {
                        FontUtil.drawStringWithShadow(getModule().getName(), scaledX, scaledY, new Color(255, 255, 255).getRGB());
                        FontUtil.drawStringWithShadow("...", scaledWidth, scaledY - 3, new Color(255, 255, 255).getRGB());
                    } else {
                        FontUtil.drawStringWithShadow(getModule().getName(), scaledX, scaledY, primaryColor);
                        FontUtil.drawStringWithShadow("...", scaledWidth, scaledY - 3, new Color(255, 255, 255).getRGB());
                    }
                }
            }

            glScaled(1.25, 1.25, 1.25);
        }
    }

    @Override
    public void onClick(ClickType in) {
        if (module != null) {

            // toggle the module if clicked
            if (isMouseOver(categoryFrameComponent.getPosition().x, featureHeight, categoryFrameComponent.getWidth(), HEIGHT)) {

                // module feature bounds
                float highestPoint = featureHeight;
                float lowestPoint = highestPoint + HEIGHT;

                // check if it's able to interact with
                if (highestPoint >= categoryFrameComponent.getPosition().y + categoryFrameComponent.getTitle() + 2 && lowestPoint <= categoryFrameComponent.getPosition().y + categoryFrameComponent.getTitle() + categoryFrameComponent.getHeight() + 2) {
                    if (in.equals(ClickType.LEFT)) {
                        module.toggle();
                        if(module.isEnabled()) {
                            module.onEnable();
                        } else {
                            module.onDisable();
                        }
                    }

                    else if (in.equals(ClickType.RIGHT)) {
                        open = !open;
                        animation.setState(open);
                    }
                }
            }

            if (open) {
                settingComponents.forEach(settingComponent -> settingComponent.onClick(in));
            }
        }
    }

    @Override
    public void onType(int in) {
        if (module != null) {
            if (open) {
                settingComponents.forEach(settingComponent -> settingComponent.onType(in));
            }
        }
    }

    @Override
    public void onScroll(int in) {
        if (module != null) {
            if (open) {
                settingComponents.forEach(settingComponent -> settingComponent.onScroll(in));
            }
        }
    }

    /**
     * Gets the parent category frame feature
     * @return The parent category frame feature
     */
    public CategoryFrameComponent getCategoryFrameComponent() {
        return categoryFrameComponent;
    }

    /**
     * Gets the module
     * @return The module
     */
    public Module getModule() {
        return module;
    }

    /**
     * Gets the offset for setting features
     * @return The offset for setting features
     */
    public float getSettingComponentOffset() {
        return settingComponentOffset;
    }
}
