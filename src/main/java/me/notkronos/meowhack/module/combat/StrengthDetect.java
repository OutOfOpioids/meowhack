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
        super("StrengthNotif", Category.COMBAT, "Notifies when a gay person is detected in your visualrange", new String[]{});
        INSTANCE = this;
        this.enabled = false;
        this.drawn = false;
    }

    @Override
    public void onUpdate() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player)) continue;
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                messageSender.sendMessageClientSide(player.getDisplayNameString() + " has strength");
                this.str.add(player);
            }
            if (this.str.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
            this.str.remove(player);
        }
    }
}
