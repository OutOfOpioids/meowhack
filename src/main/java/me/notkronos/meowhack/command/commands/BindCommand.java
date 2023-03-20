package me.notkronos.meowhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.Bind.Device;
import me.notkronos.meowhack.util.chat.MessageSender;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {

    public static BindCommand INSTANCE;
    private final String name = "bind";

    public BindCommand() {
        super("bind","Used to change module keybinds", new String[]{});
    }

    private final MessageSender messageSender = new MessageSender();
    @Override
    public void onExecute(String[] args) {
        if(args.length == 2) {
            Module module = getMeowhack().getModuleManager().getModule(name -> name.getName().equals(args[0]));
            if(module != null) {
                int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                if(key != Keyboard.KEY_NONE) {
                    module.getBind().setValue(new Bind(key, Device.KEYBOARD));
                }
            } else {
                messageSender.sendMessageClientSide(ChatFormatting.RED + "Module " + args[0] + " doesn't exist");
            }
        } else {
            messageSender.sendMessageClientSide(ChatFormatting.RED + "Bind takes 2 arguments." + ChatFormatting.RESET +  " Correct usage is " + Meowhack.PREFIX + "drawn " + getUseCase());
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
