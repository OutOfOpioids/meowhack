package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class StrengthDetect extends Module {
    public static StrengthDetect INSTANCE;

    private final Set<EntityPlayer> str = Collections.newSetFromMap(new WeakHashMap<>());
    private final MessageSender messageSender = new MessageSender();

    public StrengthDetect() {
        super("StrengthNotif", Category.COMBAT, "Notifies when someone gets strength in your visual range", new String[]{});
        INSTANCE = this;
        this.enabled = false;
        this.drawn = false;
    }

    @Override
    public void onTick() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if(player.isPotionActive(MobEffects.STRENGTH) && !player.equals(mc.player)) {
                if (!str.contains(player)) {
                    str.add(player);
                    messageSender.sendMessageClientSide(player.getName() + " has strength!");
                }
            } else {
                str.remove(player);
            }
        }
    }
}
