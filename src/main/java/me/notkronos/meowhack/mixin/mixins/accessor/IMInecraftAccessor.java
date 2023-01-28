package me.notkronos.meowhack.mixin.mixins.accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMInecraftAccessor {
    @Accessor("timer")
    Timer getTimer();
}

