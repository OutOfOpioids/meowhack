package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;

public class CustomFont extends Command {
    public CustomFont() {
        super("CustomFont", "development command for testing", new String[]{"CustomFont", "CustomFont", "CustomFont"});
    }

    @Override
    public void onExecute(String[] args) {
        me.notkronos.meowhack.module.client.CustomFontMod.INSTANCE.enable();
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
        return "customfont";
    }
}
