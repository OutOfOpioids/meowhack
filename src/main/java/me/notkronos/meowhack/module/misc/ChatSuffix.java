package me.notkronos.meowhack.module.misc;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.mixin.mixins.accessor.ICPacketChatMessageAccessor;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatSuffix extends Module {
    public static ChatSuffix INSTANCE;

    public ChatSuffix() {
        super("ChatSuffix", Category.MISC, "Appends meowhack watermark to messages you send", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.PacketSendEvent event) {
        if(event.getPacket() instanceof CPacketChatMessage) {
            String message = ((CPacketChatMessage) event.getPacket()).getMessage() + " ⏐ ᴍᴇᴏᴡʜᴀᴄᴋ";
            Pattern pattern = Pattern.compile("[+]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(Meowhack.VERSION);
            if(matcher.find()) {
                message += "-ʙᴇᴛᴀ";
            }
            ((ICPacketChatMessageAccessor) event.getPacket()).setMessage(message);
        }
    }
}
