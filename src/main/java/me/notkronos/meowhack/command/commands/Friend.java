package me.notkronos.meowhack.command.commands;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.command.Command;
import me.notkronos.meowhack.util.chat.MessageSender;

public class Friend extends Command {
    MessageSender messageSender = new MessageSender();

    public Friend() {
        super("friend", "Add or remove a friend", new String[]{"f", "friends"});
    }

    @Override
    public void onExecute(String[] args) {
        if(args.length == 1) {
            if(Meowhack.INSTANCE.getFriendManager().isFriend(args[0])) {
                Meowhack.INSTANCE.getFriendManager().removeFriend(args[0]);
                messageSender.sendMessageClientSide("Removed " + args[0] + " as a friend");
            } else {
                Meowhack.INSTANCE.getFriendManager().addFriend(args[0]);
                messageSender.sendMessageClientSide("Added " + args[0] + " as a friend");
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add")) {
                if(Meowhack.INSTANCE.getFriendManager().isFriend(args[1])) {
                    messageSender.sendMessageClientSide(args[1] + " is already a friend");
                    return;
                }
                Meowhack.INSTANCE.getFriendManager().addFriend(args[1]);
                messageSender.sendMessageClientSide("Added " + args[1] + " as a friend");
            } else if(args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")) {
                if(!Meowhack.INSTANCE.getFriendManager().isFriend(args[1])) {
                    Meowhack.LOGGER.info(args[1] + " is not a friend");
                    return;
                }
                Meowhack.INSTANCE.getFriendManager().removeFriend(args[1]);
                messageSender.sendMessageClientSide("Removed " + args[1] + " as a friend");
            }
        } else {
           messageSender.sendMessageClientSide("Friend takes 1 or 2 arguments. Correct usage is " + Meowhack.PREFIX + "friend " + getUseCase());
        }
        //Save friends when a friend is added or removed
        Meowhack.INSTANCE.getConfigManager().saveFriends();
    }

    @Override
    public String getUseCase() {
        return "<add/del> <name>";
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
