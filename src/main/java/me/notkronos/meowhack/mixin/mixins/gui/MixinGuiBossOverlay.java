package me.notkronos.meowhack.mixin.mixins.gui;

import me.notkronos.meowhack.module.render.NoRender;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiBossOverlay.class)
public abstract class MixinGuiBossOverlay {
    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    public void renderBossHealth(CallbackInfo info) {
        if (NoRender.INSTANCE.isEnabled() && NoRender.noBossOverlay.getValue()) {
            info.cancel();
        }
    }
}
