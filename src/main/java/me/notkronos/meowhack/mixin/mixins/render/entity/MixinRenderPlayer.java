package me.notkronos.meowhack.mixin.mixins.render.entity;

import me.notkronos.meowhack.module.render.PlayerModel;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.notkronos.meowhack.util.Wrapper.mc;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer {

    @Shadow
    public abstract ModelPlayer getMainModel();

    @Shadow
    public abstract void setModelVisibilities(AbstractClientPlayer clientPlayer);

    @Inject(method = "setModelVisibilities", at = @At("TAIL"), cancellable = true)
    public void onSetModelVisiblities(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        ModelPlayer modelplayer = this.getMainModel();
        if(PlayerModel.INSTANCE.isEnabled() && PlayerModel.crouch.value) {
            modelplayer.isSneak = true;
        }
    }

    @Inject(method = "doRender", at = @At("HEAD"))
    private void onRenderPre(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if(PlayerModel.INSTANCE.isEnabled() && PlayerModel.crouch.value) {
            y -= 0.125D;
        }
    }
}
