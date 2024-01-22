package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.util.render.FontUtil;

import java.io.IOException;

public class Font extends Command {
    public Font() {
        super("font", "Change the font", new String[]{"font", "font"});
    }

    public static String fontName = "Verdana";

    @Override
    public void onExecute(String[] args) throws IOException {
        if (args.length == 1) {
            FontUtil.customFont = new CustomFontRenderer(
                    new java.awt.Font(args[0],
                            CustomFontMod.fontStyle.value,
                            CustomFontMod.fontSize.value
                    ),
                    CustomFontMod.antiAlias.value,
                    CustomFontMod.metrics.value
            );
            fontName = args[0];
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
