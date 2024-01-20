package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.ChatUtil;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class AntiPoplag extends Module {

    public static AntiPoplag INSTANCE;

    public AntiPoplag() {
        super("AntiPoplag", Category.COMBAT, "Prevents poplag message from lagging your game", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = true;
        INSTANCE.drawn = true;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketChat && this.enabled) {
            String message = ((SPacketChat) event.getPacket()).getChatComponent().getUnformattedText();
            Pattern pattern = Pattern.compile("[^\\x00-\\x7F\\u0400-\\u04FF]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                event.setCanceled(true);
                ChatUtil.sendMessageClientSide(TextFormatting.RED + "[meowhack]" + TextFormatting.WHITE + " Poplag message blocked!");
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message.replaceAll(pattern.pattern(), "")));
            }
        }
    }
}
