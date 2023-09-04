package me.notkronos.meowhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.Bind.Device;
import me.notkronos.meowhack.util.chat.MessageSender;
import me.notkronos.meowhack.util.chat.MessageType;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {

    private final String name = "bind";

    public BindCommand() {
        super("bind","Used to change module keybinds", new String[]{});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length != 2) {
            MessageSender.commandFeedback("Bind takes 2 arguments. Correct usage is " + Meowhack.PREFIX + "bind " + getUseCase(), MessageType.ERROR);
            return;
        }

        Module module = getMeowhack().getModuleManager().getModule(name -> name.getName().equalsIgnoreCase(args[0]));

        if(module == null) {
            MessageSender.commandFeedback("Module " + args[0] + " doesn't exist", MessageType.ERROR);
            return;
        }

        int key = Keyboard.getKeyIndex(args[1].toUpperCase());

        if(key != Keyboard.KEY_NONE) {
            module.getBind().setValue(new Bind(key, Device.KEYBOARD));
            MessageSender.commandFeedback("Bound " + module.getName() + " to " + args[1].toUpperCase(), MessageType.SUCCESS);
        }
    }

    @Override
    public String getUseCase() {
        return "<module> [bind]";
    }

    @Override
    public int getArgSize() {
        return 2;
    }

    @Override
    public String getName() {
        return name;
    }
}
