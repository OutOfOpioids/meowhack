package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class CrystalChams extends Module {
    public static CrystalChams INSTANCE;

    public CrystalChams() {
        super("CrystalChams", Category.RENDER, "Modifies crystal rendering", new String[]{"EndCrystalChams"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    private final List<EntityEnderCrystal> crystalList = new ArrayList<>();

    //Settings
    public static Setting<Boolean> noAnimation = new Setting<>("NoAnimation", false);

    @Override
    public void onUpdate() {
        for(Entity crystal : mc.world.loadedEntityList) {
            if(!(crystal instanceof EntityEnderCrystal)) continue;
            crystalList.add((EntityEnderCrystal) crystal);
        }
    }

    //remove an EndCrystal from the list if it got destroyed.
    @SubscribeEvent
    public void onReceivePacket(PacketEvent.PacketReceiveEvent event) {
            if(event.getPacket() instanceof SPacketDestroyEntities) {
                SPacketDestroyEntities packet = (SPacketDestroyEntities) event.getPacket();
                for(int id : packet.getEntityIDs()) {
                    Entity crystal = mc.world.getEntityByID(id);
                    if(crystal instanceof EntityEnderCrystal) crystalList.remove(crystal);
                }
            }
    }
}
