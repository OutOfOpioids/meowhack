package me.notkronos.meowhack.event.events.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderLivingEntityEvent extends Event {
    private final ModelBase modelBase;
    private final EntityLivingBase entityLivingBase;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float ageInTicks;
    private final float netHeadYaw;
    private final float headPitch;
    private final float scaleFactor;

    public RenderLivingEntityEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.modelBase = modelBase;
        this.entityLivingBase = entityLivingBase;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scaleFactor = scaleFactor;
    }

    public ModelBase getModelBase() {
        return modelBase;
    }

    public EntityLivingBase getEntityLivingBase() {
        return entityLivingBase;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public float getAgeInTicks() {
        return ageInTicks;
    }

    public float getNetHeadYaw() {
        return netHeadYaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public static class RenderLivingEntityPreEvent extends RenderLivingEntityEvent {

        // pre render
        public RenderLivingEntityPreEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
            super(modelBase, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }

    public static class RenderLivingEntityPostEvent extends RenderLivingEntityEvent {

        // post render
        public RenderLivingEntityPostEvent(ModelBase modelBase, EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
            super(modelBase, entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }
    }
}
