package me.notkronos.meowhack.module.misc;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.combat.TotemPopEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class Announcer extends Module {
    public static Announcer INSTANCE;
    public Announcer() {
        super("Announcer", Category.MISC, "Announces different events in chat", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = true;
        INSTANCE.enabled = false;
        Meowhack.EVENT_BUS.register(this);
    }

    //Settings
    public static Setting<Boolean> meow = new Setting<>("CatMode", true);

    //Event Settings
    public static Setting<Boolean> announcePops = new Setting<>("TotemPops", true);

    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        if (isEnabled()) {
            if (announcePops.getValue()) {
                if(event.getPopEntity() != mc.player) {
                    //Avoid announcing friend totem pops
                    if(Meowhack.INSTANCE.getFriendManager().isFriend(event.getPopEntity().getName())) {
                        return;
                    }
                    if (meow.getValue()) {
                        mc.player.sendChatMessage(event.getPopEntity().getName() + " meow meow meow meow meow meowhack!");
                    } else {
                        mc.player.sendChatMessage(event.getPopEntity().getName() + " popped a totem thanks to meowhack!");
                    }
                }
            }
        }
    }
}
