package me.notkronos.meowhack.event.events.render;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderItemInFirstPersonEvent extends Event {
    public static class RenderItemInFirstPersonPreEvent extends RenderItemInFirstPersonEvent {
        private final EntityLivingBase entity;
        private ItemStack itemStack;
        private ItemCameraTransforms.TransformType transformType;
        private final boolean leftHanded;

        public RenderItemInFirstPersonPreEvent(EntityLivingBase entityLivingBase, ItemStack stack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
            this.entity = entityLivingBase;
            this.itemStack = stack;
            this.transformType = transform;
            this.leftHanded = leftHanded;
        }

        public void setTransformType(ItemCameraTransforms.TransformType transformType) {
            this.transformType = transformType;
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public EntityLivingBase getEntity() {
            return entity;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public ItemCameraTransforms.TransformType getTransformType() {
            return transformType;
        }

        public boolean isLeftHanded() {
            return leftHanded;
        }
    }
    public static class RenderItemInFirstPersonPostEvent extends RenderItemInFirstPersonEvent {
        private final EntityLivingBase entity;
        private ItemStack itemStack;
        private ItemCameraTransforms.TransformType transformType;
        private final boolean leftHanded;

        public RenderItemInFirstPersonPostEvent(EntityLivingBase entityLivingBase, ItemStack stack, ItemCameraTransforms.TransformType transform, boolean leftHanded) {
            this.entity = entityLivingBase;
            this.itemStack = stack;
            this.transformType = transform;
            this.leftHanded = leftHanded;
        }

        public void setTransformType(ItemCameraTransforms.TransformType transformType) {
            this.transformType = transformType;
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public EntityLivingBase getEntity() {
            return entity;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public ItemCameraTransforms.TransformType getTransformType() {
            return transformType;
        }

        public boolean isLeftHanded() {
            return leftHanded;
        }
    }
}
