package me.notkronos.meowhack.manager.managers;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.command.commands.*;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.util.chat.ChatUtil;
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

    public CommandManager() {
        super("CommandManager");
        commands.add(new BindCommand());
        commands.add(new Drawn());
        commands.add(new Friend());
        commands.add(new Font());
        commands.add(new Load());
        commands.add(new Save());
        commands.add(new SpammerFile());
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChatInput(ClientChatEvent event) throws IOException {
        String chatMessage = event.getMessage().trim();

        if(!chatMessage.startsWith(Meowhack.PREFIX)) {
            return;
        }

        event.setCanceled(true);
        mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());

        chatMessage = chatMessage.substring(Meowhack.PREFIX.length());

        String[] arguments = chatMessage.split(" ");
        String command = arguments[0];
        arguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        Command executableCommand = Meowhack.INSTANCE.getCommandManager().getCommand(command1 -> command1.getName().equalsIgnoreCase(command));

        if(executableCommand != null) {
            executableCommand.onExecute(arguments);
        } else {
            ChatUtil.sendMessageClientSide(ChatFormatting.RED + "[Meowhack] Unrecognized command!");
        }

    }

    public Command getCommand(Predicate<? super Command> predicate) {
        return commands.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}
