package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.combat.TotemPopEvent;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventManager extends Manager implements Wrapper {
    public EventManager() {
        super("EventManager");
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent.Text event) {
        for (Module module : getMeowhack().getModuleManager().getAllModules()) {
            if (nullCheck() || getMeowhack().getNullSafeFeatures().contains(module)) {
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
            if (nullCheck() || getMeowhack().getNullSafeFeatures().contains(manager)) {
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
