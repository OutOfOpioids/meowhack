package me.notkronos.meowhack.mixin.mixins.render;

import me.notkronos.meowhack.module.render.NoRender;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase {
    @Inject(method = "renderArmorLayer", at = @At("HEAD"), cancellable = true)
    public void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo info) {
        if (NoRender.INSTANCE.isEnabled()) {
           if(slotIn == EntityEquipmentSlot.HEAD && NoRender.helmet.getValue()
           || slotIn == EntityEquipmentSlot.CHEST && NoRender.chestplate.getValue()
           || slotIn == EntityEquipmentSlot.LEGS && NoRender.leggings.getValue()
           || slotIn == EntityEquipmentSlot.FEET && NoRender.boots.getValue()) {
               info.cancel();
           }
        }
    }
}
