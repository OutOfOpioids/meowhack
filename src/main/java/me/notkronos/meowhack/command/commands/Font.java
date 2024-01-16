package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.gui.clickgui.ClickGUIScreen;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.util.render.FontUtil;

import java.io.IOException;

public class Font extends Command {
    public Font() {
        super("font", "Change the font", new String[]{"font", "font"});
    }

    @Override
    public void onExecute(String[] args) throws IOException {
        if (args.length == 1) {
            ClickGUIScreen.setFont(args[0]);
        }
    }

    @Override
    public String getUseCase() {
        return null;
    }

    @Override
    public int getArgSize() {
        return 1;
    }

    @Override
    public String getName() {
        return "font";
    }
}
