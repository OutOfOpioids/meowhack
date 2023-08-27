package me.notkronos.meowhack.mixin.mixins.render.entity;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.AspectRatioEvent;
import me.notkronos.meowhack.event.events.render.RenderItemInFirstPersonEvent;
import me.notkronos.meowhack.event.events.render.RenderWorldEvent;
import me.notkronos.meowhack.module.render.Weather;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.notkronos.meowhack.util.Wrapper.mc;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements IEntityRenderer {
    @Shadow
    protected abstract void setupCameraTransform(float partialTicks, int pass);
    @Shadow protected abstract void renderHand(float partialTicks, int pass);

    @Override
    public void invokeSetupCameraTransform(float partialTicks, int pass) {
        setupCameraTransform(partialTicks, pass);
    }
    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V", remap = false))
    private void onSetupCameraTransform(final float fovy, final float aspect, final float zNear, final float zFar) {
        final AspectRatioEvent event = new AspectRatioEvent(mc.displayWidth / (float) mc.displayHeight);
        Meowhack.EVENT_BUS.post(event);
        Project.gluPerspective(fovy, event.getAspectRatio(), zNear, zFar);
    }

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

    @Override
    public void invokeRenderHand(float partialTicks, int pass) {
        renderHand(partialTicks, pass);
    }

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(F)V"))
    private void renderHand(ItemRenderer itemRenderer, float partialTicks)
    {
        RenderItemInFirstPersonEvent event = new RenderItemInFirstPersonEvent();
        Meowhack.EVENT_BUS.post(event);
        if (!event.isCanceled()) itemRenderer.renderItemInFirstPerson(partialTicks);
    }
}