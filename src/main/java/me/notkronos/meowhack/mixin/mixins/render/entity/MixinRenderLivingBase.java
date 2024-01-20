package me.notkronos.meowhack.mixin.mixins.render.entity;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.RenderLivingEntityEvent;
import me.notkronos.meowhack.module.render.PlayerModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderLivingBase.class, priority = Integer.MAX_VALUE - 1)
public abstract class MixinRenderLivingBase {
    @Shadow
    protected ModelBase mainModel;

    @Redirect(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void onRenderModelPreEntityLivingBase(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        RenderLivingEntityEvent.RenderLivingEntityPreEvent event = new RenderLivingEntityEvent.RenderLivingEntityPreEvent(modelBase, (EntityLivingBase) entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        Meowhack.EVENT_BUS.post(event);
        if(PlayerModel.INSTANCE.isEnabled() && PlayerModel.limbAnimation.value) {
            if(entityIn instanceof EntityPlayer) {
                limbSwing = PlayerModel.limbSwing.value;
                limbSwingAmount = PlayerModel.limbSwingAmount.value;
            }
        }
        if (!event.isCanceled()) {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }

    @Inject(method = "renderModel", at = @At("RETURN"), cancellable = true)
    private void onRenderModelPost(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        RenderLivingEntityEvent.RenderLivingEntityPostEvent event = new RenderLivingEntityEvent.RenderLivingEntityPostEvent(this.mainModel, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        Meowhack.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
