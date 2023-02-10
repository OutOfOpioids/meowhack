package me.notkronos.meowhack.event.events.render;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderItemInFirstPersonEvent extends Event
{
    public static class RenderItemInFirstPersonPreEvent extends RenderItemInFirstPersonEvent {
        private final EntityLivingBase entity;
        private ItemStack stack;
        private ItemCameraTransforms.TransformType transformType;
        private final boolean leftHanded;

        public RenderItemInFirstPersonPreEvent(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
            this.entity = entitylivingbaseIn;
            this.stack = heldStack;
            this.transformType = transform;
            this.leftHanded = leftHanded;
        }

        public ItemCameraTransforms.TransformType getTransformType() {
            return transformType;
        }

        public void setTransformType(ItemCameraTransforms.TransformType transformType) {
            this.transformType = transformType;
        }

        public boolean isLeftHanded() {
            return leftHanded;
        }

        public ItemStack getItemStack() {
            return stack;
        }

        public void setItemStack(ItemStack stack) {
            this.stack = stack;
        }

        public EntityLivingBase getEntity() {
            return entity;
        }
    }

    public static class RenderItemInFirstPersonPostEvent extends RenderItemInFirstPersonEvent{
        private ItemStack stack;
        private final EntityLivingBase entity;
        private ItemCameraTransforms.TransformType transformType;
        private final boolean leftHanded;

        public RenderItemInFirstPersonPostEvent(ItemStack stack, EntityLivingBase entity, ItemCameraTransforms.TransformType transformType, boolean leftHanded) {
            this.stack = stack;
            this.entity = entity;
            this.transformType = transformType;
            this.leftHanded = leftHanded;
        }
        public ItemCameraTransforms.TransformType getTransformType() {
            return transformType;
        }

        public void setTransformType(ItemCameraTransforms.TransformType transformType) {
            this.transformType = transformType;
        }

        public boolean isLeftHanded() {
            return leftHanded;
        }

        public ItemStack getItemStack() {
            return stack;
        }

        public void setItemStack(ItemStack stack) {
            this.stack = stack;
        }

        public EntityLivingBase getEntity() {
            return entity;
        }
    }
}