package me.notkronos.meowhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.MessageSender;

public class Drawn extends Command {
    public static Drawn INSTANCE;
    private final MessageSender messageSender = new MessageSender();

    public Drawn() {
        super("Drawn","Decides if modules should be in the array list", new String[] {"Drawn", "Draw"});
    }

    @Override
    public String getUseCase() {
        return "<module>";
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length == 1) {
            Module module = getMeowhack().getModuleManager().getModule(name -> name.equals(args[0]));
            if(module != null) {
                module.setDrawn(!module.isDrawn());
            } else {
                messageSender.sendMessageClientSide(ChatFormatting.RED + "Module " + args[0] + " doesn't exist");
            }
        } else {
            messageSender.sendMessageClientSide(ChatFormatting.RED + "Drawn takes 1 argument." + ChatFormatting.RESET +  "Correct usage is .drawn " + getUseCase());
        }
    }

    @Override
    public int getArgSize() {
        return 1;
    }
}
