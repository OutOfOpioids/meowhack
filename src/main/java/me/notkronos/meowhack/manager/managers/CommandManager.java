package me.notkronos.meowhack.manager.managers;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.command.commands.Drawn;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static me.notkronos.meowhack.util.Wrapper.mc;
import static me.notkronos.meowhack.util.Wrapper.meowhack;

public class CommandManager extends Manager {
    private final List<Command> commands = new ArrayList<>();
    private final MessageSender messageSender = new MessageSender();

    public CommandManager() {
        super("CommandManager");

        commands.add(new Drawn());

        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatInput(ClientChatEvent event) {
        String command = event.getMessage().trim();

        if(command.startsWith(Meowhack.PREFIX)) {
            event.setCanceled(true);
            mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

            //remove prefix
            command = command.substring(1);

            //parse
            String[] args = command.split(" ");
            String commandName = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);
            Command executable = meowhack.getCommandManager().getCommand(cmd -> cmd.equals(commandName));

            if(executable != null) {
                executable.onExecute(args);
            } else {
                messageSender.sendMessageClientSide(ChatFormatting.RED + "[Meowhack] Unrecognized command!");
            }
        }
    }

    public Command getCommand(Predicate<? super Command> predicate) {
        return commands.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
