package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraft.util.text.TextFormatting;

public class Save extends Command {

        public Save() {
            super("save", "Used to save the config", new String[]{});
        }

        MessageSender messageSender = new MessageSender();

        @Override
        public void onExecute(String[] args) {
            Meowhack.INSTANCE.getConfigManager().saveModules();
            Meowhack.INSTANCE.getConfigManager().saveFriends();
            messageSender.sendMessageClientSide(TextFormatting.RED + "[Meowhack] " + TextFormatting.RESET + "Saved the default config!");
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
