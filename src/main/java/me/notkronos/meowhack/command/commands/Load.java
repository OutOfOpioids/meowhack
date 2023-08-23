package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;

public class Load extends Command {

        public Load() {
            super("load", "Used to load the config", new String[]{});
        }

        @Override
        public void onExecute(String[] args) {
            Meowhack.INSTANCE.getConfigManager().loadModules();
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
            return "load";
        }
}
