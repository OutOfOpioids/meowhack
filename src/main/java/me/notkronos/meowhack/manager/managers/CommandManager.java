package me.notkronos.meowhack.manager.managers;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.command.commands.*;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class CommandManager extends Manager {
    private final List<Command> commands = new ArrayList<>();
    private final MessageSender messageSender = new MessageSender();

    public CommandManager() {
        super("CommandManager");
        commands.add(new BindCommand());
        commands.add(new CustomFont());
        commands.add(new Drawn());
        commands.add(new Friend());
        commands.add(new Load());
        commands.add(new Save());
        commands.add(new SpammerFile());
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatInput(ClientChatEvent event) throws IOException {
        String command = event.getMessage().trim();

        if(command.startsWith(Meowhack.PREFIX)) {
            event.setCanceled(true);
            mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

            //remove prefix
            command = command.substring(Meowhack.PREFIX.length());

            //parse
            String[] args = command.split(" ");
            String commandName = args[0];
            Meowhack.LOGGER.info(commandName);
            args = Arrays.copyOfRange(args, 1, args.length);
            Command executable = Meowhack.INSTANCE.getCommandManager().getCommand(command1 -> command1.getName().toLowerCase().equals(commandName.toLowerCase()));
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
