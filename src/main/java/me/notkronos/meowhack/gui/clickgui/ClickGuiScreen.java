package me.notkronos.meowhack.gui.clickgui;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.gui.clickgui.items.buttons.ModuleButton;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.client.ClickGUIModule;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {
    private static ClickGuiScreen clickGui;
    private final ArrayList<Frame> frames = new ArrayList<>();

    public ClickGuiScreen() {
        if(this.getFrames().isEmpty()) {
            this.load();
        }
    }

    public static ClickGuiScreen getClickGui() {
        return clickGui == null ? (clickGui = new ClickGuiScreen()) : clickGui;
    }

    private void load() {
        int x = -84;
        for(Category category : Category.values()) {
            this.frames.add(new Frame(category.name(), x += 90, 4, true) {
                @Override
                public void setupItems() {
                    Meowhack.INSTANCE.getModuleManager().getAllModules().forEach(module -> {
                        if(module.getCategory() == category) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.frames.forEach(panel -> panel.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        this.frames.forEach(panel -> panel.mouseClicked(mouseX, mouseY, clickedButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        this.frames.forEach(panel -> panel.mouseReleased(mouseX, mouseY, releaseButton));
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.frames.forEach(panel -> panel.getItems().forEach(item -> item.onType(keyCode)));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public ArrayList<Frame> getFrames() {
        return this.frames;
    }
}
