package me.notkronos.meowhack.mixin.mixins.render.tileEntity;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.tileEntity.RenderEnchantmentTableEvent;
import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityEnchantmentTableRenderer.class)
public class MixinTileEntityEnchantmentTableRenderer {
    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    public void renderBook(TileEntityEnchantmentTable enchantmentTable, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo callbackInfo) {
        RenderEnchantmentTableEvent renderEnchantmentTableEvent = new RenderEnchantmentTableEvent();
        Meowhack.EVENT_BUS.post(renderEnchantmentTableEvent);
        if(renderEnchantmentTableEvent.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
