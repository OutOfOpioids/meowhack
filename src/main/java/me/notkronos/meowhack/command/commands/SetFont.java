package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.font.CustomFontManager;
import me.notkronos.meowhack.font.CustomFontRenderer;
import me.notkronos.meowhack.module.client.CustomFontMod;

import java.awt.*;

public class SetFont extends Command {
    public SetFont() {
        super("CustomFont", "development command for testing", new String[]{"CustomFont", "CustomFont", "CustomFont"});
    }

    @Override
    public void onExecute(String[] args) {
        CustomFontManager.customFont = new CustomFontRenderer(
                new Font("Verdana",
                        CustomFontMod.fontStyle.value,
                        CustomFontMod.fontSize.value),
                CustomFontMod.antiAlias.value,
                CustomFontMod.metrics.value
        );
    }

    @Override
    public String getUseCase() {
        return "";
    }

    @Override
    public int getArgSize() {
        return 0;
    }

    @Override
    public String getName() {
        return "setfont";
    }
}
