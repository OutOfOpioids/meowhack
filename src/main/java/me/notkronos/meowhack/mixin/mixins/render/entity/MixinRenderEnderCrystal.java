package me.notkronos.meowhack.mixin.mixins.render.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.RenderCrystalEvent;
import me.notkronos.meowhack.module.render.CrystalChams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderEnderCrystal.class, priority = Integer.MAX_VALUE - 1)
public class MixinRenderEnderCrystal {
    @Redirect(method = "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void onDoRenderPre(ModelBase instance, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        RenderCrystalEvent.RenderCrystalPreEvent renderCrystalEvent = new RenderCrystalEvent.RenderCrystalPreEvent(instance, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Meowhack.EVENT_BUS.post(renderCrystalEvent);
        if (!renderCrystalEvent.isCanceled()) {
            if(CrystalChams.INSTANCE.isEnabled()) {
                if(CrystalChams.noAnimation.getValue()) {
                    //Finding these values was so fucking annoying
                    instance.render(entityIn, 0.0f, limbSwingAmount, 0.15f, 0.0f, 0.0f, 0.0625f);
                } else {
                    instance.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                }
                if(CrystalChams.mode.getValue() == CrystalChams.Mode.BOTH || CrystalChams.mode.getValue() == CrystalChams.Mode.LINE) {
                    CrystalChams.INSTANCE.onRenderModel(renderCrystalEvent);
                }
            } else {
                instance.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V", at = @At("TAIL"), cancellable = true)
    public void onDoRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        RenderCrystalEvent.RenderCrystalPostEvent renderCrystalEvent = new RenderCrystalEvent.RenderCrystalPostEvent(modelEnderCrystal, modelEnderCrystalNoBase, entity, x, y, z, 0, partialTicks);
        Meowhack.EVENT_BUS.post(renderCrystalEvent);
        if(renderCrystalEvent.isCanceled()) {
            ci.cancel();
        }
    }

    @Final
    @Shadow
    private ModelBase modelEnderCrystal;
    @Final
    @Shadow
    private ModelBase modelEnderCrystalNoBase;
}