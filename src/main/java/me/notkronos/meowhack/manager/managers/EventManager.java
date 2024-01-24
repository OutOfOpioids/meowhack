package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.combat.TotemPopEvent;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.module.combat.VisualRange;
import me.notkronos.meowhack.module.render.HoleESP;
import me.notkronos.meowhack.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
        for (Manager manager : getMeowhack().getAllManagers()) {
            if (!nullCheck()) continue;
            try {
                manager.onUpdate();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (!nullCheck() && !event.getEntity().getEntityWorld().isRemote && !event.getEntityLiving().equals(mc.player)) return;
        try {
            HUD.INSTANCE.onUpdate();
            VisualRange.INSTANCE.onUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Module module : getMeowhack().getModuleManager().getAllModules()) {
            if (!nullCheck() || !module.isEnabled()) continue;
            try {
                module.onTick();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onRender2d(RenderGameOverlayEvent.Text event) {
        for (Module module : getMeowhack().getModuleManager().getAllModules()) {
            if (!nullCheck() || !module.isEnabled()) continue;
            try {
                module.onRender2D();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        for (Manager manager : getMeowhack().getAllManagers()) {
            if (!nullCheck()) continue;
            try {
                manager.onRender2D();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        if(!nullCheck()) return;
        try {
            HoleESP.INSTANCE.onRender3D();
        } catch(Exception e) {
            e.printStackTrace();
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
