package me.notkronos.meowhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.MessageSender;

public class Drawn extends Command {
    public static Drawn INSTANCE;
    private final MessageSender messageSender = new MessageSender();

    private final String name = "drawn";

    public Drawn() {
        super("drawn","Decides if modules should be in the array list", new String[] {"drawn", "draw"});
    }

    @Override
    public String getUseCase() {
        return "<module>";
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length == 1) {
            Module module = getMeowhack().getModuleManager().getModule(name -> name.getName().equals(args[0]));
            if(module != null) {
                module.setDrawn(!module.isDrawn());
            } else {
                messageSender.sendMessageClientSide(ChatFormatting.RED + "Module " + args[0] + " doesn't exist");
            }
        } else {
            messageSender.sendMessageClientSide(ChatFormatting.RED + "Drawn takes 1 argument." + ChatFormatting.RESET +  " Correct usage is " + Meowhack.PREFIX + "drawn " + getUseCase());
        }
    }

    @Override
    public int getArgSize() {
        return 1;
    }

    @Override
    public String getName() {
        return name;
    }
}
