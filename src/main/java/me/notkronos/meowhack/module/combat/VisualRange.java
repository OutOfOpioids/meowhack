package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.MessageSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class VisualRange extends Module {
    public static VisualRange INSTANCE;

    public VisualRange() {
        super("VisualRange", Category.COMBAT, "Sends a chat notif when someone enters your render distance.", new String[]{"AutoGroom"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }

    private final List<Entity> players = new CopyOnWriteArrayList<>();
    private final MessageSender messageSender = new MessageSender();
    @Override
    public void onEnable() {
        super.onEnable();

        // clear old list
        players.clear();
        for(Entity entity : mc.world.loadedEntityList) {
            if(entity instanceof EntityPlayer) {
                players.add(entity);
            }
        }
    }

    @Override
    public void onUpdate() {
        if(mc.player.ticksExisted > 20) {
            List<Entity> exitPlayers = players.stream().filter(player -> !mc.world.loadedEntityList.contains(player)).collect(Collectors.toList());
            List<Entity> enterPlayers = mc.world.loadedEntityList.stream().filter(
                    player -> player instanceof EntityPlayer).filter(player -> !player.equals(mc.player)).filter(
                            player -> !players.contains(player)).collect(Collectors.toList()
            );

            // on enter

            for(Entity player : enterPlayers) {
                if(!player.getName().equals(mc.player.getName())) {
                    String message = TextFormatting.RED + "[Meowhack] " + TextFormatting.GREEN
                            + player.getName() + TextFormatting.WHITE + " has " + TextFormatting.GREEN
                            + "entered " + TextFormatting.WHITE + " your visual range!";

                    messageSender.sendMessageClientSide(message);
                }
            }

            // on exit
            for(Entity player : exitPlayers) {
                if(!player.getName().equals(mc.player.getName())) {
                    String message = TextFormatting.RED + "[Meowhack] " + TextFormatting.GREEN
                            + player.getName() + TextFormatting.WHITE + " has " + TextFormatting.RED
                            + "left " + TextFormatting.WHITE + " your visual range!";

                    messageSender.sendMessageClientSide(message);
                }
            }
        }
    }
}
