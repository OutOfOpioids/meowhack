package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;

public class Friend extends Command {

    public Friend() {
        super("friend", "Add or remove a friend", new String[]{"f", "friends"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length == 1) {
            addFriend(args[0]);
        }
        else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add")) {
                addFriend(args[1]);
            }
            else if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")) {
                removeFriend(args[1]);
            }
            else {
                ChatUtil.commandFeedback("Friend takes 1 or 2 arguments. Correct usage is " + Meowhack.PREFIX + "friend " + getUseCase(), MessageType.ERROR);
            }
        }
        else {
           ChatUtil.commandFeedback("Friend takes 1 or 2 arguments. Correct usage is " + Meowhack.PREFIX + "friend " + getUseCase(), MessageType.ERROR);
        }
        Meowhack.INSTANCE.getConfigManager().saveFriends();
    }

    private void addFriend(String name) {
        if(Meowhack.INSTANCE.getFriendManager().isFriend(name)) {
            ChatUtil.commandFeedback(name + " is already a friend", MessageType.ERROR);
            return;
        }
        Meowhack.INSTANCE.getFriendManager().addFriend(name);
        ChatUtil.commandFeedback("Added " + name + " as a friend", MessageType.SUCCESS);
    }

    private void removeFriend(String name) {
        if(!Meowhack.INSTANCE.getFriendManager().isFriend(name)) {
            ChatUtil.commandFeedback(name + " is not a friend", MessageType.ERROR);
            return;
        }
        Meowhack.INSTANCE.getFriendManager().removeFriend(name);
        ChatUtil.commandFeedback("Removed " + name + " as a friend", MessageType.ERROR);
    }

    @Override
    public String getUseCase() {
        return "(add/del) <name>";
    }

    @Override
    public int getArgSize() {
        return 2;
    }

    @Override
    public String getName() {
        return "friend";
    }

}
