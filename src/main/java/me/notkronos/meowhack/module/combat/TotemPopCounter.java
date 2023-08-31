package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.combat.TotemPopEvent;
import me.notkronos.meowhack.event.events.entity.DeathEvent;
import me.notkronos.meowhack.event.events.entity.EntityWorldEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.Wrapper;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class TotemPopCounter extends Module implements Wrapper {
    public static TotemPopCounter INSTANCE;
    MessageSender messageSender = new MessageSender();
    private final Map<Entity, Integer> totemPops = new HashMap<>();

    public int getTotemPops(Entity entity) {
        return totemPops.getOrDefault(entity, 0);
    }

    public TotemPopCounter(){
        super("TotemPopCounter", Category.COMBAT, "Counts totem pops and print pop notifs in chat", new String[]{"PopCounter"});
        INSTANCE = this;
        INSTANCE.drawn = false;
        INSTANCE.enabled = true;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        if(isEnabled()) {
            totemPops.put(event.getPopEntity(), totemPops.containsKey(event.getPopEntity()) ? totemPops.get(event.getPopEntity()) + 1 : 1);
            String message;
            Entity player = event.getPopEntity();
            int pops = getTotemPops(player);
            if (pops == 1) {
                message = TextFormatting.RED + "[Meowhack] " + TextFormatting.WHITE
                        + player.getName() + " popped " + TextFormatting.GREEN + pops + TextFormatting.WHITE + " totem.";
            } else {
                message = TextFormatting.RED + "[Meowhack] " + TextFormatting.WHITE
                        + player.getName() + " popped " + TextFormatting.GREEN + pops + TextFormatting.WHITE + " totems.";
            }
            messageSender.sendMessageClientSide(message);
        }
    }

    @SubscribeEvent
    public void onDeath(DeathEvent event) {
        if(totemPops.containsKey(event.getEntity())) {
            int pops = totemPops.get(event.getEntity());
            messageSender.sendMessageClientSide(TextFormatting.RED + "[Meowhack] " + TextFormatting.WHITE
                    + event.getEntity().getName() + " died after popping " + TextFormatting.GREEN + totemPops.get(event.getEntity()) + TextFormatting.WHITE + (pops == 1 ? " totem." : " totems."));
        }
    }

    @SubscribeEvent
    public void onRemoveEntity(EntityWorldEvent.EntityRemoveEvent event) {
        totemPops.remove(event.getEntity());
    }
}
