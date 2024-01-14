package me.notkronos.meowhack.util.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;

import java.util.concurrent.ThreadLocalRandom;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ChatUtil {

    public static void sendMessageClientSide(String in) {
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(in), ThreadLocalRandom.current().nextInt(32767));
    }

    public static void commandFeedback(String message, MessageType type) {
        String finalMessage = ChatFormatting.RED + "[Meowhack] ";
        switch(type) {
            case ERROR:
                finalMessage += ChatFormatting.RED + message;
                break;
            case SUCCESS:
                finalMessage += ChatFormatting.GREEN + message;
                break;
            case INFO:
                finalMessage += ChatFormatting.RESET + message;
        }
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(finalMessage), ThreadLocalRandom.current().nextInt(32767));
    }

}
