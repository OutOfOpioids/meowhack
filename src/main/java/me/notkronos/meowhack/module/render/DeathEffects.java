package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.DeathEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class DeathEffects extends Module {
    public static DeathEffects INSTANCE;

    public DeathEffects() {
        super("DeathEffects", Category.RENDER.RENDER, "Adds death effects", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDeathEvent(DeathEvent event) {
        if (isEnabled() && event.getEntity() instanceof EntityOtherPlayerMP) {
            EntityLightningBolt lightningBoltEntity = new EntityLightningBolt(mc.world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, true);
            mc.world.addWeatherEffect(lightningBoltEntity);
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_LIGHTNING_IMPACT, 1.0F));
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1.0F));
        }
    }
}
