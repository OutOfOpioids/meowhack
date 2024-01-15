package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;

public class Drawn extends Command {

    private final String name = "drawn";

    public Drawn() {
        super("drawn","Decides if modules should be in the array list", new String[] {"drawn", "draw"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length != 1) {
            ChatUtil.commandFeedback("Drawn takes 1 argument. Correct usage is " + Meowhack.PREFIX + "drawn " + getUseCase(), MessageType.ERROR);
        }

        Module module = getMeowhack().getModuleManager().getModule(name -> name.getName().equals(args[0]));
        if(module != null) {
            module.setDrawn(!module.isDrawn());
        } else {
            ChatUtil.commandFeedback("Module " + args[0] + " not found.", MessageType.ERROR);
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

    @Override
    public String getUseCase() {
        return "<module>";
    }
}
