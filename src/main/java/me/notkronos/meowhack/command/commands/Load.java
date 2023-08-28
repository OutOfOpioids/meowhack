package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class Load extends Command {

        public Load() {
            super("load", "Used to load the config", new String[]{});
        }

        MessageSender messageSender = new MessageSender();

        @Override
        public void onExecute(String[] args) {
            Meowhack.INSTANCE.getConfigManager().loadModules();
            Meowhack.INSTANCE.getConfigManager().loadFriends();
            messageSender.sendMessageClientSide(TextFormatting.RED + "[Meowhack]" + TextFormatting.RESET + " Loaded the default config!");
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
