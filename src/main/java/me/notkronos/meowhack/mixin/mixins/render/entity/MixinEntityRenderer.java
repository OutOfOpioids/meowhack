package me.notkronos.meowhack.mixin.mixins.render.entity;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.RenderWorldEvent;
import me.notkronos.meowhack.module.render.Weather;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.notkronos.meowhack.util.Wrapper.mc;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @Inject(method = "renderWorld", at = @At("RETURN"))
    private void renderWorldHook(CallbackInfo info)
    {
        final int guiScale = mc.gameSettings.guiScale;
        mc.gameSettings.guiScale = 1;
        Meowhack.EVENT_BUS.post(new RenderWorldEvent());
        mc.gameSettings.guiScale = guiScale;
    }
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