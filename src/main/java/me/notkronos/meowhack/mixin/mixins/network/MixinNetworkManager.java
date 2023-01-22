package me.notkronos.meowhack.mixin.mixins.network;

import com.google.common.graph.Network;
import io.netty.channel.ChannelHandlerContext;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketReceive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent.PacketReceiveEvent packetReceiveEvent = new PacketEvent.PacketReceiveEvent(packet);
        Meowhack.EVENT_BUS.post(packetReceiveEvent);
        if(packetReceiveEvent.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
