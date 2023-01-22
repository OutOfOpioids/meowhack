package me.notkronos.meowhack.util.chat;

import net.minecraft.util.text.TextComponentString;
import java.util.concurrent.ThreadLocalRandom;
import static me.notkronos.meowhack.util.Wrapper.mc;

public class MessageSender {
    public void sendMessageClientSide(String in) {
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(in), ThreadLocalRandom.current().nextInt(32767));
    }
}
