package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.combat.TotemPopEvent;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventManager extends Manager implements Wrapper {
    public EventManager() {
        super("EventManager");
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {

        // check if the update event is for the local player
        if (nullCheck() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals(mc.player)) {
            try {
                HUD.INSTANCE.onUpdate();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

            // manager onUpdate
            for (Manager manager : getMeowhack().getAllManagers()) {

                // check if the manager is safe to run
                if (nullCheck()) {

                    // run
                    try {
                        manager.onUpdate();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {

        // module onTick
        for (Module module : getMeowhack().getModuleManager().getAllModules()) {

            // check if the module is safe to run
            if (nullCheck()) {

                // check if module should run
                if (module.isEnabled()) {

                    // run
                    try {
                        module.onTick();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent.Text event) {
        for (Module module : getMeowhack().getModuleManager().getAllModules()) {
            if (nullCheck()) {
                if (module.isEnabled()) {
                    try {
                        module.onRender2D();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        for (Manager manager : getMeowhack().getAllManagers()) {
            if (nullCheck()) {
                try {
                    manager.onRender2D();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceived(PacketEvent.PacketReceiveEvent event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                packet.getEntity(EventManager.mc.world);
                Entity entity = packet.getEntity(EventManager.mc.world);
                Meowhack.EVENT_BUS.post(new TotemPopEvent(entity));
            }
        }
    }
}
