package me.notkronos.meowhack.mixin.mixins.render.entity;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityRenderer.class)
public interface IEntityRenderer {

    @Accessor("rendererUpdateCount")
    int getRendererUpdateCount();

    @Accessor("rainXCoords")
    float[] getRainXCoords();

    @Accessor("rainYCoords")
    float[] getRainYCoords();

    @Invoker("setupCameraTransform")
    void invokeSetupCameraTransform(float partialTicks, int pass);

    @Invoker("renderHand")
    void invokeRenderHand(float partialTicks, int pass);
}