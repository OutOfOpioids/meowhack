package me.notkronos.meowhack.mixin.mixins.entity;

import me.notkronos.meowhack.module.render.SwingSpeed;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase{
    @Shadow protected abstract boolean isPlayer();

    @Inject(method = "getArmSwingAnimationEnd", at = @At(value = "HEAD"), cancellable = true)
    protected void onGetArmSwingAnimationEnd(CallbackInfoReturnable<Integer> ci){
        if(SwingSpeed.INSTANCE.isEnabled()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player == null || !this.isPlayer()) return;
            int modifier = SwingSpeed.speed.value;
            ci.setReturnValue(modifier);
        } else {
            ci.setReturnValue(8);
        }
    }
}
