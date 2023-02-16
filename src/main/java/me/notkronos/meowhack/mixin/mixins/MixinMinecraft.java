package me.notkronos.meowhack.mixin.mixins;

import me.notkronos.meowhack.module.render.SwingSpeed;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void onRCM(CallbackInfo ci) {
        SwingSpeed.INSTANCE.isCausedByRClick = true;
    }
}
