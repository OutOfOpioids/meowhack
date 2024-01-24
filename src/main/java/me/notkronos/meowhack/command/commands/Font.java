package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;
import me.notkronos.meowhack.util.render.FontUtil;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Font extends Command {
    public Font() {
        super("font", "Change the font", new String[]{"font", "font"});
    }

    public static String fontName = "Verdana";

    @Override
    public void onExecute(String[] args) throws IOException {
        if (args.length == 1) {
            List<String> fonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
            if(fonts.stream().anyMatch(f -> f.equalsIgnoreCase(args[0]))) {
                FontUtil.customFont = new CustomFontRenderer(
                        new java.awt.Font(args[0],
                                CustomFontMod.fontStyle.value,
                                CustomFontMod.fontSize.value
                        ),
                        CustomFontMod.antiAlias.value,
                        CustomFontMod.metrics.value
                );
                fontName = args[0];
                Meowhack.INSTANCE.getConfigManager().saveFont(args[0]);
            } else {
                ChatUtil.commandFeedback("Font " + args[0] + " not found.", MessageType.ERROR);
            }
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
