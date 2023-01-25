package me.notkronos.meowhack.gui.util;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.gui.clickgui.ClickGUIScreen;

public interface InterfaceWrapper {

    /**
     * Checks if the mouse is over a region
     * @param x The lower x
     * @param y The lower y
     * @param width The upper x
     * @param height The upper y
     * @return Whether the mouse is over the given region
     */
    default boolean isMouseOver(float x, float y, float width, float height) {
        return getMouse().getPosition().x >= x && getMouse().getPosition().y >= y && getMouse().getPosition().x <= (x + width) && getMouse().getPosition().y <= (y + height);
    }

    /**
     * Gets the mouse
     * @return The mouse
     */
    default MousePosition getMouse() {
        return getGUI().getMouse();
    }

    /**
     * Gets the scissor stack
     * @return The scissor stack
     */
    default ScissorStack getScissorStack() {
        return getGUI().getScissorStack();
    }

    /**
     * Gets the Click Gui screen
     * @return The Click Gui screen
     */
    default ClickGUIScreen getGUI() {
        return Meowhack.INSTANCE.getClickGUI();
    }
}
