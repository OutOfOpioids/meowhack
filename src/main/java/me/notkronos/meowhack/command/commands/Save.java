package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;

public class Save extends Command {

        public Save() {
            super("save", "Used to save the config", new String[]{});
        }

        @Override
        public void onExecute(String[] args) {
            Meowhack.INSTANCE.getConfigManager().saveModules();
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
        return "save";
    }

}
