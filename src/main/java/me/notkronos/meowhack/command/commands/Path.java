package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.MessageSender;
import me.notkronos.meowhack.util.file.FileSystemUtil;

public class Path extends Command {

    public Path() {
        super("path", "Used to save the config", new String[]{});
    }

    @Override
    public void onExecute(String[] args) {
        Meowhack.LOGGER.info("[MEOWHACK] Default config path: " + FileSystemUtil.getDefaultConfigPath().toString());
        new MessageSender().sendMessageClientSide("[meowhack] Default config path: " + FileSystemUtil.getDefaultConfigPath().toString());
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
        return "path";
    }

}
