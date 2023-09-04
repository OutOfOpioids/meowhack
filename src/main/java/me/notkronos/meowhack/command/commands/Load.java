package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.MessageSender;
import me.notkronos.meowhack.util.chat.MessageType;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class Load extends Command {

        public Load() {
            super("load", "Used to load the config", new String[]{});
        }

        @Override
        public void onExecute(String[] args) {
            Meowhack.INSTANCE.getConfigManager().loadModules();
            Meowhack.INSTANCE.getConfigManager().loadFriends();
            MessageSender.commandFeedback("Loaded the default config", MessageType.SUCCESS);
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
