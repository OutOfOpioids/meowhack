package me.notkronos.meowhack.module.misc;

import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntiPoplag extends Module {

    public static AntiPoplag INSTANCE;

    public AntiPoplag() {
        super("AntiPoplag", Category.MISC, "Prevents poplag message from lagging your game", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = true;
        INSTANCE.drawn = true;
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event) {
        if(event.getPacket() instanceof SPacketChat) {
            TextComponentString component = (TextComponentString) ((SPacketChat) event.getPacket()).getChatComponent();
            String text = component.getText();
            Pattern pattern = Pattern.compile("[\\x00-\\x7F]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                event.setCanceled(true);
            }
        }
    }
}
