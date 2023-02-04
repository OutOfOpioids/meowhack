package me.notkronos.meowhack.mixin.mixins.render.entity;

import me.notkronos.meowhack.module.render.Weather;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements IEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V"))
    public void weatherHook(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        try {
            if (Weather.INSTANCE.isEnabled()) {
                Weather.render(partialTicks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}