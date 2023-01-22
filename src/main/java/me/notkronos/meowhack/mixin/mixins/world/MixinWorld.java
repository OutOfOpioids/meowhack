package me.notkronos.meowhack.mixin.mixins.world;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.EntityWorldEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class MixinWorld {
    @Inject(method = "removeEntity", at =@At("HEAD"), cancellable = true)
    public void onRemoveEntity(Entity entity, CallbackInfo callbackInfo) {
        EntityWorldEvent.EntityRemoveEvent entityRemoveEvent = new EntityWorldEvent.EntityRemoveEvent(entity);
        Meowhack.EVENT_BUS.post(entityRemoveEvent);

        if(entityRemoveEvent.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
