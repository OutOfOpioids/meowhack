package me.notkronos.meowhack.mixin.mixins.accessor;

import net.minecraft.util.text.TextComponentString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextComponentString.class)
public interface ITextComponentStringAccessor {

    @Accessor("text")
    void setText(String text);
}