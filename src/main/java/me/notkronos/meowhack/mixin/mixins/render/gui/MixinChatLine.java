package me.notkronos.meowhack.mixin.mixins.render.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.module.misc.ChatTimestamp;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mixin(ChatLine.class)
public class MixinChatLine {
    String timestamp;
    private static final DateFormat FORMAT = new SimpleDateFormat("k:mm");

    @Accessor(value = "lineString")
    public void setComponent(ITextComponent component) {

    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void constructor(int updateCounterCreated, ITextComponent lineString, int chatLineId, CallbackInfo ci) {
        StringBuilder timestampBuilder = new StringBuilder();

        if (ChatTimestamp.rainbow.value) {
            String rainbow = '§' + "+";
            timestampBuilder.append(rainbow);
        } else {
            String custom = '§' + "z";
            int hex = 0x808080;
            timestampBuilder.append(custom).append(hex);
        }

        this.timestamp = timestampBuilder.append("<")
                .append(FORMAT.format(new Date()))
                .append("> ")
                .append(ChatFormatting.RESET)
                .toString();

        String t = lineString.getFormattedText();
        if (ChatTimestamp.INSTANCE.isEnabled()) {
            t = timestamp + t;
        }
    }
}
