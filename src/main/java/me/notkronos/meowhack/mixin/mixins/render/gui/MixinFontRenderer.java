package me.notkronos.meowhack.mixin.mixins.render.gui;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.RenderFontEvent;
import me.notkronos.meowhack.util.render.FontUtil;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), cancellable = true)
    public void renderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> info) {
        RenderFontEvent renderFontEvent = new RenderFontEvent();
        Meowhack.EVENT_BUS.post(renderFontEvent);
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    public void getWidth(String text, CallbackInfoReturnable<Integer> info) {
        RenderFontEvent renderFontEvent = new RenderFontEvent();
        Meowhack.EVENT_BUS.post(renderFontEvent);

        if (renderFontEvent.isCanceled()) {
            info.setReturnValue(FontUtil.getStringWidth(text));
        }
    }
}