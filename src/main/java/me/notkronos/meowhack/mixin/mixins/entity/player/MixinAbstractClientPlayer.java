package me.notkronos.meowhack.mixin.mixins.entity.player;

import me.notkronos.meowhack.module.render.StaticFOV;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {
    @Inject(method = "getFovModifier", at = @At("HEAD"), cancellable = true)
    public void onGetFovModifier(CallbackInfoReturnable<Float> cir) {
        if(StaticFOV.INSTANCE.isEnabled()) {
            cir.setReturnValue(StaticFOV.fovModifier.getValue());
        }
    }
}
